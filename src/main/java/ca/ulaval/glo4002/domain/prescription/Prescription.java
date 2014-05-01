package ca.ulaval.glo4002.domain.prescription;

import java.util.Date;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.staff.StaffMember;

@SuppressWarnings("unused") //Suppresses warning for private attributes used by Hibernate persistence
@Entity(name = "PRESCRIPTION")
public class Prescription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne()
	private Drug drug;
	private String drugName;
	private Integer allowedNumberOfRenewals;
	private Date date;
	private StaffMember staffMember;

	protected Prescription() {
		// Required for Hibernate
	}

	public Prescription(Drug drug, Integer allowedNumberOfRenewals, Date date, StaffMember staffMember) {
		this.drug = drug;
		this.staffMember = staffMember;
		this.allowedNumberOfRenewals = allowedNumberOfRenewals;
		this.date = date;
	}
	
	public Prescription(String drugName, Integer allowedNumberOfRenewal, Date date, StaffMember staffMember) {
		this.drugName = drugName;
		this.staffMember = staffMember;
		this.allowedNumberOfRenewals = allowedNumberOfRenewal;
		this.date = date;
	}
	
	public boolean isPrescriptionInteractive(Prescription prescription) {
		if (drug != null) {
			return drug.isDrugInteractive(prescription.drug);
		}
		return false;
	}
}
