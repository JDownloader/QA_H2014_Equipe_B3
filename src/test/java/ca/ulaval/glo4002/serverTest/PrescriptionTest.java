package ca.ulaval.glo4002.serverTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.server.Medicament;
import ca.ulaval.glo4002.server.Prescription;
import ca.ulaval.glo4002.server.Staff;
import ca.ulaval.glo4002.error.FormatDeDateNonValide;

public class PrescriptionTest {

	public static final Medicament UN_MEDICAMENT = new Medicament("test");
	public static final int UN_RENOUVELLEMENT = 3;
	public static final String MAINTENANT = "2001-07-04T12:08:56";
	public static final String UNE_MAUVAISE_DATE = "28-04-1989234-23";
	public static final Staff UN_INTERVENANT = new Staff(1);

	Prescription prescriptionVide;
	Prescription prescriptionPleine;

	@Before
	public void init() throws FormatDeDateNonValide, ParseException {
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
		Prescription autrePrescription = new Prescription(UN_MEDICAMENT, UN_INTERVENANT);
		assertFalse(prescriptionVide.getId() == autrePrescription.getId());
	}

	@Test(expected = FormatDeDateNonValide.class)
	public void unePrescriptionNePrendPasLeMauvaisFormatDeDate() throws FormatDeDateNonValide, ParseException {
		prescriptionVide.setDate(UNE_MAUVAISE_DATE);
	}

	@Test
	public void unePrescriptionVideNestPasValide() {
		assertFalse(prescriptionVide.getValid());
	}

	@Test
	public void onPeutAjouterUnRenouvellementAUnePrescription() {
		prescriptionVide.setRenouvellement(UN_RENOUVELLEMENT);
		assertFalse(prescriptionVide.getValid());
	}

	@Test
	public void onPeutAjouterUneDateAUnePrescription() throws FormatDeDateNonValide, ParseException {
		prescriptionVide.setDate(MAINTENANT);
		assertFalse(prescriptionVide.getValid());
	}

	@Test
	public void UnePrescriptionCompleteEstValide() {
		assertTrue(prescriptionPleine.getValid());
	}

}
