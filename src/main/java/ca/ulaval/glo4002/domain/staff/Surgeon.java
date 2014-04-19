package ca.ulaval.glo4002.domain.staff;

import java.io.Serializable;

public final class Surgeon extends StaffMember implements Serializable {

	private static final long serialVersionUID = -2075692897885002844L;

	public Surgeon(String licenseNumber) {
		super(licenseNumber);
	}
}
