package ca.ulaval.glo4002.rest;

import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.json.*;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;
import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.persistence.drug.HibernateDrugRepository;
import ca.ulaval.glo4002.rest.requestparsers.drug.DrugSearchRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.drug.DrugSearchRequestParserFactory;
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;
import ca.ulaval.glo4002.services.drug.DrugService;
import ca.ulaval.glo4002.services.drug.DrugServiceBuilder;

@Path("medicaments/dins/")
public class DrugResource {
	private DrugService service;
	private DrugSearchRequestParserFactory drugSearchRequestParserFactory;
	
	public DrugResource() {
		EntityManager entityManager = new EntityManagerProvider().getEntityManager();
		
		DrugServiceBuilder drugServiceBuilder = new DrugServiceBuilder();
		drugServiceBuilder.entityTransaction(entityManager.getTransaction());
		drugServiceBuilder.drugRepository(new HibernateDrugRepository());
		this.service = new DrugService(drugServiceBuilder);
		
		this.drugSearchRequestParserFactory = new DrugSearchRequestParserFactory();
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
			DrugSearchRequestParser drugSearchRequest = getDrugSearchRequestParser(request);
			List<Drug> drugResults = service.searchDrug(drugSearchRequest); 
			return buildDrugResultResponse(drugResults);
		} catch (JSONException | ParseException e) {
			return BadRequestJsonResponseBuilder.build("DIN001", "Invalid parameters were supplied to the request.");
		} catch (IllegalArgumentException e) {
			return BadRequestJsonResponseBuilder.build("DIN001", e.getMessage());
		} catch (BadRequestException e) {
			return BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	private DrugSearchRequestParser getDrugSearchRequestParser(String request) throws JSONException, ParseException {
		JSONObject jsonRequest = new JSONObject(request);
		DrugSearchRequestParser interventionRequestParser = drugSearchRequestParserFactory.getParser(jsonRequest);
		return interventionRequestParser;
	}
	
	private Response buildDrugResultResponse(List<Drug> drugs) {
		JSONArray jsonArray = new JSONArray();
		
		for(Drug drug : drugs) {
			JSONObject jsonResponse = new JSONObject();
			jsonResponse.append("din", drug.getDin().toString());
			jsonResponse.append("nom", drug.getName());
			jsonResponse.append("description", drug.getDescription());
			jsonArray.put(jsonResponse);
		}

		return Response.status(Status.CREATED).type(MediaType.APPLICATION_JSON).entity(jsonArray.toString()).build();
	}

}
