package ca.ulaval.glo4002.staff;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StaffMemberTest {

	private static final int ID_NUMBER = 0;
	private StaffMember myStaffMember;

	@Before
	public void init() {
		myStaffMember = new StaffMember(0);
	}

	@Test
	public void emptyConstructorOfStaffMember() {
		assertNotNull(myStaffMember);
	}

	@Test
	public void getIdOfStaffMember() {
		assertEquals(ID_NUMBER, myStaffMember.getId());
	}

}
