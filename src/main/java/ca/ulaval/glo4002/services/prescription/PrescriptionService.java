package ca.ulaval.glo4002.services.prescription;

import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.drug.*;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.prescription.*;
import ca.ulaval.glo4002.domain.staff.StaffMember;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.prescription.AddPrescriptionRequestParser;

public class PrescriptionService {
	public static final String ERROR_SERVICE_REQUEST_EXCEPTION_PRES001 = "PRES001";

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

	public void addPrescription(AddPrescriptionRequestParser requestParser) throws ServiceRequestException, Exception {
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

	protected void doAddPrescription(AddPrescriptionRequestParser requestParser) throws ServiceRequestException, Exception {
		Prescription prescription = buildPrescription(requestParser);
		prescriptionRepository.create(prescription);
		updatePatient(requestParser, prescription);
	}

	private Prescription buildPrescription(AddPrescriptionRequestParser requestParser) throws ServiceRequestException, Exception {
		PrescriptionBuilder prescriptionBuilder = new PrescriptionBuilder()
			.date(requestParser.getDate())
			.allowedNumberOfRenewal(requestParser.getRenewals())
			.staffMember(new StaffMember(requestParser.getStaffMember()))
			.drugName(requestParser.getDrugName());

		if (requestParser.hasDin()) {
			Drug drug = getDrug(requestParser);
			prescriptionBuilder.drug(drug);
		}

		return prescriptionBuilder.build();
	}

	private Drug getDrug(AddPrescriptionRequestParser requestParser) throws ServiceRequestException, Exception {
		try {
			return drugRepository.getByDin(new Din(requestParser.getDin()));
		} catch (EntityNotFoundException e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_PRES001, e.getMessage());
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
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_PRES001, e.getMessage());
		}
	}

}
