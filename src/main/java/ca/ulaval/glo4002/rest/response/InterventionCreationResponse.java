package ca.ulaval.glo4002.rest.response;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.services.dto.BadRequestDTO;
import ca.ulaval.glo4002.services.dto.CreatedWithLocationResponseDTO;

public class InterventionCreationResponse {

	public Response createBadRequestResponse(ServiceRequestException e) {
		BadRequestDTO badResponseDto = new BadRequestDTO(e);
		return Response.status(Status.BAD_REQUEST)
				.type(MediaType.APPLICATION_JSON).entity(badResponseDto)
				.build();
	}

	public Response createSuccessResponse(int interventionNumber) {
		String message = "/interventions/" + interventionNumber;
		CreatedWithLocationResponseDTO createdWithMessageResponseDTO = new CreatedWithLocationResponseDTO(
				message);
		return Response.status(Status.CREATED).type(MediaType.APPLICATION_JSON)
				.entity(createdWithMessageResponseDTO).build();
	}
}
