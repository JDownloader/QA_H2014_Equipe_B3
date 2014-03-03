package ca.ulaval.glo4002.domain.drug;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.drug.DrugFactory;
import ca.ulaval.glo4002.domain.drug.DrugDontHaveDinExeption;

public class DrugTest {
	private static final Din NULL_DIN = null;

	private static final Din SAMPLE_DIN = new Din(5);
	private static final String EMPTY_DESCRPTION = "";
	private static final String SAMPLE_DRUG_NAME = "Drug";
	private static final String SAMPLE_DESCRIPTION = "Description";
	
	private Drug drugWithDescription;
	private Drug drugWithoutDescription;

	@Before
	public void init() {
		drugWithDescription = new DrugFactory().createDrug(SAMPLE_DIN, SAMPLE_DRUG_NAME, SAMPLE_DESCRIPTION);
		drugWithoutDescription  = new DrugFactory().createDrug(SAMPLE_DIN, SAMPLE_DRUG_NAME);
	}

	@Test(expected = DrugDontHaveDinExeption.class)
	public void newDrugWithNoDinThrowExeption() throws DrugDontHaveDinExeption {
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
	public void returnsCorrectDin() throws DrugDontHaveDinExeption {
		assertEquals(SAMPLE_DIN, drugWithDescription.getDin());
	}
	
	@Test
	public void returnsCorrectDescription() {
		assertEquals(SAMPLE_DESCRIPTION, drugWithDescription.getDescription());
	}
}
