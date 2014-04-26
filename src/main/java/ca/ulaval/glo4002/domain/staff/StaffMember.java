package ca.ulaval.glo4002.domain.staff;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class StaffMember implements Serializable {

	private static final long serialVersionUID = -7716343327245896940L;

	private String licenseNumber;

	protected StaffMember() {
		//Required for Hibernate
	}
	
	public StaffMember(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getLicenseNumber() {
		return this.licenseNumber;
	}

	@Override
	public int hashCode() {
		return licenseNumber.hashCode();
	};
	
	@Override
	public String toString() {
		return licenseNumber.toString();
	};

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StaffMember) {
			return licenseNumber.equals(((StaffMember) obj).licenseNumber);
		}
		return false;
	};
}
