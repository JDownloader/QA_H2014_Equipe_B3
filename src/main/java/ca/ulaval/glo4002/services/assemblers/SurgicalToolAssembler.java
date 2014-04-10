package ca.ulaval.glo4002.services.assemblers;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalTool;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;
import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;

public class SurgicalToolAssembler {
	
	private final String ANONYME = null; //TODO: inutile si les NoSerie anonyme sont laissés à null

	public SurgicalTool assembleFromDTO(SurgicalToolCreationDTO surgicalToolCreationDTO) {
		SurgicalTool surgicalTool;
		if (surgicalToolCreationDTO.getSerialNumber() == null) {
			surgicalTool = new SurgicalTool(
					ANONYME, 
					surgicalToolCreationDTO.getTypeCode(),
					SurgicalToolStatus.fromString(surgicalToolCreationDTO.getStatus()));
		} else {
			surgicalTool = new SurgicalTool(
					surgicalToolCreationDTO.getSerialNumber(), 
					surgicalToolCreationDTO.getSerialNumber(),
					SurgicalToolStatus.fromString(surgicalToolCreationDTO.getStatus()));
		}

		return surgicalTool;
	}

}
