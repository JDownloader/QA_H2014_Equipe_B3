package ca.ulaval.glo4002.domain.intervention;

import java.util.Date;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;

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

	@Column(name = "TYPE", nullable = false)
	private InterventionType type;

	@Column(name = "STATUS", nullable = false)
	private InterventionStatus status;

	@ManyToOne()
	@JoinColumn(name = "PATIENT", nullable = false)
	private Patient patient;
	
	protected Intervention() {
		//Required for Hibernate.
	}

	public Intervention(InterventionBuilder builder) {
		this.description = builder.description;
		this.surgeon = builder.surgeon;
		this.date = builder.date;
		this.room = builder.room;
		this.type = builder.type;
		this.status = builder.status;
	}
}
