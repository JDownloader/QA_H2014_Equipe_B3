package ca.ulaval.glo4002.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.drug.DrugNotFoundException;
import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.domain.patient.*;
import ca.ulaval.glo4002.domain.prescription.Prescription;
import ca.ulaval.glo4002.domain.prescription.PrescriptionRepository;
import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;
import ca.ulaval.glo4002.persistence.*;
import ca.ulaval.glo4002.services.assemblers.PrescriptionAssembler;
import ca.ulaval.glo4002.services.dto.PrescriptionCreationDTO;
import ca.ulaval.glo4002.services.dto.validators.DTOValidationException;
import ca.ulaval.glo4002.services.dto.validators.PrescriptionCreationDTOValidator;

public class PatientService {
	public static final String ERROR_PRES001 = "PRES001";

	private PatientRepository patientRepository;
	private PrescriptionRepository prescriptionRepository;
	private DrugRepository drugRepository;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;

	public PatientService() {
		patientRepository = new HibernatePatientRepository();
		prescriptionRepository = new HibernatePrescriptionRepository();
		drugRepository = new HibernateDrugRepository();
		entityManager = new EntityManagerProvider().getEntityManager();
		entityTransaction = entityManager.getTransaction();
	}

	public PatientService(PatientRepository patientRepository, PrescriptionRepository prescriptionRepository, DrugRepository drugRepository,
			EntityManager entityManager) {
		this.patientRepository = patientRepository;
		this.prescriptionRepository = prescriptionRepository;
		this.drugRepository = drugRepository;
		this.entityManager = entityManager;
		this.entityTransaction = entityManager.getTransaction();
	}

	public void createPrescription(PrescriptionCreationDTO prescriptionCreationDTO, PrescriptionCreationDTOValidator prescriptionCreationDTOValidator,
			PrescriptionAssembler prescriptionAssembler) throws ServiceRequestException {
		try {
			prescriptionCreationDTOValidator.validate(prescriptionCreationDTO);
			entityTransaction.begin();

			doCreatePrescription(prescriptionCreationDTO, prescriptionAssembler);

			entityTransaction.commit();
		} catch (DTOValidationException | PatientNotFoundException | DrugNotFoundException e) {
			throw new ServiceRequestException(ERROR_PRES001, e.getMessage());
		} finally {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
		}
	}

	protected void doCreatePrescription(PrescriptionCreationDTO prescriptionCreationDTO, PrescriptionAssembler prescriptionAssembler) {
		Prescription prescription = prescriptionAssembler.assembleFromDTO(prescriptionCreationDTO, drugRepository);
		prescriptionRepository.persist(prescription);
		Patient patient = patientRepository.getById(prescriptionCreationDTO.patientNumber);
		patient.addPrescription(prescription);
		patientRepository.update(patient);
	}

}
