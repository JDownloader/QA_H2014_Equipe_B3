package ca.ulaval.glo4002.domain.surgicaltool;

import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;

public class SurgicalToolFactory {

	public SurgicalTool createFromDTO(SurgicalToolCreationDTO surgicalToolCreationDTO) {
		
		return new SurgicalTool(surgicalToolCreationDTO.serialNumber,
				surgicalToolCreationDTO.typeCode, surgicalToolCreationDTO.status);
		
	}

}
