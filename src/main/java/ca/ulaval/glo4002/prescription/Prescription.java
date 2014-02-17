package ca.ulaval.glo4002.prescription;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.h2.util.StringUtils;

import ca.ulaval.glo4002.drug.Drug;
import ca.ulaval.glo4002.staff.StaffMember;

@Entity(name = "PRESCRIPTION")
public class Prescription {

	private static final int UNSPECIFIED = -1;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRESCRIPTION_ID", nullable = false)
	private int id;

	@ManyToOne()
	@JoinColumn(name = "DRUG", nullable = true)
	private Drug drug;
	
	@Column(name = "DRUG_NAME", nullable = true)
	private String drugName = null;

	@Column(name = "RENEWALS", nullable = false)
	private int renewals = UNSPECIFIED;

	@Column(name = "DATE", nullable = false)
	private Date date;

	@Column(name = "STAFF_MEMBER", nullable = false)
	private StaffMember staffMember;

	@SuppressWarnings("unused")
	private Prescription() {
		
	}
	
	public Prescription(Builder builder) {
		this.drug = builder.drug;
		this.drugName = builder.drugName;
		this.staffMember = builder.staffMember;
		this.renewals = builder.renewals;
		this.date = builder.date;
	}
	
	public static class Builder {
		private Drug drug = null;
		private String drugName = null;
		private int renewals = UNSPECIFIED;
		private Date date = null;
		private StaffMember staffMember = null;
		
		public Builder drug(Drug drug) {
			this.drug = drug;
			return this;
		}
		
		public Builder drugName(String drugName) {
			this.drugName = drugName;
			return this;
		}
		
		public Builder renewals(int renewals) {
			this.renewals = renewals;
			return this;
		}
		
		public Builder date(Date date) {
			this.date = date;
			return this;
		}
		
		public Builder prescriber(StaffMember prescriber) {
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
}
