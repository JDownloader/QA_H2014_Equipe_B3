package ca.ulaval.glo4002.rest.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.exceptions.RequestParseException;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.prescription.PrescriptionCreationRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.prescription.PrescriptionCreationRequestParserFactory;
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;
import ca.ulaval.glo4002.services.prescription.PrescriptionService;

@Path("patient/{patient_number: [0-9]+}/prescriptions/")
public class PrescriptionResource {
	public static final String BAD_REQUEST_ERROR_CODE_PRES001 = "PRES001";

	private PrescriptionService service;
	private PrescriptionCreationRequestParserFactory addPrescriptionRequestParserFactory;

	public PrescriptionResource() {
		this.service = new PrescriptionService();
		this.addPrescriptionRequestParserFactory = new PrescriptionCreationRequestParserFactory();
	}

	public PrescriptionResource(PrescriptionService service, PrescriptionCreationRequestParserFactory addPrescriptionRequestParserFactory) {
		this.service = service;
		this.addPrescriptionRequestParserFactory = addPrescriptionRequestParserFactory;
	}

	@PathParam("patient_number")
	private int patientNumber;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(String request) throws Exception {
		try {
			PrescriptionCreationRequestParser requestParser = getRequestParser(request);
			service.addPrescription(requestParser);
			return Response.status(Status.CREATED).build();
		} catch (RequestParseException | JSONException e) {
			return BadRequestJsonResponseBuilder.build(BAD_REQUEST_ERROR_CODE_PRES001, e.getMessage());
		} catch (ServiceRequestException e) {
			return BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		}
	}

	private PrescriptionCreationRequestParser getRequestParser(String request) throws RequestParseException {
		JSONObject jsonRequest = new JSONObject(request);
		jsonRequest.put(PrescriptionCreationRequestParser.PATIENT_NUMBER_PARAMETER_NAME, String.valueOf(patientNumber));

		PrescriptionCreationRequestParser prescriptionParserRequest = addPrescriptionRequestParserFactory.createParser(jsonRequest);
		return prescriptionParserRequest;
	}
}
