package ca.ulaval.glo4002.domain.drug;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.drug.Din;
import ca.ulaval.glo4002.domain.drug.Drug;

public class DrugTest {
	private static final Din NULL_DIN = null;

	private static final String NAME_OF_DRUG = "Drug";
	private static final Din DIN_OF_DRUG = new Din(5);
	Drug drug;

	@Before
	public void init() {
		drug = new DrugBuilder().din(DIN_OF_DRUG).name(NAME_OF_DRUG).build();
	}

	@Test
	public void aNewDrugWithoutDinReturnsANullDin() {
		Drug medicamentSansDin = new DrugBuilder().name("drug without Din").build();
		assertEquals(NULL_DIN, medicamentSansDin.getDin());
	}
	
	@Test(expected = IllegalStateException.class)
	public void disallowsEmptyNameAndNullDin() {
		drug = new DrugBuilder().build();
	}
	
	@Test
	public void returnsTheCorrectName() {
		assertEquals(NAME_OF_DRUG, drug.getName());
	}

	@Test
	public void returnsTheCorrectDin() {
		assertEquals(DIN_OF_DRUG, drug.getDin());
	}
}
