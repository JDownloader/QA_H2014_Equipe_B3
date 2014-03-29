package ca.ulaval.glo4002.domain.prescription;

import java.util.Date;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.staff.StaffMember;

@Entity(name = "PRESCRIPTION")
public class Prescription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne()
	private Drug drug;
	private String drugName;
	private int allowedNumberOfRenewal;
	private Date date;
	private StaffMember staffMember;

	protected Prescription() {
		// Required for Hibernate
	}

	public Prescription(Builder builder) {
		this.drug = builder.drug;
		this.drugName = builder.drugName;
		this.staffMember = builder.staffMember;
		this.allowedNumberOfRenewal = builder.allowedNumberOfRenewal;
		this.date = builder.date;
	}
	
	public String getDrugName() {
		return drugName;
	}

	public int getAllowedNumberOfRenewal() {
		return allowedNumberOfRenewal;
	}
	
	public Date getDate() {
		return date;
	}
	public StaffMember getStaffMember() {
		return staffMember;
	}

	public static class Builder {
		private Drug drug;
		private String drugName;
		private int allowedNumberOfRenewal;
		private Date date;
		private StaffMember staffMember;
		
		public Builder(int allowedNumberOfRenewal, Date date, StaffMember staffMember) {
			this.staffMember = staffMember;
			this.allowedNumberOfRenewal = allowedNumberOfRenewal;
			this.date = date;
		}
		
		public Builder withDrug(Drug drug) {
			this.drug = drug;
			return this;
		}
		
		public Builder withDrugName(String drugName) {
			this.drugName = drugName;
			return this;
		}
		
		public Prescription build() {
			if (!(drug == null ^ StringUtils.isBlank(drugName))) {
				throw new IllegalStateException("Either parameter 'drug' or 'drugName' must be specified.");
			}
			return new Prescription(this);
		}
	}
}
