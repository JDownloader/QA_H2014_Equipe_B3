package ca.ulaval.glo4002.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.drug.*;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.prescription.*;
import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.persistence.HibernateDrugRepository;
import ca.ulaval.glo4002.persistence.HibernatePatientRepository;
import ca.ulaval.glo4002.persistence.HibernatePrescriptionRepository;
import ca.ulaval.glo4002.rest.dto.PrescriptionCreationDto;
import ca.ulaval.glo4002.rest.dto.validators.PrescriptionCreationDtoValidator;

public class PatientService {
	public static final String ERROR_SERVICE_REQUEST_EXCEPTION_PRES001 = "PRES001";

	private PatientRepository patientRepository;
	private PrescriptionRepository prescriptionRepository;
	private DrugRepository drugRepository;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	
	public PatientService() {
		patientRepository = new HibernatePatientRepository();
		prescriptionRepository =  new HibernatePrescriptionRepository();
		drugRepository =  new HibernateDrugRepository();
		entityManager = new EntityManagerProvider().getEntityManager();
		entityTransaction = entityManager.getTransaction();
	}
	
	public PatientService(PatientRepository patientRepository, PrescriptionRepository prescriptionRepository, DrugRepository drugRepository, EntityManager entityManager) {
		this.patientRepository = patientRepository;
		this.prescriptionRepository =  prescriptionRepository;
		this.drugRepository =  drugRepository;
		this.entityManager = entityManager;
		this.entityTransaction = entityManager.getTransaction();
	}

	public void createPrescription(PrescriptionCreationDto prescriptionCreationDto, PrescriptionCreationDtoValidator prescriptionCreationDtoValidator, PrescriptionAssembler prescriptionAssembler) throws ServiceRequestException {
		try {
			entityTransaction.begin();
			prescriptionCreationDtoValidator.validate(prescriptionCreationDto);
			executePrescriptionCreationUseCase(prescriptionCreationDto, prescriptionAssembler);
			entityTransaction.commit();
		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_PRES001, e.getMessage());
		}
	}

	protected void executePrescriptionCreationUseCase(PrescriptionCreationDto prescriptionCreationDto, PrescriptionAssembler prescriptionAssembler) throws ServiceRequestException {
		Prescription prescription = prescriptionAssembler.assemblePrescription(prescriptionCreationDto, drugRepository);
		prescriptionRepository.persist(prescription);
		Patient patient = patientRepository.getById(prescriptionCreationDto.getPatientNumber());
		patient.addPrescription(prescription);
		patientRepository.update(patient);
	}

}
