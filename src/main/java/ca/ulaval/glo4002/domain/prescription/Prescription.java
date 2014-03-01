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
	private int renewals;

	@Column(name = "DATE", nullable = false)
	private Date date;

	@Column(name = "STAFF_MEMBER", nullable = false)
	private StaffMember staffMember;

	protected Prescription() {
		
	}
	
	public Prescription(PrescriptionBuilder builder) {
		this.drug = builder.drug;
		this.drugName = builder.drugName;
		this.staffMember = builder.staffMember;
		this.renewals = builder.renewals;
		this.date = builder.date;
	}
}
