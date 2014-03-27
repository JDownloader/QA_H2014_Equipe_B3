package ca.ulaval.glo4002.services.prescription;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.drug.*;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.prescription.*;
import ca.ulaval.glo4002.domain.staff.StaffMember;
import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.persistence.drug.HibernateDrugRepository;
import ca.ulaval.glo4002.persistence.patient.HibernatePatientRepository;
import ca.ulaval.glo4002.persistence.prescription.HibernatePrescriptionRepository;
import ca.ulaval.glo4002.rest.requestparsers.prescription.PrescriptionCreationRequestParser;

public class PrescriptionService {
	public static final String ERROR_SERVICE_REQUEST_EXCEPTION_PRES001 = "PRES001";

	private PrescriptionRepository prescriptionRepository =  new HibernatePrescriptionRepository();;
	private DrugRepository drugRepository =  new HibernateDrugRepository();
	private PatientRepository patientRepository = new HibernatePatientRepository();
	private EntityTransaction entityTransaction = new EntityManagerProvider().getEntityManager().getTransaction();
	
	public PrescriptionService() {	
	}

	public void addPrescription(PrescriptionCreationRequestParser requestParser) throws ServiceRequestException, Exception {
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

	protected void doAddPrescription(PrescriptionCreationRequestParser requestParser) throws ServiceRequestException, Exception {
		Prescription prescription = buildPrescription(requestParser);
		prescriptionRepository.persist(prescription);
		updatePatient(requestParser, prescription);
	}

	private Prescription buildPrescription(PrescriptionCreationRequestParser requestParser) throws ServiceRequestException, Exception {
		PrescriptionAssembler prescriptionBuilder = new PrescriptionAssembler().date(requestParser.getDate()).allowedNumberOfRenewal(requestParser.getRenewals())
				.staffMember(new StaffMember(requestParser.getStaffMember())).drugName(requestParser.getDrugName());

		if (requestParser.hasDin()) {
			Drug drug = getDrug(requestParser);
			prescriptionBuilder.drug(drug);
		}

		return prescriptionBuilder.build();
	}

	private Drug getDrug(PrescriptionCreationRequestParser requestParser) throws ServiceRequestException, Exception {
		try {
			return drugRepository.getByDin(new Din(requestParser.getDin()));
		} catch (EntityNotFoundException e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_PRES001, e.getMessage());
		}
	}

	private void updatePatient(PrescriptionCreationRequestParser requestParser, Prescription prescription) throws ServiceRequestException {
		Patient patient = getPatient(requestParser);
		patient.addPrescription(prescription);
		patientRepository.update(patient);
	}

	private Patient getPatient(PrescriptionCreationRequestParser requestParser) throws ServiceRequestException {
		try {
			return patientRepository.getById(requestParser.getPatientNumber());
		} catch (EntityNotFoundException e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_PRES001, e.getMessage());
		}
	}

}
