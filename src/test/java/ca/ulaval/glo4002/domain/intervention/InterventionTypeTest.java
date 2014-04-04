package ca.ulaval.glo4002.domain.intervention;

import static org.junit.Assert.*;

import org.junit.Test;


public class InterventionTypeTest {
	private InterventionType type;
	
	@Test (expected=IllegalArgumentException.class)
	public void doNotBuildInvalidType() {
		type = InterventionType.fromString("");
	}
	
	@Test
	public void buildEyeType() {
		type = InterventionType.fromString( InterventionType.EYE.getValue() );
		assertEquals(type, InterventionType.EYE);
	}
	
	@Test
	public void buildHeartType() {
		type = InterventionType.fromString( InterventionType.HEART.getValue() );
		assertEquals(type, InterventionType.HEART);
	}
	
	@Test
	public void buildMarrowType() {
		type = InterventionType.fromString( InterventionType.MARROW.getValue() );
		assertEquals(type, InterventionType.MARROW);
	}
	
	@Test
	public void buildOncologicType() {
		type = InterventionType.fromString( InterventionType.ONCOLOGIC.getValue() );
		assertEquals(type, InterventionType.ONCOLOGIC);
	}
	
	@Test
	public void buildOtherType() {
		type = InterventionType.fromString( InterventionType.OTHER.getValue() );
		assertEquals(type, InterventionType.OTHER);
	}
}