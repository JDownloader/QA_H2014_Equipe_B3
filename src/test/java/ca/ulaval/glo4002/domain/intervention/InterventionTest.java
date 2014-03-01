package ca.ulaval.glo4002.domain.intervention;

import org.junit.Before;
import org.junit.Test;

public class InterventionTest {
	
	InterventionBuilder interventionBuilder;
	
	//TODO: Make Intervention tests.

	@Before
	public void init() {
		interventionBuilder = new InterventionBuilder();
	}
	
	@Test
	public void newInterventionCanSetTypeWithoutThrowingAnException() {
		interventionBuilder.type(InterventionType.OEIL);
		interventionBuilder.type(InterventionType.COEUR);
		interventionBuilder.type(InterventionType.MOELLE);
		interventionBuilder.type(InterventionType.ONCOLOGIQUE);
		interventionBuilder.type(InterventionType.AUTRE);
	}
	
	@Test
	public void newInterventionCanSetStatusWithoutThrowingAnException() {
		interventionBuilder.status(InterventionStatus.PLANIFIEE);
		interventionBuilder.status(InterventionStatus.EN_COURS);
		interventionBuilder.status(InterventionStatus.TERMINEE);
		interventionBuilder.status(InterventionStatus.ANNULEE);
		interventionBuilder.status(InterventionStatus.REPORTEE);
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
