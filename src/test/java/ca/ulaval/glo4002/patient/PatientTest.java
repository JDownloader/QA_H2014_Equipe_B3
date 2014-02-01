package ca.ulaval.glo4002.patient;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class PatientTest {
	private static final int ID_PATIENT = 1;
	private Patient myPatient;

	@Before
	public void init() {
		myPatient = new Patient();
	}

	@Test
	public void emptyConstructorPatient() {
		assertNotNull(myPatient);
	}

	@Test
	public void getIdPatient() {
		assertEquals(ID_PATIENT, myPatient.getId());
	}

	@Test
	public void addPrescriptionToPatient() {
		Collection<Integer> presciptionId = null;
		Integer idPrescription = 1;
		presciptionId.add(idPrescription);
		assertTrue(presciptionId.contains(idPrescription));
		/*
		 * TODO Check out whether we will update the database or add the
		 * prescription to the object's ArrayList called PrecriptionId.
		 * 
		 * @author Marie-Hélène
		 */
	}
}
