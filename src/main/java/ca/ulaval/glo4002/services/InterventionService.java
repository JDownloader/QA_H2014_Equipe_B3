package ca.ulaval.glo4002.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.intervention.*;
import ca.ulaval.glo4002.domain.patient.PatientNotFoundException;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.surgicaltool.*;
import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;
import ca.ulaval.glo4002.persistence.*;
import ca.ulaval.glo4002.services.assemblers.InterventionAssembler;
import ca.ulaval.glo4002.services.assemblers.SurgicalToolAssembler;
import ca.ulaval.glo4002.services.dto.*;
import ca.ulaval.glo4002.services.dto.validators.*;

public class InterventionService {
	public static final String ERROR_INT001 = "INT001";
	public static final String ERROR_INT002 = "INT002";
	public static final String ERROR_INT010 = "INT010";
	public static final String ERROR_INT011 = "INT011";
	public static final String ERROR_INT012 = "INT012";

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
			InterventionAssembler interventionAssembler) {
		try {
			interventionCreationDTOValidator.validate(interventionCreationDTO);
			entityTransaction.begin();

			Intervention intervention = doCreateIntervention(interventionCreationDTO, interventionAssembler);

			entityTransaction.commit();
			return intervention.getId();
		} catch (DTOValidationException e) {
			throw new ServiceException(ERROR_INT001, e.getMessage());
		} catch (PatientNotFoundException e) {
			throw new ServiceException(ERROR_INT002, e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new ServiceRequestException(ERROR_INT001, e.getMessage());
		} finally {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
		}
	}

	public Intervention doCreateIntervention(InterventionCreationDTO interventionCreationDTO, InterventionAssembler interventionAssembler) {
		Intervention intervention = interventionAssembler.assembleFromDTO(interventionCreationDTO, patientRepository);
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
		} catch (DTOValidationException | InterventionNotFoundException | InvalidSurgicalToolStatusException e) {
			throw new ServiceException(ERROR_INT010, e.getMessage());
		} catch (SurgicalToolExistsException e) {
			throw new ServiceException(ERROR_INT011, e.getMessage());
		} catch (SurgicalToolRequiresSerialNumberException e) {
			throw new ServiceException(ERROR_INT012, e.getMessage());
		} finally {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
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
		} catch (DTOValidationException | InterventionNotFoundException | SurgicalToolNotFoundException | InvalidSurgicalToolStatusException e) {
			throw new ServiceException(ERROR_INT010, e.getMessage());
		} catch (SurgicalToolExistsException e) {
			throw new ServiceException(ERROR_INT011, e.getMessage());
		} catch (SurgicalToolRequiresSerialNumberException e) {
			throw new ServiceException(ERROR_INT012, e.getMessage());
		} finally {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
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
