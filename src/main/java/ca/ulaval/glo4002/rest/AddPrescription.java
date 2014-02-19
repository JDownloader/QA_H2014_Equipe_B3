package ca.ulaval.glo4002.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import ca.ulaval.glo4002.requests.PrescriptionRequest;

@Path("patient/{patient_number: [0-9]+}/prescriptions/")
public class AddPrescription {
	
	@PathParam("patient_number")
	private int patientNumber;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(final String input) {
		JSONObject jsonRequest = new JSONObject(input);
		jsonRequest.put("patient", String.valueOf(patientNumber));
		PrescriptionRequest addedPrescription = new PrescriptionRequest(jsonRequest);
		if(addedPrescription.isValid()) {
			//TODO : Send request to create prescription
			return Response.status(Status.CREATED).build();
		}
		else {
			JSONObject prescriptionError = new JSONObject();
			prescriptionError.append("code", "PRES001").append("message","Votre requête est invalide ou malformée.");
			return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(prescriptionError.toString()).build();
		}	
	}
}
