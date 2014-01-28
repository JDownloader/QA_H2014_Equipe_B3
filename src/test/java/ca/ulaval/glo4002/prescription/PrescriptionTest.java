package ca.ulaval.glo4002.prescription;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.drug.Drug;
import ca.ulaval.glo4002.exceptions.InvalidDateFormatException;
import ca.ulaval.glo4002.staff.StaffMember;

public class PrescriptionTest {

	public static final Drug A_DRUG = new Drug("test");
	public static final int A_RENEWAL = 3;
	public static final String NOW = "2001-07-04T12:08:56";
	public static final String A_WRONG_DATE = "28-04-1989234-23";
	public static final StaffMember A_STAFF_MEMBER = new StaffMember();

	Prescription emptyPrescription;
	Prescription filledPrescription;

	@Before
	public void init() throws InvalidDateFormatException, ParseException {
		emptyPrescription = new Prescription(A_DRUG, A_STAFF_MEMBER);
		filledPrescription = new Prescription(A_DRUG, A_STAFF_MEMBER);
		filledPrescription.setDate(NOW);
		filledPrescription.setRenewal(A_RENEWAL);
	}

	@Test
	public void unePrescriptionEstCree() {
		assertNotNull(emptyPrescription);
	}

	@Test
	public void unePrescriptionAUnIdUnique() {
		Prescription otherPrescription = new Prescription(A_DRUG,
				A_STAFF_MEMBER);
		assertFalse(emptyPrescription.getId() == otherPrescription.getId());
	}

	@Test(expected = InvalidDateFormatException.class)
	public void unePrescriptionNePrendPasLeMauvaisFormatDeDate()
			throws InvalidDateFormatException, ParseException {
		emptyPrescription.setDate(A_WRONG_DATE);
	}

	@Test
	public void unePrescriptionVideNestPasValide() {
		assertFalse(emptyPrescription.isValid());
	}

	@Test
	public void onPeutAjouterUnRenouvellementAUnePrescription() {
		emptyPrescription.setRenewal(A_RENEWAL);
		assertFalse(emptyPrescription.isValid());
	}

	@Test
	public void onPeutAjouterUneDateAUnePrescription()
			throws InvalidDateFormatException, ParseException {
		emptyPrescription.setDate(NOW);
		assertFalse(emptyPrescription.isValid());
	}

	@Test
	public void UnePrescriptionCompleteEstValide() {
		assertTrue(filledPrescription.isValid());
	}

}
