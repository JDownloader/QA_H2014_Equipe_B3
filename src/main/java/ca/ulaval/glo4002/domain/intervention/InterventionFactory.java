package ca.ulaval.glo4002.domain.intervention;

import java.util.Arrays;
import java.util.Date;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;

public class InterventionFactory {
	
	private static final InterventionType[] criticalInterventionTypes = {InterventionType.EYE, InterventionType.HEART,
		InterventionType.MARROW };
	
	public Intervention createIntervention(String description, Surgeon surgeon, Date date, String room, InterventionType type, InterventionStatus status, Patient patient) {
		
		if (Arrays.asList(criticalInterventionTypes).contains(type)) {
			return new CriticalIntervention(description, surgeon, date,
					room, type, status, patient);
		} else {
			return new NonCriticalIntervention(description, surgeon, date,
					room, type, status, patient);
		}
		
	}
}
