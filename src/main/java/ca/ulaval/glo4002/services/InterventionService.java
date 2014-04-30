package ca.ulaval.glo4002.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.intervention.*;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalTool;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;
import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;
import ca.ulaval.glo4002.persistence.HibernateInterventionRepository;
import ca.ulaval.glo4002.persistence.HibernatePatientRepository;
import ca.ulaval.glo4002.services.assemblers.InterventionAssembler;
import ca.ulaval.glo4002.services.assemblers.SurgicalToolAssembler;
import ca.ulaval.glo4002.services.dto.*;
import ca.ulaval.glo4002.services.dto.validators.*;

public class InterventionService {

	private EntityManager entityManager;
	private EntityTransaction entityTransaction;

	private InterventionRepository interventionRepository;
	private PatientRepository patientRepository;

	public InterventionService() {
		this.entityManager = new EntityManagerProvider().getEntityManager();
		this.entityTransaction = entityManager.getTransaction();

		this.interventionRepository = new HibernateInterventionRepository();
		this.patientRepository = new HibernatePatientRepository();
	}

	public InterventionService(InterventionRepository interventionRepository, PatientRepository patientRepository, EntityManager entityManager) {
		this.interventionRepository = interventionRepository;
		this.patientRepository = patientRepository;
		this.entityManager = entityManager;
		this.entityTransaction = entityManager.getTransaction();
	}

	public Integer createIntervention(InterventionCreationDTO interventionCreationDTO, InterventionCreationDTOValidator interventionCreationDTOValidator,
			InterventionAssembler interventionAssembler, InterventionFactory interventionFactory) {
		try {
			interventionCreationDTOValidator.validate(interventionCreationDTO);
			entityTransaction.begin();

			Intervention intervention = doCreateIntervention(interventionCreationDTO, interventionAssembler, interventionFactory);

			entityTransaction.commit();
			return intervention.getId();
		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw e;
		}
	}

	public Intervention doCreateIntervention(InterventionCreationDTO interventionCreationDTO, InterventionAssembler interventionAssembler, InterventionFactory interventionFactory) {
		Intervention intervention = interventionAssembler.assembleFromDTO(interventionCreationDTO, interventionFactory, patientRepository);
		interventionRepository.persist(intervention);
		return intervention;
	}

	public Integer createSurgicalTool(SurgicalToolCreationDTO surgicalToolCreationDTO, SurgicalToolCreationDTOValidator surgicalToolCreationDTOValidator,
			SurgicalToolAssembler surgicalToolAssembler) {
		try {
			surgicalToolCreationDTOValidator.validate(surgicalToolCreationDTO);
			entityTransaction.begin();

			SurgicalTool newSurgicalTool = doCreateSurgicalTool(surgicalToolCreationDTO, surgicalToolAssembler);

			entityTransaction.commit();
			return newSurgicalTool.getId();
		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw e;
		}
	}

	private SurgicalTool doCreateSurgicalTool(SurgicalToolCreationDTO surgicalToolCreationDTO, SurgicalToolAssembler surgicalToolAssembler) {
		SurgicalTool surgicalTool = surgicalToolAssembler.createFromDTO(surgicalToolCreationDTO);
		Intervention intervention = interventionRepository.getById(surgicalToolCreationDTO.interventionNumber);
		intervention.addSurgicalTool(surgicalTool);
		interventionRepository.persist(intervention);
		return surgicalTool;
	}

	public void modifySurgicalTool(SurgicalToolModificationDTO surgicalToolModificationDTO,
			SurgicalToolModificationDTOValidator surgicalToolModificationDTOValidator) {
		try {
			surgicalToolModificationDTOValidator.validate(surgicalToolModificationDTO);
			entityTransaction.begin();

			doModifySurgicalTool(surgicalToolModificationDTO);

			entityTransaction.commit();
		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw e;
		}
	}

	private void doModifySurgicalTool(SurgicalToolModificationDTO surgicalToolModificationDTO) {
		Intervention intervention = interventionRepository.getById(surgicalToolModificationDTO.interventionNumber);
		SurgicalTool surgicalTool = intervention.getSurgicalToolBySerialNumberOrId(surgicalToolModificationDTO.serialNumberOrId);
		surgicalTool.setStatus(SurgicalToolStatus.fromString(surgicalToolModificationDTO.newStatus));
		surgicalTool.changeSerialNumber(surgicalToolModificationDTO.newSerialNumber);
		interventionRepository.persist(intervention);
	}
}
