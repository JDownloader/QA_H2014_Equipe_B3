package ca.ulaval.glo4002.domain.intervention;

import java.util.Date;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;

public class InterventionBuilder {
	protected String description = null;
	protected Surgeon surgeon = null;
	protected Date date = null;
	protected String room = null;
	protected InterventionType type = null;
	protected InterventionStatus status = null;
	protected Patient patient = null;
	
	public InterventionBuilder description(String description) {
		this.description = description;
		return this;
	}
	
	public InterventionBuilder surgeon(Surgeon surgeon) {
		this.surgeon = surgeon;
		return this;
	}
	
	public InterventionBuilder date(Date date) {
		this.date = date;
		return this;
	}
	
	public InterventionBuilder room(String room) {
		this.room = room;
		return this;
	}
	
	public InterventionBuilder type(InterventionType type) {
		this.type = type;
		return this;
	}
	
	public InterventionBuilder status(InterventionStatus status) {
		this.status = status;
		return this;
	}
	
	public InterventionBuilder patient(Patient patient) {
		this.patient = patient;
		return this;
	}
	
	public Intervention build() {
		Intervention intervention = new Intervention(this);
		if (description == null
				|| surgeon == null
				|| date == null
				|| room == null
				|| type == null
				|| room == null) {
			throw new IllegalStateException();
		}
        return intervention;
    }
}
