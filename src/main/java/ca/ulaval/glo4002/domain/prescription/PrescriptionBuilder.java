package ca.ulaval.glo4002.domain.prescription;

import java.util.Date;

import org.h2.util.StringUtils;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.staff.StaffMember;

public class PrescriptionBuilder {
	private static final int UNSPECIFIED = -1;
	
	protected Drug drug = null;
	protected String drugName = null;
	protected int renewals = UNSPECIFIED;
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
	
	public PrescriptionBuilder renewals(int renewals) {
		this.renewals = renewals;
		return this;
	}
	
	public PrescriptionBuilder date(Date date) {
		this.date = date;
		return this;
	}
	
	public PrescriptionBuilder prescriber(StaffMember prescriber) {
		this.staffMember = prescriber;
		return this;
	}
	
	public Prescription build() {
		Prescription prescription = new Prescription(this);
		if (staffMember == null
				|| renewals == UNSPECIFIED
				|| date == null
				|| (drug == null && StringUtils.isNullOrEmpty(drugName))) {
			throw new IllegalStateException();
		}
        return prescription;
    }
}
