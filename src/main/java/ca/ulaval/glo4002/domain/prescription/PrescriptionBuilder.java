package ca.ulaval.glo4002.domain.prescription;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.staff.StaffMember;

public class PrescriptionBuilder {
	private static final int UNSPECIFIED = -1;
	protected Drug drug = null;
	protected String drugName = null;
	protected int allowedNumberOfRenewal = UNSPECIFIED;
	protected Date date = null;
	protected StaffMember staffMember = null;
	
	public PrescriptionBuilder drug(Drug drug) {
		this.drug = drug;
		return this;
	}
	
	public PrescriptionBuilder drugName(String drugName) {
		this.drugName = drugName;
		return this;
	}
	
	public PrescriptionBuilder allowedNumberOfRenewal(int allowedNumberOfRenewal) {
		this.allowedNumberOfRenewal = allowedNumberOfRenewal;
		return this;
	}
	
	public PrescriptionBuilder date(Date date) {
		this.date = date;
		return this;
	}
	
	public PrescriptionBuilder staffMember(StaffMember staffMember) {
		this.staffMember = staffMember;
		return this;
	}
	
	public Prescription build() {
		Prescription prescription = new Prescription(this);
		if (staffMember == null
				|| allowedNumberOfRenewal == UNSPECIFIED
				|| date == null
				|| !(drug == null ^ StringUtils.isBlank(drugName))) {
			throw new IllegalStateException();
		}
        return prescription;
    }
}
