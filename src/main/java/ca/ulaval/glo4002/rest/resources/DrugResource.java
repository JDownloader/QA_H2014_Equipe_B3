package ca.ulaval.glo4002.rest.resources;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.rest.utils.ResponseAssembler;
import ca.ulaval.glo4002.services.DrugService;
import ca.ulaval.glo4002.services.dto.DrugSearchDTO;
import ca.ulaval.glo4002.services.dto.validators.DTOValidationException;
import ca.ulaval.glo4002.services.dto.validators.DrugSearchDTOValidator;

@Path("medicaments/dins/")
public class DrugResource {
	public static final String ERROR_DIN001 = "DIN001";
	
	private DrugService drugService;

	public static final String BAD_REQUEST_ERROR_CODE_DIN001 = "DIN001";

	public DrugResource() {
		this.drugService = new DrugService();
	}

	public DrugResource(DrugService drugService) {
		this.drugService = drugService;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@QueryParam("nom") String name) throws Exception {
		try {
			DrugSearchDTO drugSearchDTO = new DrugSearchDTO(name);
			
			List<Drug> drugResults = drugService.searchDrug(drugSearchDTO, new DrugSearchDTOValidator());
			
			return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(drugResults).build();
		} catch (DTOValidationException e) {
			return ResponseAssembler.assembleErrorResponse(Status.BAD_REQUEST, ERROR_DIN001, e.getMessage());
		}
	}
}
