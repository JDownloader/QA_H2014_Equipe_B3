package ca.ulaval.glo4002.domain.intervention;


import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

public class InterventionFactory {
	public Intervention createIntervention(InterventionCreationDTO dto, Surgeon surgeon, Patient patient) {
		Intervention intervention = new Intervention(dto.getDescription(), surgeon, );
		return intervention;
	}
}
