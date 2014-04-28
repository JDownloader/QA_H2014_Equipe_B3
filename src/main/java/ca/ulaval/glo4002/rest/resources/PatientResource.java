package ca.ulaval.glo4002.rest.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.domain.drug.DrugNotFoundException;
import ca.ulaval.glo4002.domain.patient.DrugInteractionException;
import ca.ulaval.glo4002.domain.patient.PatientNotFoundException;
import ca.ulaval.glo4002.rest.utils.ResponseAssembler;
import ca.ulaval.glo4002.services.PatientService;
import ca.ulaval.glo4002.services.assemblers.PrescriptionAssembler;
import ca.ulaval.glo4002.services.dto.PrescriptionCreationDTO;
import ca.ulaval.glo4002.services.dto.validators.DTOValidationException;
import ca.ulaval.glo4002.services.dto.validators.PrescriptionCreationDTOValidator;

@Path("patient/{patient_number: [0-9]+}/prescriptions/")
public class PatientResource {
	public static final String ERROR_PRES001 = "PRES001";
	public static final String ERROR_PRES002 = "PRES002";
	
	private PatientService patientService; 

	public PatientResource() {
		this.patientService = new PatientService();
	}

	public PatientResource(PatientService patientService) {
		this.patientService = patientService;
	}

	@PathParam("patient_number")
	private Integer patientNumber;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createPrescription(PrescriptionCreationDTO prescriptionCreationDTO) {
		try {
			prescriptionCreationDTO.patientNumber = patientNumber;
			
			patientService.createPrescription(prescriptionCreationDTO, new PrescriptionCreationDTOValidator(), new PrescriptionAssembler());
			
			return Response.status(Status.CREATED).build();
		} catch (DTOValidationException | PatientNotFoundException | DrugNotFoundException e) {
			return ResponseAssembler.assembleErrorResponse(Status.BAD_REQUEST, ERROR_PRES001, e.getMessage());
		} catch (DrugInteractionException e) {
			return ResponseAssembler.assembleErrorResponse(Status.CONFLICT, ERROR_PRES002, e.getMessage());
		}
	}
}
