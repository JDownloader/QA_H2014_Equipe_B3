package ca.ulaval.glo4002.rest.resources;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;
import ca.ulaval.glo4002.services.DrugService;
import ca.ulaval.glo4002.services.dto.DrugSearchDto;

@Path("medicaments/dins/")
public class DrugResource {

	private DrugService drugService;

	public static final String BAD_REQUEST_ERROR_CODE_DIN001 = "DIN001";

	public DrugResource() {
		this.drugService = new DrugService();
	}

	public DrugResource(DrugService drugService) {
		this.drugService = drugService;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(DrugSearchDto drugSearchDto) throws Exception {
		try {
			List<Drug> drugResults = drugService.searchDrug(drugSearchDto);
			return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(drugResults).build();
		} catch (ServiceRequestException e) {
			return BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		}
	}
}
