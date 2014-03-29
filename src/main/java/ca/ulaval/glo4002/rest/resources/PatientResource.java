package ca.ulaval.glo4002.rest.resources;

import java.io.IOException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.ulaval.glo4002.domain.prescription.PrescriptionFactory;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.dto.PrescriptionCreationDto;
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;
import ca.ulaval.glo4002.rest.utils.ObjectMapperProvider;
import ca.ulaval.glo4002.services.patient.PatientService;

@Path("patient/{patient_number: [0-9]+}/prescriptions/")
public class PatientResource {
	public static final String BAD_REQUEST_ERROR_CODE_PRES001 = "PRES001";

	private PatientService patientService; 
	private ObjectMapper objectMapper;

	public PatientResource() {
		this.patientService = new PatientService();
		this.objectMapper = ObjectMapperProvider.getObjectMapper();
	}

	public PatientResource(PatientService patientService, ObjectMapper objectMapper) {
		this.patientService = patientService;
		this.objectMapper = objectMapper;
	}

	@PathParam("patient_number")
	private int patientNumber;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(String jsonRequest) throws Exception {
		try {
			PrescriptionCreationDto prescriptionCreationDto = mapJsonToPrescriptionCreationDto(jsonRequest);
			patientService.createPrescription(prescriptionCreationDto, new PrescriptionFactory());
			return Response.status(Status.CREATED).build();
		} catch (JsonParseException | JsonMappingException e) {
			return BadRequestJsonResponseBuilder.build(BAD_REQUEST_ERROR_CODE_PRES001, e.getMessage());
		} catch (ServiceRequestException e) {
			return BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		}
	}
	
	private PrescriptionCreationDto mapJsonToPrescriptionCreationDto(String jsonRequest) throws JsonParseException, JsonMappingException, IOException {
		PrescriptionCreationDto prescriptionCreationDto = objectMapper.readValue(jsonRequest, PrescriptionCreationDto.class);
		prescriptionCreationDto.setPatientNumber(patientNumber);
		return prescriptionCreationDto;
	}
}
