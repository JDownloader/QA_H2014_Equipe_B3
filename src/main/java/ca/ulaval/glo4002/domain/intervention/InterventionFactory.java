package ca.ulaval.glo4002.domain.intervention;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

public class InterventionFactory {
	public Intervention createFromDTO(InterventionCreationDTO interventionCreationDto, Patient patient) {
		return new Intervention(interventionCreationDto.description,
				interventionCreationDto.surgeon, interventionCreationDto.date,
				interventionCreationDto.room, InterventionType.fromString(interventionCreationDto.type),
				InterventionStatus.fromString(interventionCreationDto.status), patient);
	}
}
