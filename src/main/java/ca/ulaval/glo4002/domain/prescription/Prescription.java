package ca.ulaval.glo4002.domain.prescription;

import java.util.Date;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.staff.StaffMember;

@Entity(name = "PRESCRIPTION")
public class Prescription {

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
	private int allowedNumberOfRenewal;

	@Column(name = "DATE", nullable = false)
	private Date date;

	@Column(name = "STAFF_MEMBER", nullable = false)
	private StaffMember staffMember;

	protected Prescription() {
		//Required for Hibernate
	}
	
	public Prescription(PrescriptionBuilder builder) {
		this.drug = builder.drug;
		this.drugName = builder.drugName;
		this.staffMember = builder.staffMember;
		this.allowedNumberOfRenewal = builder.allowedNumberOfRenewal;
		this.date = builder.date;
	}
	
	public boolean compareId(int id) {
		return this.id == id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public Drug getDrug() {
		return this.drug;
	}
	
	public String getDrugName() {
		return this.drugName;
	}
	
	public int getAllowedNumberOfRenewal() {
		return this.allowedNumberOfRenewal;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public StaffMember getStaffMember() {
		return this.staffMember;
	}
}
