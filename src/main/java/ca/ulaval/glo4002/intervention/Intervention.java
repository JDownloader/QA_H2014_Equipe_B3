package ca.ulaval.glo4002.intervention;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ca.ulaval.glo4002.drug.Drug;
import ca.ulaval.glo4002.patient.Patient;
import ca.ulaval.glo4002.persistence.EM;
import ca.ulaval.glo4002.staff.Surgeon;

@Entity(name = "INTERVENTION")
public class Intervention {
	@Transient
	private static int idMax = 0;

	@Id
	@Column(name = "INTERVENTION_ID", nullable = false)
	private int id;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@ManyToOne()
	@ElementCollection(targetClass = Surgeon.class)
	@JoinColumn(name = "SURGEON", nullable = false)
	private Surgeon surgeon;

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
	@ElementCollection(targetClass = Drug.class)
	@JoinColumn(name = "PATIENT", nullable = false)
	private Patient patient;

	public Intervention(String description) {
		incrementAutoId();
		this.description = description;
	}
	
	public int getId() {
		return id;
	}

	private void incrementAutoId() {
		this.id = idMax;
		idMax++;
	}
	
	public void setSurgeon(Surgeon surgeon) {
		this.surgeon = surgeon;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setStatus(String statusName) throws IllegalArgumentException {
		for (Status status : Status.values()) {
			if (status.toString().compareToIgnoreCase(statusName) == 0) {
				this.status = status;
				return;
			}
		}

		throw new IllegalArgumentException("The specified status value is invalid.");
	}
	
	public void commit() {
		EM.getEntityManager().persist(this);
		EM.getUserTransaction().commit();
	}
}
