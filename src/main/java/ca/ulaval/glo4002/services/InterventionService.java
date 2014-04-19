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
	private SurgicalToolRepository surgicalToolRepository;

	public InterventionService() {
		this.entityManager = new EntityManagerProvider().getEntityManager();
		this.entityTransaction = entityManager.getTransaction();

		this.interventionRepository = new HibernateInterventionRepository();
		this.patientRepository = new HibernatePatientRepository();
		this.surgicalToolRepository = new HibernateSurgicalToolRepository();
	}

	public InterventionService(InterventionRepository interventionRepository, PatientRepository patientRepository,
			SurgicalToolRepository surgicalToolRepository, EntityManager entityManager) {
		this.interventionRepository = interventionRepository;
		this.patientRepository = patientRepository;
		this.surgicalToolRepository = surgicalToolRepository;
		this.entityManager = entityManager;
		this.entityTransaction = entityManager.getTransaction();
	}

	public Integer createIntervention(InterventionCreationDTO interventionCreationDTO, InterventionCreationDTOValidator interventionCreationDTOValidator,
			InterventionAssembler interventionAssembler) throws ServiceRequestException {
		try {
			interventionCreationDTOValidator.validate(interventionCreationDTO);
			entityTransaction.begin();

			Intervention intervention = doCreateIntervention(interventionCreationDTO, interventionAssembler);

			entityTransaction.commit();
			return intervention.getId();
		} catch (DTOValidationException e) {
			throw new ServiceRequestException(ERROR_INT001, e.getMessage());
		} catch (PatientNotFoundException e) {
			throw new ServiceRequestException(ERROR_INT002, e.getMessage());
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
			SurgicalToolFactory surgicalToolAssembler) throws ServiceRequestException {
		try {
			surgicalToolCreationDTOValidator.validate(surgicalToolCreationDTO);
			entityTransaction.begin();

			SurgicalTool newSurgicalTool = doCreateSurgicalTool(surgicalToolCreationDTO, surgicalToolAssembler);

			entityTransaction.commit();
			return newSurgicalTool.getId();
		} catch (DTOValidationException | InterventionNotFoundException e) {
			throw new ServiceRequestException(ERROR_INT010, e.getMessage());
		} catch (SurgicalToolExistsException e) {
			throw new ServiceRequestException(ERROR_INT011, e.getMessage());
		} catch (SurgicalToolRequiresSerialNumberException e) {
			throw new ServiceRequestException(ERROR_INT012, e.getMessage());
		} finally {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
		}
	}

	private SurgicalTool doCreateSurgicalTool(SurgicalToolCreationDTO surgicalToolCreationDTO, SurgicalToolFactory surgicalToolAssembler)
			throws ServiceRequestException {
		SurgicalTool surgicalTool = surgicalToolAssembler.createFromDTO(surgicalToolCreationDTO);
		surgicalToolRepository.persist(surgicalTool);
		Intervention intervention = interventionRepository.getById(surgicalToolCreationDTO.interventionNumber);
		intervention.addSurgicalTool(surgicalTool);
		interventionRepository.update(intervention);
		return surgicalTool;
	}

	public void modifySurgicalTool(SurgicalToolModificationDTO surgicalToolModificationDTO,
			SurgicalToolModificationDTOValidator surgicalToolModificationDTOValidator) throws ServiceRequestException {
		try {
			surgicalToolModificationDTOValidator.validate(surgicalToolModificationDTO);
			entityTransaction.begin();

			doModifySurgicalTool(surgicalToolModificationDTO);

			entityTransaction.commit();
		} catch (DTOValidationException | SurgicalToolNotFoundException e) {
			throw new ServiceRequestException(ERROR_INT010, e.getMessage());
		} catch (SurgicalToolExistsException e) {
			throw new ServiceRequestException(ERROR_INT011, e.getMessage());
		} catch (SurgicalToolRequiresSerialNumberException e) {
			throw new ServiceRequestException(ERROR_INT012, e.getMessage());
		} finally {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
		}
	}

	private void doModifySurgicalTool(SurgicalToolModificationDTO surgicalToolModificationDTO) throws ServiceRequestException {
		SurgicalTool surgicalTool = surgicalToolRepository.getBySerialNumberOrId(surgicalToolModificationDTO.serialNumberOrId);
		Intervention intervention = interventionRepository.getById(surgicalToolModificationDTO.interventionNumber);
		surgicalTool.setStatus(surgicalToolModificationDTO.newStatus);
		intervention.changeSurgicalToolSerialNumber(surgicalTool, surgicalToolModificationDTO.newSerialNumber);
		surgicalToolRepository.update(surgicalTool);
	}
}
