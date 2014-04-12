package ca.ulaval.glo4002.domain.intervention;

import java.util.*;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalTool;
import ca.ulaval.glo4002.exceptions.domainexceptions.interventionexceptions.InvalidArgument;

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
	
	public Intervention(String description, int surgeon, Date date, String room, 
						String type, String status, Patient patient) {
		this.description = returnValidDescription(description);
		this.surgeon = new Surgeon(surgeon); //TODO : call a factory
		this.date = date;
		this.room = returnValidRoom(room);
		this.type = InterventionType.fromString(type);
		this.status = returnValidStatus(status);
		this.patient = patient;
	}
	
	private String returnValidRoom(String room) {
		if(room.isEmpty()) {
			throw new InvalidArgument();
		}
		return room;
	}
	
	private String returnValidDescription(String description) {
		if(description.isEmpty()) {
			throw new InvalidArgument();
		}
		return description;
	}
	
	private InterventionStatus returnValidStatus(String status) {
		InterventionStatus newStatus;
		
		if(status.isEmpty()) {
			newStatus = InterventionStatus.PLANNED;
		}
		else {
			newStatus = InterventionStatus.fromString(status);
		}
		
		return newStatus;
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