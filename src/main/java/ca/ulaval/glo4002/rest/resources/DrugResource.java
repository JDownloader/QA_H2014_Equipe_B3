package ca.ulaval.glo4002.rest.resources;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.json.*;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.drug.DrugDoesNotContainDinException;
import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.exceptions.RequestParseException;
import ca.ulaval.glo4002.persistence.drug.HibernateDrugRepository;
import ca.ulaval.glo4002.rest.requestparsers.drug.DrugSearchRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.drug.DrugSearchRequestParserFactory;
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;
import ca.ulaval.glo4002.services.drug.DrugService;
import ca.ulaval.glo4002.services.drug.DrugServiceBuilder;

@Path("medicaments/dins/")
public class DrugResource {
    private static final String DRUG_DIN_RESULT_PARAMETER = "din";
    private static final String DRUG_NAME_RESULT_PARAMETER = "nom";
    private static final String DRUG_DESCRIPTION_RESULT_PARAMETER = "description";
    
	private DrugService service;
	private DrugSearchRequestParserFactory drugSearchRequestParserFactory;
	
    public static final String ERROR_BAD_REQUEST_DIN001 = "DIN001";

	public DrugResource() {
		EntityManager entityManager = new EntityManagerProvider().getEntityManager();
		
		buildDrugService(entityManager);
		
		this.drugSearchRequestParserFactory = new DrugSearchRequestParserFactory();
	}

	private void buildDrugService(EntityManager entityManager) {
		DrugServiceBuilder drugServiceBuilder = new DrugServiceBuilder();
		drugServiceBuilder.entityTransaction(entityManager.getTransaction());
		drugServiceBuilder.drugRepository(new HibernateDrugRepository());
		this.service = new DrugService(drugServiceBuilder);
	}
	
	public DrugResource(DrugService service, DrugSearchRequestParserFactory drugSearchRequestParserFactory) {
		this.service = service;
		this.drugSearchRequestParserFactory = drugSearchRequestParserFactory;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(String request){
		try {
			DrugSearchRequestParser requestParser = getRequestParser(request);
			List<Drug> drugResults = service.searchDrug(requestParser); 
			return buildDrugResultResponse(drugResults);
		} catch (RequestParseException | JSONException e) {
			return BadRequestJsonResponseBuilder.build(ERROR_BAD_REQUEST_DIN001, e.getMessage());
		} catch (ServiceRequestException e) {
			return BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	private DrugSearchRequestParser getRequestParser(String request) throws RequestParseException {
		JSONObject jsonRequest = new JSONObject(request);
		DrugSearchRequestParser interventionRequestParser = drugSearchRequestParserFactory.getParser(jsonRequest);
		return interventionRequestParser;
	}
	
	private Response buildDrugResultResponse(List<Drug> drugs) throws DrugDoesNotContainDinException{
		JSONArray jsonArray = new JSONArray();
		
		for(Drug drug : drugs) {
			JSONObject jsonResponse = new JSONObject();
			jsonResponse.append(DRUG_DIN_RESULT_PARAMETER, drug.getDin().toString());
			jsonResponse.append(DRUG_NAME_RESULT_PARAMETER, drug.getName());
			jsonResponse.append(DRUG_DESCRIPTION_RESULT_PARAMETER, drug.getDescription());
			jsonArray.put(jsonResponse);
		}

		return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(jsonArray.toString()).build();
	}

}
