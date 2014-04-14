package ca.ulaval.glo4002.domain.intervention;

import java.util.*;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.domain.surgicaltool.*;

@SuppressWarnings("unused") //Suppresses warning for private attributes used for Hibernate persistence
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

	//Refactor NewMarie BEGIN
	public Intervention(InterventionBuilder builder) {
		this.description = builder.description;
		this.surgeon = builder.surgeon;
		this.date = builder.date;
		this.room = builder.room;
		this.type = builder.type;
		this.patient = builder.patient;
		this.status = builder.status;
	}
	//Refactor NewMarie END

	public int getId() {
		return this.id;
	}
	
	public void addSurgicalTool(SurgicalTool surgicalTool) {
		checkForAnonymousSurgicalToolViolation(surgicalTool);
		surgicalTools.add(surgicalTool);
	}
	
	public void changeSurgicalToolSerialNumber(SurgicalTool surgicalTool, String newSerialNumber) {
		surgicalTool.setSerialNumber(newSerialNumber);
		checkForAnonymousSurgicalToolViolation(surgicalTool);
	}
	
	private void checkForAnonymousSurgicalToolViolation(SurgicalTool surgicalTool) {
		if (surgicalTool.isAnonymous() && Arrays.asList(forbiddenInterventionTypesForAnonymousSurgicalTools).contains(type)) {
			throw new SurgicalToolRequiresSerialNumberException("Erreur - requiert numéro de série.");
		}
	}

	public boolean hasSurgicalTool(SurgicalTool surgicalTool) {
		return surgicalTools.contains(surgicalTool);
	}
}
