package ca.ulaval.glo4002.domain.intervention;

import java.util.*;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.domain.surgicaltool.*;

@SuppressWarnings("unused")
// Suppresses warning for private attributes used for Hibernate persistence
@Entity
public class Intervention {

	private static final InterventionType[] forbiddenInterventionTypesForAnonymousSurgicalTools = {InterventionType.EYE, InterventionType.HEART,
			InterventionType.MARROW };

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String description;
	@Enumerated(EnumType.STRING)
	private Surgeon surgeon;
	private Date date;
	private String room;
	@Enumerated(EnumType.STRING)
	private InterventionType type;
	@Enumerated(EnumType.STRING)
	private InterventionStatus status;
	@ManyToOne()
	private Patient patient;
	@OneToMany(cascade = CascadeType.ALL)
	private List<SurgicalTool> surgicalTools = new ArrayList<SurgicalTool>();

	protected Intervention() {
		// Required for Hibernate.
	}

	public Intervention(String description, Surgeon surgeon, Date date, String room, InterventionType type, InterventionStatus status, Patient patient) {
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
	
	@PostLoad
	public void linkObservers() {
		for (SurgicalTool surgicalTool : surgicalTools) {
			surgicalTool.deleteObservers();
			addObserverToSurgicalTool(surgicalTool);
		}
	}
	
	public SurgicalTool getSurgicalToolBySerialNumberOrId(String serialNumberOrId) {
		for (SurgicalTool surgicalTool : surgicalTools) {
			if (surgicalTool.compareToSerialNumber(serialNumberOrId) || surgicalTool.compareToId(serialNumberOrId)) {
				return surgicalTool;
			}
		}

		throw new SurgicalToolNotFoundException(String.format("Impossible de trouver l'instrument avec numéro de série ou no unique '%s' dans l'intervention '%s'.",
				serialNumberOrId, this.id));
	}

	public void addSurgicalTool(SurgicalTool surgicalTool) {
		checkAnonymousSurgicalToolIsAuthorized(surgicalTool);
		addObserverToSurgicalTool(surgicalTool);
		surgicalTools.add(surgicalTool);
	}

	public boolean containsSurgicalTool(SurgicalTool surgicalTool) {
		return surgicalTools.contains(surgicalTool);
	}
	
	public class SurgicalToolObserver implements Observer {

		@Override
		public void update(Observable observable, Object arg) {
			checkAnonymousSurgicalToolIsAuthorized((SurgicalTool) observable);
		}
        
    }
	
	private void checkAnonymousSurgicalToolIsAuthorized(SurgicalTool surgicalTool) {
		if (surgicalTool.isAnonymous() && Arrays.asList(forbiddenInterventionTypesForAnonymousSurgicalTools).contains(type)) {
			throw new SurgicalToolRequiresSerialNumberException("Erreur - requiert numéro de série.");
		}
	}
	
	private void addObserverToSurgicalTool(SurgicalTool surgicalTool) {
		surgicalTool.addObserver(new SurgicalToolObserver());
	}
}
