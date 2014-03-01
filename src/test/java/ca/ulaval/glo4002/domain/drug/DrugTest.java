package ca.ulaval.glo4002.domain.drug;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.drug.Din;
import ca.ulaval.glo4002.domain.drug.Drug;

public class DrugTest {
	private static final Din NULL_DIN = null;

	private static final String SAMPLE_DRUG_NAME = "Drug";
	private static final Din SAMPLE_DIN = new Din(5);
	private Drug drug;

	@Before
	public void setup() {
		drug = new DrugBuilder().din(SAMPLE_DIN).name(SAMPLE_DRUG_NAME).build();
	}

	@Test
	public void newDrugWithNoDinReturnsNullDin() {
		Drug drugWithNoDin = new DrugBuilder().name("drug without Din").build();
		assertEquals(NULL_DIN, drugWithNoDin.getDin());
	}
	
	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedNameAndDin() {
		new DrugBuilder().build();
	}
	
	@Test
	public void returnsTheCorrectName() {
		assertEquals(SAMPLE_DRUG_NAME, drug.getName());
	}

	@Test
	public void returnsTheCorrectDin() {
		assertEquals(SAMPLE_DIN, drug.getDin());
	}
}
