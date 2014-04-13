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

	private String description;
	private Surgeon surgeon;
	private Date date;
	private String room;
	private InterventionType type;
	private InterventionStatus status;

	@ManyToOne()
	private Patient patient;

	@OneToMany(cascade = CascadeType.ALL)
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
