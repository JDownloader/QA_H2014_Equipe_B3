package ca.ulaval.glo4002.domain.intervention;

import java.util.Date;

import javax.persistence.Entity;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalTool;

@Entity
public class NonCriticalIntervention extends Intervention {
	
	protected NonCriticalIntervention() {
		// Required for Hibernate.
	}
	
	public NonCriticalIntervention(String description, Surgeon surgeon, Date date, String room, InterventionType type, InterventionStatus status, Patient patient) {
		super(description, surgeon, date, room, type, status, patient);
	}
	
	public void addSurgicalTool(SurgicalTool surgicalTool) {
		surgicalTools.add(surgicalTool);
	}
	
}
