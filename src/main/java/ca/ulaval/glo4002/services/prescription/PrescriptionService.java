package ca.ulaval.glo4002.services.prescription;

import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.drug.*;
import ca.ulaval.glo4002.domain.patient.*;
import ca.ulaval.glo4002.domain.prescription.*;
import ca.ulaval.glo4002.domain.staff.StaffMember;
import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.rest.requests.AddPrescriptionRequest;

public class PrescriptionService {
	private PrescriptionRepository prescriptionRepository;
	private DrugRepository drugRepository;
	private PatientRepository patientRepository;
	private EntityTransaction entityTransaction;
	
	public PrescriptionService(PrescriptionServiceBuilder builder) {
		this.entityTransaction = builder.entityTransaction;
		this.prescriptionRepository = builder.prescriptionRepository;
		this.drugRepository = builder.drugRepository;
		this.patientRepository = builder.patientRepository;
	}
	
	public void addPrescription(AddPrescriptionRequest prescriptionRequest) throws BadRequestException {
		try {
			entityTransaction.begin();
			doAddPrescription(prescriptionRequest);
			entityTransaction.commit();
		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw e;
		}
	}
	
	protected void doAddPrescription(AddPrescriptionRequest prescriptionRequest) throws BadRequestException {
		Prescription prescription = createPrescription(prescriptionRequest);
		updatePatient(prescriptionRequest, prescription);
	}

	private Prescription createPrescription(AddPrescriptionRequest prescriptionRequest) throws BadRequestException {
		PrescriptionBuilder prescriptionBuilder = new PrescriptionBuilder();
		prescriptionBuilder.date(prescriptionRequest.getDate());
		prescriptionBuilder.allowedNumberOfRenewal(prescriptionRequest.getRenewals());
		prescriptionBuilder.staffMember(new StaffMember(prescriptionRequest.getStaffMember()));
		prescriptionBuilder.drugName(prescriptionRequest.getDrugName());
		if (prescriptionRequest.hasDin()) {
			Drug drug = getDrug(prescriptionRequest);
			prescriptionBuilder.drug(drug);	
		}
		Prescription prescription = prescriptionBuilder.build();
		prescriptionRepository.create(prescription);
		return prescription;
	}
	
	private Drug getDrug(AddPrescriptionRequest prescriptionRequest) throws BadRequestException {
		try {
			return drugRepository.getByDin(new Din(prescriptionRequest.getDin()));
		} catch (EntityNotFoundException e) {
			throw new BadRequestException("PRES001", e.getMessage());
		}
	}

	private void updatePatient(AddPrescriptionRequest prescriptionRequest, Prescription prescription) throws BadRequestException {
		Patient patient = getPatient(prescriptionRequest);
		patient.addPrescription(prescription);
		patientRepository.update(patient);
	}
	
	private Patient getPatient(AddPrescriptionRequest prescriptionRequest) throws BadRequestException {
		try {
			return patientRepository.getById(prescriptionRequest.getPatientNumber());
		} catch (EntityNotFoundException e) {
			throw new BadRequestException("PRES001", e.getMessage());
		}
	}
	
}
