package ca.ulaval.glo4002.intervention;

import org.junit.Before;
import org.junit.Test;

public class InterventionTest {
	
	Intervention.Builder interventionBuilder;
	
	//TODO: Make Intervention tests.

	@Before
	public void init() {
		interventionBuilder = new Intervention.Builder();
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
