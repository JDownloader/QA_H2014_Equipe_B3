package ca.ulaval.glo4002.domain.intervention;

import java.util.Date;

import ca.ulaval.glo4002.domain.intervention.Intervention.Status;
import ca.ulaval.glo4002.domain.intervention.Intervention.Type;
import ca.ulaval.glo4002.domain.staff.Surgeon;

public class InterventionBuilder {
	protected String description = null;
	protected Surgeon surgeon = null;
	protected Date date = null;
	protected String room = null;
	protected Type type = null;
	protected Status status = null;
	
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
	
	public InterventionBuilder type(Type type) {
		this.type = type;
		return this;
	}
	
	public InterventionBuilder status(Status status) {
		this.status = status;
		return this;
	}
	
	public InterventionBuilder status(String statusName) throws IllegalArgumentException {
		for (Status status : Status.values()) {
			if (status.toString().compareToIgnoreCase(statusName) == 0) {
				this.status = status;
				return this;
			}
		}

		throw new IllegalArgumentException(
				"The specified status value is invalid.");
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
