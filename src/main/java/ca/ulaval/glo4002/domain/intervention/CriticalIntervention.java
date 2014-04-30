package ca.ulaval.glo4002.domain.intervention;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.PostLoad;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalTool;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolRequiresSerialNumberException;

@Entity
public class CriticalIntervention extends Intervention {
	
	protected CriticalIntervention() {
		// Required for Hibernate.
	}
	
	public CriticalIntervention(String description, Surgeon surgeon, Date date, String room, InterventionType type, InterventionStatus status, Patient patient) {
		super(description, surgeon, date, room, type, status, patient);
	}
	
	@PostLoad
	public void linkObservers() {
		for (SurgicalTool surgicalTool : surgicalTools) {
			surgicalTool.deleteObservers();
			addObserverToSurgicalTool(surgicalTool);
		}
	}
	
	@Override
	public void addSurgicalTool(SurgicalTool surgicalTool) {
		verifySurgicalToolIsNonAnonymous(surgicalTool);
		addObserverToSurgicalTool(surgicalTool);
		surgicalTools.add(surgicalTool);
	}
	
	public class SurgicalToolObserver implements Observer {

		@Override
		public void update(Observable observable, Object arg) {
			verifySurgicalToolIsNonAnonymous((SurgicalTool) observable);
		}
        
    }
	
	private void addObserverToSurgicalTool(SurgicalTool surgicalTool) {
		surgicalTool.addObserver(new SurgicalToolObserver());
	}
	
	protected void verifySurgicalToolIsNonAnonymous(SurgicalTool surgicalTool) {
		if (surgicalTool.isAnonymous()) {
			throw new SurgicalToolRequiresSerialNumberException("Erreur - requiert numéro de série.");
		}
	}
	
}
