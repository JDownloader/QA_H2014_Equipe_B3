package ca.ulaval.glo4002.services.intervention;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.intervention.*;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.domain.surgicaltool.*;
import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.persistence.HibernateSurgicalToolRepository;
import ca.ulaval.glo4002.persistence.intervention.HibernateInterventionRepository;
import ca.ulaval.glo4002.persistence.patient.HibernatePatientRepository;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParser;
import ca.ulaval.glo4002.services.assemblers.SurgicalToolAssembler;
import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;
import ca.ulaval.glo4002.services.dto.SurgicalToolModificationDTO;
import ca.ulaval.glo4002.services.dto.validators.DTOValidationException;
import ca.ulaval.glo4002.services.dto.validators.SurgicalToolCreationDTOValidator;
import ca.ulaval.glo4002.services.dto.validators.SurgicalToolModificationDTOValidator;

public class InterventionService {
	private static final String ERROR_SERVICE_REQUEST_EXCEPTION_INT002 = "INT002";
	private static final String ERROR_SERVICE_REQUEST_EXCEPTION_INT010 = "INT010";
	private static final String ERROR_SERVICE_REQUEST_EXCEPTION_INT011 = "INT011";
	private static final String ERROR_SERVICE_REQUEST_EXCEPTION_INT012 = "INT012";

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

	//TODO: Refactor NewMarie BEGIN
	public int createIntervention(CreateInterventionRequestParser requestParser) throws ServiceRequestException,
			Exception {
		try {
			entityTransaction.begin();
			Intervention newIntervention = doCreateIntervention(requestParser);
			entityTransaction.commit();
			return newIntervention.getId();
		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw e;
		}
	}

	protected Intervention doCreateIntervention(CreateInterventionRequestParser requestParser)
			throws ServiceRequestException {
		Intervention intervention = buildIntervention(requestParser);
		interventionRepository.create(intervention);
		return intervention;
	}

	private Intervention buildIntervention(CreateInterventionRequestParser requestParser)
			throws ServiceRequestException {
		InterventionBuilder interventionBuilder = new InterventionBuilder().date(requestParser.getDate())
				.description(requestParser.getDescription()).room(requestParser.getRoom())
				.surgeon(new Surgeon(requestParser.getSurgeon())).type(requestParser.getType())
				.status(requestParser.getStatus()).patient(getPatient(requestParser));
		return interventionBuilder.build();
	}

	private Patient getPatient(CreateInterventionRequestParser requestParser) throws ServiceRequestException {
		try {
			return patientRepository.getById(requestParser.getPatient());
		} catch (EntityNotFoundException e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_INT002, e.getMessage());
		}
	}
	//TODO: Refactor NewMarie END

	public int createSurgicalTool(SurgicalToolCreationDTO surgicalToolCreationDTO, SurgicalToolCreationDTOValidator surgicalToolCreationDTOValidator, SurgicalToolAssembler surgicalToolAssembler) throws ServiceRequestException {
		try {
			surgicalToolCreationDTOValidator.validate(surgicalToolCreationDTO);
			entityTransaction.begin();
			
			SurgicalTool newSurgicalTool = doCreateSurgicalTool(surgicalToolCreationDTO, surgicalToolAssembler);	
			
			entityTransaction.commit();
			return newSurgicalTool.getId();
		} catch (DTOValidationException | InterventionNotFoundException e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_INT010, e.getMessage());
		} catch (SurgicalToolExistsException e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_INT011, e.getMessage());
		} catch (SurgicalToolRequiresSerialNumberException e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_INT012, e.getMessage());
		} finally {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
		}
	}

	private SurgicalTool doCreateSurgicalTool(SurgicalToolCreationDTO surgicalToolCreationDTO, SurgicalToolAssembler surgicalToolAssembler) throws ServiceRequestException {
		SurgicalTool surgicalTool = surgicalToolAssembler.assembleFromDTO(surgicalToolCreationDTO);
		surgicalToolRepository.persist(surgicalTool);
		Intervention intervention = interventionRepository.getById(surgicalToolCreationDTO.interventionNumber);
		intervention.addSurgicalTool(surgicalTool);
		interventionRepository.update(intervention);
		return surgicalTool;
	}

	public void modifySurgicalTool(SurgicalToolModificationDTO surgicalToolModificationDTO, SurgicalToolModificationDTOValidator surgicalToolModificationDTOValidator) throws ServiceRequestException {
		try {
			surgicalToolModificationDTOValidator.validate(surgicalToolModificationDTO);
			entityTransaction.begin();

			doModifySurgicalTool(surgicalToolModificationDTO);
			
			entityTransaction.commit();
		} catch (DTOValidationException | SurgicalToolNotFoundException e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_INT010, e.getMessage());
		} catch (SurgicalToolExistsException e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_INT011, e.getMessage());
		} catch (SurgicalToolRequiresSerialNumberException e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_INT012, e.getMessage());
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
