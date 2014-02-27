package ca.ulaval.glo4002.intervention;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ca.ulaval.glo4002.patient.Patient;
import ca.ulaval.glo4002.staff.Surgeon;

@Entity(name = "INTERVENTION")
public class Intervention {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@Column(name = "SURGEON", nullable = false)
	private Surgeon surgeon = null;

	@Column(name = "DATE", nullable = false)
	private Date date = null;

	@Column(name = "ROOM", nullable = false)
	private String room;

	public enum Type {
		OEIL, COEUR, MOELLE, ONCOLOGIQUE, AUTRE;

		@Override
		public String toString() {
			return super.toString();
		}
	}

	@Column(name = "TYPE", nullable = false)
	private Type type;

	public enum Status {
		PLANIFIEE, EN_COURS, TERMINEE, ANNULEE, REPORTEE;

		@Override
		public String toString() {
			return super.toString();
		}
	}

	@Column(name = "STATUS", nullable = false)
	private Status status;

	@ManyToOne()
	@JoinColumn(name = "PATIENT", nullable = false)
	private Patient patient;
	
	protected Intervention() {
		
	}

	public Intervention(Builder builder) {
		this.description = builder.description;
		this.surgeon = builder.surgeon;
		this.date = builder.date;
		this.room = builder.room;
		this.type = builder.type;
		this.status = builder.status;
	}
	
	public static class Builder {
		private String description = null;
		private Surgeon surgeon = null;
		private Date date = null;
		private String room = null;
		private Type type = null;
		private Status status = null;
		
		public Builder description(String description) {
			this.description = description;
			return this;
		}
		
		public Builder surgeon(Surgeon surgeon) {
			this.surgeon = surgeon;
			return this;
		}
		
		public Builder date(Date date) {
			this.date = date;
			return this;
		}
		
		public Builder room(String room) {
			this.room = room;
			return this;
		}
		
		public Builder type(Type type) {
			this.type = type;
			return this;
		}
		
		public Builder status(Status status) {
			this.status = status;
			return this;
		}
		
		public Builder status(String statusName) throws IllegalArgumentException {
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
}
