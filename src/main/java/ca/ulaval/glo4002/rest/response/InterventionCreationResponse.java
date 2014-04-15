package ca.ulaval.glo4002.rest.response;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.exceptions.domainexceptions.DomainException;
import ca.ulaval.glo4002.services.dto.BadResponseDTO;
import ca.ulaval.glo4002.services.dto.CreatedWithMessageResponseDTO;

public class InterventionCreationResponse {

	private static final String MESSAGE_PARAMETER = "message";
	private static final String LOCATION_PARAMETER = "location";
	
	public Response createBadRequestResponse(DomainException e) {
		BadResponseDTO badResponseDto = new BadResponseDTO(e);
		return Response.status(Status.BAD_REQUEST)
				.type(MediaType.APPLICATION_JSON)
				.entity(badResponseDto).build();
	}

	public Response createSuccessResponse(int interventionNumber) {
		String message = MESSAGE_PARAMETER + ": Succès, " + LOCATION_PARAMETER + ": /interventions/" + interventionNumber;
		CreatedWithMessageResponseDTO createdWithMessageResponseDTO = new CreatedWithMessageResponseDTO(message);
		return Response.status(Status.CREATED).type(MediaType.APPLICATION_JSON)
				.entity(createdWithMessageResponseDTO).build();
	}
}
