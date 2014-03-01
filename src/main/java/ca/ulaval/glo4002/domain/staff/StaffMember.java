package ca.ulaval.glo4002.domain.staff;

public class StaffMember {

	private int licenseNumber;
	
	public StaffMember(int licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public int getLicenseNumber() {
		return this.licenseNumber;
	}
}
