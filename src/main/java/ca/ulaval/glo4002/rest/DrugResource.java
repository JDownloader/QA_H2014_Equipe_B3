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
import ca.ulaval.glo4002.rest.requests.DrugSearchRequest;
import ca.ulaval.glo4002.rest.requests.DrugSearchRequestFactory;
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;
import ca.ulaval.glo4002.services.drug.DrugService;
import ca.ulaval.glo4002.services.drug.DrugServiceBuilder;

@Path("medicaments/dins/")
public class DrugResource {
	private DrugService service;
	private DrugSearchRequestFactory drugSearchRequestFactory;
	
	public DrugResource() {
		EntityManager entityManager = new EntityManagerProvider().getEntityManager();
		
		DrugServiceBuilder drugServiceBuilder = new DrugServiceBuilder();
		drugServiceBuilder.entityTransaction(entityManager.getTransaction());
		drugServiceBuilder.drugRepository(new HibernateDrugRepository());
		this.service = new DrugService(drugServiceBuilder);
		
		this.drugSearchRequestFactory = new DrugSearchRequestFactory();
	}
	
	public DrugResource(DrugService service, DrugSearchRequestFactory drugSearchRequestFactory) {
		this.service = service;
		this.drugSearchRequestFactory = drugSearchRequestFactory;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(String request){
		try {
			DrugSearchRequest drugSearchRequest = getDrugSearchRequest(request);
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
	
	private DrugSearchRequest getDrugSearchRequest(String request) throws JSONException, ParseException {
		JSONObject jsonRequest = new JSONObject(request);
		DrugSearchRequest interventionRequest = drugSearchRequestFactory.createDrugSearchRequest(jsonRequest);
		return interventionRequest;
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
