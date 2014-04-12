package ca.ulaval.glo4002.domain.intervention;

import java.util.*;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalTool;
import ca.ulaval.glo4002.exceptions.SurgicalToolRequiresSerialNumberException;

@Entity(name = "INTERVENTION")
public class Intervention {

	private static final InterventionType[] forbiddenInterventionTypesForAnonymousSurgicalTools = {
			InterventionType.OEIL, InterventionType.COEUR, InterventionType.MOELLE };

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@Column(name = "SURGEON", nullable = false)
	private Surgeon surgeon;

	@Column(name = "DATE", nullable = false)
	private Date date;

	@Column(name = "ROOM", nullable = false)
	private String room;

	@Column(name = "TYPE", nullable = false)
	private InterventionType type;

	@Column(name = "STATUS", nullable = false)
	private InterventionStatus status;

	@ManyToOne()
	@JoinColumn(name = "PATIENT", nullable = false)
	private Patient patient;

	@ElementCollection()
	@JoinColumn(name = "INTERVENTION")
	private List<SurgicalTool> surgicalTools = new ArrayList<SurgicalTool>();

	protected Intervention() {
		// Required for Hibernate.
	}

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
		verifyIfSurgicalToolRequiresASerialNumber(surgicalTool);
		surgicalTools.add(surgicalTool);
	}

	public boolean hasSurgicalTool(SurgicalTool surgicalTool) {
		return surgicalTools.contains(surgicalTool);
	}

	public void verifyIfSurgicalToolRequiresASerialNumber(SurgicalTool surgicalTool) {

		if (surgicalTool.isAnonymous()
				&& Arrays.asList(forbiddenInterventionTypesForAnonymousSurgicalTools).contains(type)) {
			throw new SurgicalToolRequiresSerialNumberException(
					"An anonymous surgical tool cannot be used with this type of intervention.");
		}

	}

}
