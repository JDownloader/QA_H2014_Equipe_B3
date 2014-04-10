package ca.ulaval.glo4002.domain.prescription;

import java.util.Date;

import javax.persistence.*;

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

	public Prescription(Drug drug, int allowedNumberOfRenewal, Date date, StaffMember staffMember) {
		this.drug = drug;
		this.staffMember = staffMember;
		this.allowedNumberOfRenewal = allowedNumberOfRenewal;
		this.date = date;
	}
	
	public Prescription(String drugName, int allowedNumberOfRenewal, Date date, StaffMember staffMember) {
		this.drugName = drugName;
		this.staffMember = staffMember;
		this.allowedNumberOfRenewal = allowedNumberOfRenewal;
		this.date = date;
	}

	public Drug getDrug() {
		return drug;
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

}
