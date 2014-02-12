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

@Path("/patient/{patient_number: [0-9]}/prescriptions/")
public class AddPrescription {
	
	@PathParam("patient_number")
	private int patientNumber;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(String message) {
		//TODO Complete the method appropriately
		//Fetch the JSON object passed by the POST request
		JSONObject jsonReceived = new JSONObject(message);
		//From that JSON, create a PrescriptionRequest
		PrescriptionRequest addedPrescription = new PrescriptionRequest(jsonReceived);
		if(addedPrescription.isValid())
			return Response.status(Status.CREATED).build();
		else {
			JSONObject prescriptionError = new JSONObject();
			prescriptionError.append("code", "PRES001");
			return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(prescriptionError.toString()).build();
		}
		
	}
}
