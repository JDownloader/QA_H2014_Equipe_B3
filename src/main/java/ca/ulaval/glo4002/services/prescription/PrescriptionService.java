package ca.ulaval.glo4002.services.prescription;

import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.drug.*;
import ca.ulaval.glo4002.domain.patient.*;
import ca.ulaval.glo4002.domain.prescription.*;
import ca.ulaval.glo4002.domain.staff.StaffMember;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.prescription.AddPrescriptionRequestParser;

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
	
	public void addPrescription(AddPrescriptionRequestParser requestParser) throws ServiceRequestException, DrugDontHaveDinExeption {
		try {
			entityTransaction.begin();
			doAddPrescription(requestParser);
			entityTransaction.commit();
		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw e;
		}
	}
	
	protected void doAddPrescription(AddPrescriptionRequestParser requestParser) throws ServiceRequestException, DrugDontHaveDinExeption {
		Prescription prescription = buildPrescription(requestParser);
		prescriptionRepository.create(prescription);
		updatePatient(requestParser, prescription);
	}

	private Prescription buildPrescription(AddPrescriptionRequestParser requestParser) throws ServiceRequestException, DrugDontHaveDinExeption {
		PrescriptionBuilder prescriptionBuilder = new PrescriptionBuilder();
		prescriptionBuilder.date(requestParser.getDate());
		prescriptionBuilder.allowedNumberOfRenewal(requestParser.getRenewals());
		prescriptionBuilder.staffMember(new StaffMember(requestParser.getStaffMember()));
		prescriptionBuilder.drugName(requestParser.getDrugName());
		if (requestParser.hasDin()) {
			Drug drug = getDrug(requestParser);
			prescriptionBuilder.drug(drug);	
		}
		Prescription prescription = prescriptionBuilder.build();
		return prescription;
	}
	
	private Drug getDrug(AddPrescriptionRequestParser requestParser) throws ServiceRequestException, DrugDontHaveDinExeption {
		try {
			return drugRepository.getByDin(new Din(requestParser.getDin()));
		} catch (EntityNotFoundException e) {
			throw new ServiceRequestException("PRES001", e.getMessage());
		}
	}

	private void updatePatient(AddPrescriptionRequestParser requestParser, Prescription prescription) throws ServiceRequestException {
		Patient patient = getPatient(requestParser);
		patient.addPrescription(prescription);
		patientRepository.update(patient);
	}
	
	private Patient getPatient(AddPrescriptionRequestParser requestParser) throws ServiceRequestException {
		try {
			return patientRepository.getById(requestParser.getPatientNumber());
		} catch (EntityNotFoundException e) {
			throw new ServiceRequestException("PRES001", e.getMessage());
		}
	}
	
}
