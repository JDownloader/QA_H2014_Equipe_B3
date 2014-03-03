package ca.ulaval.glo4002.domain.drug;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DrugTest {
	private static final Din SAMPLE_DIN = new Din(5);
	private static final String EMPTY_DESCRPTION = "";
	private static final String SAMPLE_DRUG_NAME = "Drug";
	private static final String SAMPLE_DESCRIPTION = "Description";

	private Drug drugWithDescription;
	private Drug drugWithoutDescription;

	@Before
	public void init() {
		drugWithDescription = new DrugFactory().createDrug(SAMPLE_DIN, SAMPLE_DRUG_NAME, SAMPLE_DESCRIPTION);
		drugWithoutDescription = new DrugFactory().createDrug(SAMPLE_DIN, SAMPLE_DRUG_NAME);
	}

	@Test(expected = DrugDoesntHaveDinException.class)
	public void newDrugWithNoDinThrowExeption() throws DrugDoesntHaveDinException {
		Drug drugWhitoutDin = new DrugFactory().createDrug(SAMPLE_DRUG_NAME);
		drugWhitoutDin.getDin();
	}

	@Test
	public void returnsCorrectName() {
		assertEquals(SAMPLE_DRUG_NAME, drugWithDescription.getName());
	}

	@Test
	public void drugWithoutDescriptionReturnsEmptyDescription() {
		assertEquals(EMPTY_DESCRPTION, drugWithoutDescription.getDescription());
	}

	@Test
	public void returnsCorrectDin() throws DrugDoesntHaveDinException {
		assertEquals(SAMPLE_DIN, drugWithDescription.getDin());
	}

	@Test
	public void returnsCorrectDescription() {
		assertEquals(SAMPLE_DESCRIPTION, drugWithDescription.getDescription());
	}
}
