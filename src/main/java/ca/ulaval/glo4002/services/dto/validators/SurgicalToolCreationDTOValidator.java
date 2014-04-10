package ca.ulaval.glo4002.services.dto.validators;

import java.util.Arrays;

import ca.ulaval.glo4002.domain.intervention.InterventionType;
import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;

public class SurgicalToolCreationDTOValidator {

	private static final InterventionType[] forbiddenInterventionTypesForAnonymousSurgicalTools = {
			InterventionType.OEIL, InterventionType.COEUR, InterventionType.MOELLE };

	public void validate(SurgicalToolCreationDTO surgicalToolCreationDTO) {
		if (surgicalToolCreationDTO.getStatus() == null) {
			// TODO: inutile vu qu'on vérifie ça dans la création du tool?
			throw new SurgicalToolCreationException("Parameter 'statut' is required.");
		} else if (surgicalToolCreationDTO.getTypeCode() == null) {
			throw new SurgicalToolCreationException("Parameter 'typecode' is required.");
		}

		validateSerialNumber(surgicalToolCreationDTO);
	}

	//TODO: devrait se faire dans la logique d'affaire: dans la méthode addSurgicalTool de l'intervention
	private void validateSerialNumber(SurgicalToolCreationDTO surgicalToolCreationDTO) {

		boolean hasSerialNumber = (surgicalToolCreationDTO.getNoSerie() != null);

		if (hasSerialNumber == false
				&& Arrays.asList(forbiddenInterventionTypesForAnonymousSurgicalTools).contains(
						surgicalToolCreationDTO.getInterventionType())) {
			throw new SurgicalToolCreationException(
					"An anonymous surgical tool cannot be used with this type of intervention.");
		}
	}
}
