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
	@JoinColumn(name = "DRUG")
	private Drug drug;

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

	public boolean compareId(int id) {
		return this.id == id;
	}

	public int getId() {
		return this.id;
	}

	public Drug getDrug() {
		return this.drug;
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
