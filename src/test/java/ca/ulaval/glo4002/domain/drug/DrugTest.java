package ca.ulaval.glo4002.domain.drug;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class DrugTest {
	private static final String SAMPLE_NAME = "SampleName";
	private static final String SAMPLE_DESCRIPTION = "SampleDescription";
	private static final String SAMPLE_DIN_VALUE1 = "SampleDin1";
	private static final String SAMPLE_DIN_VALUE2 = "SampleDin2";
	
	Drug baseDrug;
	Drug interactiveDrug;
	Set<Din> interactiveDins;
	Din sampleDin1;
	Din sampleDin2;
	
	@Before
	public void init() {
		sampleDin1 = new Din(SAMPLE_DIN_VALUE1);
		sampleDin2 = new Din(SAMPLE_DIN_VALUE2);
		
		interactiveDins = new HashSet<Din>();
		
		baseDrug = new Drug(sampleDin1, SAMPLE_NAME, SAMPLE_DESCRIPTION);
		interactiveDrug = new Drug(sampleDin2, SAMPLE_NAME, SAMPLE_DESCRIPTION);
	}
	
	@Test
	public void detectsInteractionWhenInteractiveDinListOfBaseDrugContainsInteractiveDrug() {
		interactiveDins.add(sampleDin2);
		baseDrug.setInteractiveDinList(interactiveDins);
		assertTrue(baseDrug.isDrugInteractive(interactiveDrug));
	}
	
	@Test
	public void detectsInteractionWhenInteractiveDinListOfInteractiveDrugContainsBaseDrug() {
		interactiveDins.add(sampleDin1);
		interactiveDrug.setInteractiveDinList(interactiveDins);
		assertTrue(baseDrug.isDrugInteractive(interactiveDrug));
	}
	
	@Test
	public void ignoresInteractionWhenDrugsAreNotInteractive() {
		interactiveDins.add(sampleDin2);
		interactiveDrug.setInteractiveDinList(interactiveDins);
		assertFalse(baseDrug.isDrugInteractive(interactiveDrug));
	}
}
