package ca.ulaval.glo4002.drug;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DrugTest {
	private static final int NULL_DIN = 0;

	private static final String NAME_OF_DRUG = "Drug";
	private static final int DIN_OF_DRUG = 5;
	Drug drug;

	@Before
	public void init() {
		drug = new Drug(DIN_OF_DRUG, NAME_OF_DRUG);
	}

	@Test
	public void unNouveauMedicamentEstCree() {
		assertNotNull(drug);
	}

	@Test
	public void unNouveauMedicamentSansDinEstCree() {
		assertNotNull(new Drug(NAME_OF_DRUG));
	}

	/*
	 * @Test public void unNouveauMedicamentAUnIdUnique() { Drug autreMedicament
	 * = new Drug("other drug"); assertTrue(autreMedicament.getId() !=
	 * drug.getId()); }
	 */

	@Test
	public void unNouveauMedicamentSansDinRetourneUnDinNull() {
		Drug medicamentSansDin = new Drug("drug without DIN");
		assertEquals(NULL_DIN, medicamentSansDin.getDin());
	}

	@Test
	public void unNouveauMedicamentRetourneLeBonNom() {
		assertEquals(NAME_OF_DRUG, drug.getName());
	}

	@Test
	public void unNouveauMedicamentRetourneLeBonDin() {
		assertEquals(DIN_OF_DRUG, drug.getDin());
	}
}
