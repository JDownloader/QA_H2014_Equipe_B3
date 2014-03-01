package ca.ulaval.glo4002.domain.staff;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.staff.StaffMember;

public class StaffMemberTest {

	private static final int A_LICENSE_NUMBER = 50;
	private StaffMember staffMember;

	@Before
	public void setup() {
		staffMember = new StaffMember(A_LICENSE_NUMBER);
	}
	
	@Test
	public void staffReturnsTheCorrectLicenseNumber() {
		assertEquals(A_LICENSE_NUMBER, staffMember.getLicenseNumber());
	}

}
