package ca.ulaval.glo4002.services.dto;

import ca.ulaval.glo4002.exceptions.ServiceRequestException;

public class BadRequestDTOFactory {
	public BadRequestDTO createBadRequestDTOFromServiceException(ServiceRequestException e) {
		return new BadRequestDTO(e.getInternalCode(), e.getMessage());
	}
}
