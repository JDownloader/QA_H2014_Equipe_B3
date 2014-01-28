package ca.ulaval.glo4002.drug;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.drug.Drug;

public class DrugTest {
	private static final int NULL_DIN = 0;

	private static final String NOM_DUN_MEDICAMENT = "Medicament";
	private static final int DIN_DUN_MEDICAMENT = 5;
	Drug medicament;

	@Before
	public void init() {
		medicament = new Drug(DIN_DUN_MEDICAMENT, NOM_DUN_MEDICAMENT);
	}

	@Test
	public void unNouveauMedicamentEstCree() {
		assertNotNull(medicament);
	}

	@Test
	public void unNouveauMedicamentSansDinEstCree() {
		assertNotNull(new Drug(NOM_DUN_MEDICAMENT));
	}

	@Test
	public void unNouveauMedicamentAUnIdUnique() {
		Drug autreMedicament = new Drug("Autre medicament");
		assertTrue(autreMedicament.getId() != medicament.getId());
	}

	@Test
	public void unNouveauMedicamentSansDinRetourneUnDinNull() {
		Drug medicamentSansDin = new Drug("Medicament sans DIN");
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
