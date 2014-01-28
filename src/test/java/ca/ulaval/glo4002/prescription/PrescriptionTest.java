package ca.ulaval.glo4002.prescription;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.drug.Drug;
import ca.ulaval.glo4002.exeptions.InvalidDateFormat;
import ca.ulaval.glo4002.staff.StaffMember;
import ca.ulaval.glo4002.prescription.Prescription;

public class PrescriptionTest {

	public static final Drug UN_MEDICAMENT = new Drug("test");
	public static final int UN_RENOUVELLEMENT = 3;
	public static final String MAINTENANT = "2001-07-04T12:08:56";
	public static final String UNE_MAUVAISE_DATE = "28-04-1989234-23";
	public static final StaffMember UN_INTERVENANT = new StaffMember();

	Prescription prescriptionVide;
	Prescription prescriptionPleine;

	@Before
	public void init() throws InvalidDateFormat, ParseException {
		prescriptionVide = new Prescription(UN_MEDICAMENT, UN_INTERVENANT);
		prescriptionPleine = new Prescription(UN_MEDICAMENT, UN_INTERVENANT);
		prescriptionPleine.setDate(MAINTENANT);
		prescriptionPleine.setRenouvellement(UN_RENOUVELLEMENT);
	}

	@Test
	public void unePrescriptionEstCree() {
		assertNotNull(prescriptionVide);
	}

	@Test
	public void unePrescriptionAUnIdUnique() {
		Prescription autrePrescription = new Prescription(UN_MEDICAMENT,
				UN_INTERVENANT);
		assertFalse(prescriptionVide.getId() == autrePrescription.getId());
	}

	@Test(expected = InvalidDateFormat.class)
	public void unePrescriptionNePrendPasLeMauvaisFormatDeDate()
			throws InvalidDateFormat, ParseException {
		prescriptionVide.setDate(UNE_MAUVAISE_DATE);
	}

	@Test
	public void unePrescriptionVideNestPasValide() {
		assertFalse(prescriptionVide.isValid());
	}

	@Test
	public void onPeutAjouterUnRenouvellementAUnePrescription() {
		prescriptionVide.setRenouvellement(UN_RENOUVELLEMENT);
		assertFalse(prescriptionVide.isValid());
	}

	@Test
	public void onPeutAjouterUneDateAUnePrescription()
			throws InvalidDateFormat, ParseException {
		prescriptionVide.setDate(MAINTENANT);
		assertFalse(prescriptionVide.isValid());
	}

	@Test
	public void UnePrescriptionCompleteEstValide() {
		assertTrue(prescriptionPleine.isValid());
	}

}
