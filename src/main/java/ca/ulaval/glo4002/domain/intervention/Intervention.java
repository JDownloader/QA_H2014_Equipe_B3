package ca.ulaval.glo4002.domain.intervention;

import java.util.*;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.domain.surgicaltool.*;

@SuppressWarnings("unused") //Suppresses warning for private attributes used for Hibernate persistence
@Entity
public class Intervention {

	private static final InterventionType[] forbiddenInterventionTypesForAnonymousSurgicalTools = {
			InterventionType.EYE, InterventionType.HEART, InterventionType.MARROW };

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
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

	public Integer getId() {
		return this.id;
	}
	
	public void addSurgicalTool(SurgicalTool surgicalTool) {
		checkAnonymousSurgicalToolIsAuthorized(surgicalTool);
		surgicalTools.add(surgicalTool);
	}
	
	public void changeSurgicalToolSerialNumber(SurgicalTool surgicalTool, String newSerialNumber) {
		surgicalTool.setSerialNumber(newSerialNumber);
		checkAnonymousSurgicalToolIsAuthorized(surgicalTool);
	}
	
	private void checkAnonymousSurgicalToolIsAuthorized(SurgicalTool surgicalTool) {
		if (surgicalTool.isAnonymous() && Arrays.asList(forbiddenInterventionTypesForAnonymousSurgicalTools).contains(type)) {
			throw new SurgicalToolRequiresSerialNumberException("Erreur - requiert numéro de série.");
		}
	}

	public boolean hasSurgicalTool(SurgicalTool surgicalTool) {
		return surgicalTools.contains(surgicalTool);
	}
	
}