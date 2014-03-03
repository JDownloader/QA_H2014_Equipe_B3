package ca.ulaval.glo4002.domain.intervention;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;

public class InterventionBuilderTest {

	private static final Date SAMPLE_DATE = new Date();
	private static final String SAMPLE_DESCRIPTION = "description";
	private static final String SAMPLE_ROOM = "room";
	private static final InterventionType SAMPLE_TYPE = InterventionType.MOELLE;

	private Patient patientMock;
	private Surgeon surgeonMock;

	private InterventionBuilder interventionBuilder;
	private InterventionBuilder interventionBuilderSpy;

	@Before
	public void init() {
		patientMock = mock(Patient.class);
		surgeonMock = mock(Surgeon.class);

		interventionBuilder = new InterventionBuilder();
		interventionBuilderSpy = spy(interventionBuilder);
	}

	@Test
	public void buildsCorrectly() {
		doBuild();
	}

	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedDescription() {
		doReturn(interventionBuilderSpy).when(interventionBuilderSpy).description(anyString());
		doBuild();
	}

	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedSurgeon() {
		doReturn(interventionBuilderSpy).when(interventionBuilderSpy).surgeon(any(Surgeon.class));
		doBuild();
	}

	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedDate() {
		doReturn(interventionBuilderSpy).when(interventionBuilderSpy).date(any(Date.class));
		doBuild();
	}

	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedRoom() {
		doReturn(interventionBuilderSpy).when(interventionBuilderSpy).room(anyString());
		doBuild();
	}

	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedType() {
		doReturn(interventionBuilderSpy).when(interventionBuilderSpy).type(any(InterventionType.class));
		doBuild();
	}

	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedPatient() {
		doReturn(interventionBuilderSpy).when(interventionBuilderSpy).patient(any(Patient.class));
		doBuild();
	}

	private void doBuild() {
		interventionBuilderSpy.date(SAMPLE_DATE);
		interventionBuilderSpy.description(SAMPLE_DESCRIPTION);
		interventionBuilderSpy.patient(patientMock);
		interventionBuilderSpy.room(SAMPLE_ROOM);
		interventionBuilderSpy.surgeon(surgeonMock);
		interventionBuilderSpy.type(SAMPLE_TYPE);
		interventionBuilderSpy.build();
	}
}
