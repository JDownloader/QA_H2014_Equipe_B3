package ca.ulaval.glo4002.domain.intervention;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.intervention.Intervention;

public class InterventionTest {
	
	InterventionBuilder interventionBuilder;
	
	//TODO: Make Intervention tests.

	@Before
	public void init() {
		interventionBuilder = new InterventionBuilder();
	}
	
	@Test
	public void newInterventionCanSetTypeWithoutThrowingAnException() {
		interventionBuilder.type(Intervention.Type.OEIL);
		interventionBuilder.type(Intervention.Type.COEUR);
		interventionBuilder.type(Intervention.Type.MOELLE);
		interventionBuilder.type(Intervention.Type.ONCOLOGIQUE);
		interventionBuilder.type(Intervention.Type.AUTRE);
	}
	
	@Test
	public void newInterventionCanSetStatusWithoutThrowingAnException() {
		interventionBuilder.status(Intervention.Status.PLANIFIEE);
		interventionBuilder.status(Intervention.Status.EN_COURS);
		interventionBuilder.status(Intervention.Status.TERMINEE);
		interventionBuilder.status(Intervention.Status.ANNULEE);
		interventionBuilder.status(Intervention.Status.REPORTEE);
	}
	
	@Test
	public void newInterventionCanSetStatusFromNameWithoutThrowingAnException() {
		interventionBuilder.status("PlAnIfIeE");
		interventionBuilder.status("En_CoUrS");
		interventionBuilder.status("TeRmInEe");
		interventionBuilder.status("AnNuLeE");
		interventionBuilder.status("RePoRtEe");
	}

}
