package ca.ulaval.glo4002.domain.intervention;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

public class InterventionFactory {
	public Intervention createIntervention(InterventionCreationDTO dto, Patient patient) {
		Intervention intervention = new Intervention(dto.getDescription(),
				dto.getSurgeonNumber(), dto.getDate(), dto.getRoom(), dto.getType(),
				dto.getStatus(), patient);
		return intervention;
	}
}
