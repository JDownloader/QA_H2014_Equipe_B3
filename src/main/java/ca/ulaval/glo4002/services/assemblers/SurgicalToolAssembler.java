package ca.ulaval.glo4002.services.assemblers;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalTool;
import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;

public class SurgicalToolAssembler {

	public SurgicalTool assembleFromDTO(SurgicalToolCreationDTO surgicalToolCreationDTO) {

		SurgicalTool surgicalTool = new SurgicalTool(surgicalToolCreationDTO.getSerialNumber(),
				surgicalToolCreationDTO.getTypeCode(), surgicalToolCreationDTO.getStatus());

		return surgicalTool;
	}

}
