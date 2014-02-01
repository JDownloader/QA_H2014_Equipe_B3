package ca.ulaval.glo4002.intervention;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ca.ulaval.glo4002.staff.Surgeon;

public class InterventionTest {

	private static final String A_DATE = "2012-01-01";
	
	Intervention intervention;

	@Before
	public void init() {
		intervention = new Intervention("An intervention");
	}

	@Test
	public void aNewInterventionIsBeingCreated() {
		assertNotNull(intervention);
	}

	@Test
	public void aNewInterventionHasAUniqueId() {
		Intervention anotherIntervention = new Intervention("another intervention");
		assertTrue(anotherIntervention.getId() != intervention.getId());
	}

	@Test
	public void aNewInterventionCanSetSurgeonWithoutThrowingAnException() {
		Surgeon surgeonMock = Mockito.mock(Surgeon.class);
		intervention.setSurgeon(surgeonMock);
	}

	@Test
	public void aNewInterventionCanSetDateWithoutThrowingAnException() throws ParseException {
		Date aDate = new SimpleDateFormat("yyyy-MM-dd").parse(A_DATE);
		intervention.setDate(aDate);
	}
	
	@Test
	public void aNewInterventionCanHandleMinimumDate() throws ParseException {
		Date minimumDate = new Date(Long.MIN_VALUE);
		intervention.setDate(minimumDate);
	}
	
	@Test
	public void aNewInterventionCanHandleMaximumDate() throws ParseException {
		Date maximumDate = new Date(Long.MIN_VALUE);
		intervention.setDate(maximumDate);
	}
	
	@Test
	public void aNewInterventionCanSetRoomWithoutThrowingAnException() {
		intervention.setRoom("a room");
	}
	
	@Test
	public void aNewInterventionCanSetTypeWithoutThrowingAnException() {
		intervention.setType(Intervention.Type.OEIL);
		intervention.setType(Intervention.Type.COEUR);
		intervention.setType(Intervention.Type.MOELLE);
		intervention.setType(Intervention.Type.ONCOLOGIQUE);
		intervention.setType(Intervention.Type.AUTRE);
	}
	
	@Test
	public void aNewInterventionCanSetStatusWithoutThrowingAnException() {
		intervention.setStatus(Intervention.Status.PLANIFIEE);
		intervention.setStatus(Intervention.Status.EN_COURS);
		intervention.setStatus(Intervention.Status.TERMINEE);
		intervention.setStatus(Intervention.Status.ANNULEE);
		intervention.setStatus(Intervention.Status.REPORTEE);
	}
	
	@Test
	public void aNewInterventionCanSetStatusFromNameWithoutThrowingAnException() {
		intervention.setStatus("PlAnIfIeE");
		intervention.setStatus("En_CoUrS");
		intervention.setStatus("TeRmInEe");
		intervention.setStatus("AnNuLeE");
		intervention.setStatus("RePoRtEe");
	}

}
