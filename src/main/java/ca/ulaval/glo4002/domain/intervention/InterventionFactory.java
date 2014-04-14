package ca.ulaval.glo4002.domain.intervention;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

public class InterventionFactory {
	public Intervention createIntervention(InterventionCreationDTO dto,
			Patient patient) {
		Intervention intervention = new Intervention(dto.getDescription(),
				new Surgeon(dto.getSurgeonNumber()), dto.getDate(),
				dto.getRoom(), InterventionType.fromString(dto.getType()),
				InterventionStatus.fromString(dto.getStatus()), patient);
		return intervention;
	}
}
