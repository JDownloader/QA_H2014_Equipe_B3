package ca.ulaval.glo4002.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class PrescriptionTest {

	public static final Medicament UN_MEDICAMENT = new Medicament();
	public static final int UN_RENOUVELLEMENT = 3;
	public static final String MAINTENANT = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	public static final Intervenant UN_INTERVENANT = new Intervenant();
	public static final Patient UN_PATIENT = new Patient();

	Prescription prescription;

	@Before
	public void init() {
		prescription = new Prescription();
	}

	@Test
	public void unePrescriptionEstCree() {
		assertNotNull(prescription);
	}

	@Test
	public void unePrescriptionAUnIdUnique() {
		Prescription autrePrescription = new Prescription();
		assertFalse(prescription.getId() == autrePrescription.getId());
	}

	@Test
	public void unePrescriptionVideNestPasValide() {
		assertFalse(prescription.getValid());
	}

	@Test
	public void onPeutAjouterUnMedicamentAUnePrescription() {

	}

}
