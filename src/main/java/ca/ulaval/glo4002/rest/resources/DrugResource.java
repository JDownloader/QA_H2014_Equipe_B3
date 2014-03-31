package ca.ulaval.glo4002.rest.resources;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.dto.DrugSearchDto;
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;
import ca.ulaval.glo4002.rest.utils.ObjectMapperProvider;
import ca.ulaval.glo4002.services.drug.DrugService;

@Path("medicaments/dins/")
public class DrugResource {

	private DrugService drugService;
	private ObjectMapper objectMapper;

	public static final String BAD_REQUEST_ERROR_CODE_DIN001 = "DIN001";

	public DrugResource() {
		this.drugService = new DrugService();
		this.objectMapper = ObjectMapperProvider.getObjectMapper();
	}

	public DrugResource(DrugService drugService, ObjectMapper objectMapper) {
		this.drugService = drugService;
		this.objectMapper = objectMapper;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(String request) throws Exception {
		try {
			DrugSearchDto drugSearchDto = mapJsonToDrugSearchDto(request);
			List<Drug> drugResults = drugService.searchDrug(drugSearchDto);
			return buildDrugResultResponse(drugResults);
		} catch (JsonParseException | JsonMappingException e) {
			return BadRequestJsonResponseBuilder.build(BAD_REQUEST_ERROR_CODE_DIN001, e.getMessage());
		} catch (ServiceRequestException e) {
			return BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		}
	}

	private DrugSearchDto mapJsonToDrugSearchDto(String jsonRequest) throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(jsonRequest, DrugSearchDto.class);
	}

	private Response buildDrugResultResponse(List<Drug> drugResults) throws JsonGenerationException, JsonMappingException, IOException {
		Writer stringWriter = new StringWriter();
		objectMapper.writeValue(stringWriter, drugResults);
		return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(stringWriter.toString()).build();
	}

}
