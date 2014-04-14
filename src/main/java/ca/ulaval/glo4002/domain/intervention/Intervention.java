package ca.ulaval.glo4002.domain.intervention;

import java.util.*;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalTool;

@Entity
public class Intervention {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String description;
	@Column
	private Surgeon surgeon;
	@Column
	private Date date;
	@Column
	private String room;
	@Column
	private InterventionType type;
	@Column
	private InterventionStatus status;
	@ManyToOne()
	private Patient patient;
	@ElementCollection()
	private List<SurgicalTool> surgicalTools = new ArrayList<SurgicalTool>();

	protected Intervention() {
		// Required for Hibernate.
	}
	
	public Intervention(String description, Surgeon surgeon, Date date, String room, 
						InterventionType type, InterventionStatus status, Patient patient) {
		this.description = description;
		this.surgeon = surgeon;
		this.date = date;
		this.room = room;
		this.type = type;
		this.status = status;
		this.patient = patient;
	}
	
	//TODO : remove when nobody depends on this
	public Intervention(InterventionBuilder builder) {
		this.description = builder.description;
		this.surgeon = builder.surgeon;
		this.date = builder.date;
		this.room = builder.room;
		this.type = builder.type;
		this.patient = builder.patient;
		this.status = builder.status;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public Surgeon getSurgeon() {
		return surgeon;
	}

	public Date getDate() {
		return date;
	}

	public String getRoom() {
		return room;
	}

	public InterventionType getType() {
		return type;
	}

	public InterventionStatus getStatus() {
		return status;
	}

	public Patient getPatient() {
		return patient;
	}

	public void addSurgicalTool(SurgicalTool surgicalTool) {
		surgicalTools.add(surgicalTool);
	}

	public boolean hasSurgicalTool(SurgicalTool surgicalTool) {
		return surgicalTools.contains(surgicalTool);
	}
	
}