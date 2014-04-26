package ca.ulaval.glo4002.rest.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.services.PatientService;
import ca.ulaval.glo4002.services.ServiceException;
import ca.ulaval.glo4002.services.assemblers.PrescriptionAssembler;
import ca.ulaval.glo4002.services.dto.BadRequestDTO;
import ca.ulaval.glo4002.services.dto.PrescriptionCreationDTO;
import ca.ulaval.glo4002.services.dto.validators.PrescriptionCreationDTOValidator;

@Path("patient/{patient_number: [0-9]+}/prescriptions/")
public class PatientResource {
	
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
	public Response createPrescription(PrescriptionCreationDTO prescriptionCreationDTO) throws Exception {
		try {
			prescriptionCreationDTO.patientNumber = patientNumber;
			patientService.createPrescription(prescriptionCreationDTO, new PrescriptionCreationDTOValidator(), new PrescriptionAssembler());
			return Response.status(Status.CREATED).build();
		} catch (ServiceException e) {
			return Response.status(Status.BAD_REQUEST).entity(new BadRequestDTO(e.getInternalCode(), e.getMessage())).build();
		}
	}
}
