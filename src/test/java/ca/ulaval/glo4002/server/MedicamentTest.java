package ca.ulaval.glo4002.server;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.server.Medicament;

public class MedicamentTest {
	private static final int NULL_DIN = 0;

	private static final String NOM_DUN_MEDICAMENT = "Medicament";
	private static final int DIN_DUN_MEDICAMENT = 5;
	Medicament medicament;

	@Before
	public void init() {
		medicament = new Medicament(DIN_DUN_MEDICAMENT, NOM_DUN_MEDICAMENT);
	}

	@Test
	public void unNouveauMedicamentEstCree() {
		assertNotNull(medicament);
	}

	@Test
	public void unNouveauMedicamentSansDinEstCree() {
		assertNotNull(new Medicament(NOM_DUN_MEDICAMENT));
	}

	@Test
	public void unNouveauMedicamentAUnIdUnique() {
		Medicament autreMedicament = new Medicament("Autre medicament");
		assertTrue(autreMedicament.getId() != medicament.getId());
	}

	@Test
	public void unNouveauMedicamentSansDinRetourneUnDinNull() {
		Medicament medicamentSansDin = new Medicament("Medicament sans DIN");
		assertEquals(NULL_DIN, medicamentSansDin.getDin());
	}

	@Test
	public void unNouveauMedicamentRetourneLeBonNom() {
		assertEquals(NOM_DUN_MEDICAMENT, medicament.getNom());
	}

	@Test
	public void unNouveauMedicamentRetourneLeBonDin() {
		assertEquals(DIN_DUN_MEDICAMENT, medicament.getDin());
	}
}
