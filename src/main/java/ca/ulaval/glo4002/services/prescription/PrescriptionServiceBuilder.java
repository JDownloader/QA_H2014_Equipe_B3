package ca.ulaval.glo4002.services.prescription;

import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.prescription.PrescriptionRepository;

public class PrescriptionServiceBuilder {
	protected PrescriptionRepository prescriptionRepository = null;
	protected DrugRepository drugRepository = null;
	protected PatientRepository patientRepository = null;
	protected EntityTransaction entityTransaction = null;

	public PrescriptionServiceBuilder prescriptionRepository(PrescriptionRepository prescriptionRepository) {
		this.prescriptionRepository = prescriptionRepository;
		return this;
	}

	public PrescriptionServiceBuilder drugRepository(DrugRepository drugRepository) {
		this.drugRepository = drugRepository;
		return this;
	}

	public PrescriptionServiceBuilder patientRepository(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
		return this;
	}

	public PrescriptionServiceBuilder entityTransaction(EntityTransaction entityTransaction) {
		this.entityTransaction = entityTransaction;
		return this;
	}

	public PrescriptionService build() {
		PrescriptionService prescriptionService = new PrescriptionService(this);
		if (prescriptionRepository == null || drugRepository == null || patientRepository == null || entityTransaction == null) {
			throw new IllegalStateException();
		}
		return prescriptionService;
	}
}
