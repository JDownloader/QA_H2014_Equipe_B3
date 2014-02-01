package ca.ulaval.glo4002.drug;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DrugTest {
	private static final int NULL_DIN = 0;

	private static final String DRUG_NAME = "Drug";
	private static final int DRUG_DIN = 5;
	
	Drug drug;

	@Before
	public void init() {
		drug = new Drug(DRUG_DIN, DRUG_NAME);
	}

	@Test
	public void aNewDrugIsBeingCreated() {
		assertNotNull(drug);
	}

	@Test
	public void aNewDrugWithoutDinIsBeingCreated() {
		assertNotNull(new Drug(DRUG_NAME));
	}

	@Test
	public void aNewDrugHasAUniqueId() {
		Drug anotherDrug = new Drug("another drug");
		assertTrue(anotherDrug.getId() != drug.getId());
	}

	@Test
	public void aNewDrugWithoutDinReturnsANullDin() {
		Drug drugWithoutDin = new Drug("drug without DIN");
		assertEquals(NULL_DIN, drugWithoutDin.getDin());
	}

	@Test
	public void aNewDrugReturnsTheRightName() {
		assertEquals(DRUG_NAME, drug.getName());
	}

	@Test
	public void aNewDrugReturnsTheRightDin() {
		assertEquals(DRUG_DIN, drug.getDin());
	}
}
