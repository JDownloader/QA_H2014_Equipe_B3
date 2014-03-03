package ca.ulaval.glo4002.domain.staff;

import java.io.Serializable;

public class StaffMember implements Serializable {

	private static final long serialVersionUID = -7716343327245896940L;

	private int licenseNumber;

	public StaffMember(int licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public int getLicenseNumber() {
		return this.licenseNumber;
	}
}
