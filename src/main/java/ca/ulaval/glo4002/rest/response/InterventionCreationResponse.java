package ca.ulaval.glo4002.rest.response;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import ca.ulaval.glo4002.exceptions.domainexceptions.DomainException;
import ca.ulaval.glo4002.services.dto.BadResponseDTO;

public class InterventionCreationResponse {

	private static final String MESSAGE_PARAMETER = "message";
	private static final String LOCATION_PARAMETER = "location";

	public Response createBadRequestResponse(DomainException e) {
		// TODO : This code does not send back the JSON in the right order (JSON
		// by definition does not respect a particular order, see if this can be
		// fixed. LinkedHashMap doesn't work to order the attributes.)
		BadResponseDTO badResponseDto = new BadResponseDTO(e);
		return Response.status(Status.BAD_REQUEST)
				.type(MediaType.APPLICATION_JSON)
				.entity(badResponseDto).build();
	}

	public Response createSuccessResponse(int interventionNumber) {
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put(LOCATION_PARAMETER,
				"/interventions/" + interventionNumber).put(MESSAGE_PARAMETER,
				"Succès");
		return Response.status(Status.CREATED).type(MediaType.APPLICATION_JSON)
				.entity(jsonResponse.toString()).build();
	}
}
