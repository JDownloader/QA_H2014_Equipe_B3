package ca.ulaval.glo4002.services;

import java.util.Arrays;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.intervention.*;
import ca.ulaval.glo4002.domain.patient.PatientDoesNotExist;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.surgicaltool.*;
import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.persistence.intervention.HibernateInterventionRepository;
import ca.ulaval.glo4002.persistence.patient.HibernatePatientRepository;
import ca.ulaval.glo4002.persistence.surgicaltool.HibernateSurgicalToolRepository;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.*;
import ca.ulaval.glo4002.services.assemblers.InterventionAssembler;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;
import ca.ulaval.glo4002.services.dto.validators.InterventionCreationDTOValidator;
import ca.ulaval.glo4002.services.dto.validators.InvalidArgument;
import ca.ulaval.glo4002.services.intervention.InterventionServiceBuilder;

public class InterventionService {
	private static final String ERROR_SERVICE_REQUEST_EXCEPTION_INT010 = "INT010";
	private static final String ERROR_SERVICE_REQUEST_EXCEPTION_INT011 = "INT011";
	private static final String ERROR_SERVICE_REQUEST_EXCEPTION_INT012 = "INT012";
	
	private String PATIENT_DOES_NOT_EXIST_CODE = "INT002";
	private String PATIENT_DOES_NOT_EXIST_MESSAGE = "Erreur - Patient inexistant";
	private String INVALID_ARGUMENT_CODE = "INT001";
	private String INVALID_ARGUMENT_MESSAGE = "Erreur - informations manquantes ou invalides";

	private static final InterventionType[] forbiddenInterventionTypesForAnonymousSurgicalTools = { InterventionType.EYE, InterventionType.HEART,
			InterventionType.MARROW };
	
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	private InterventionRepository interventionRepository;
	private PatientRepository patientRepository;
	private SurgicalToolRepository surgicalToolRepository;
	private InterventionAssembler interventionAssembler;
	private InterventionCreationDTOValidator interventionCreationValidator;
	
	public InterventionService() {
		this.entityManager = new EntityManagerProvider().getEntityManager();
		this.entityTransaction = entityManager.getTransaction();
		
		this.interventionRepository = new HibernateInterventionRepository();
		this.patientRepository = new HibernatePatientRepository();
		this.surgicalToolRepository = new HibernateSurgicalToolRepository();

		this.interventionAssembler = new InterventionAssembler();
		this.interventionCreationValidator = new InterventionCreationDTOValidator();
	}
	
	//Should only be used for testing
	public InterventionService(InterventionRepository interventionRepository, PatientRepository patientRepository, SurgicalToolRepository surgicalToolRepository, InterventionAssembler interventionAssembler, EntityManager entityManager, InterventionCreationDTOValidator interventionCreationValidator) {
		this.entityManager = entityManager;
		this.entityTransaction = entityManager.getTransaction();
		
		this.interventionRepository = interventionRepository;
		this.patientRepository = patientRepository;
		this.surgicalToolRepository = surgicalToolRepository;

		this.interventionAssembler = interventionAssembler;
		this.interventionCreationValidator = interventionCreationValidator;
	}
	
	public InterventionService(InterventionServiceBuilder builder) {
		this.entityTransaction = builder.entityTransaction;
		this.interventionRepository = builder.interventionRepository;
		this.surgicalToolRepository = builder.surgicalToolRepository;
		this.patientRepository = builder.patientRepository;
	}

	//TODO : class has too many roles. It validates the DTO it receives, applies the business logic and handles exceptions.
	//This could be divided in 3 classes.
	public int createIntervention(InterventionCreationDTO dto) {
		validateInterventionCreationDTO(dto);
		return addIntervention(dto).getId();
	}
	
	private void validateInterventionCreationDTO(InterventionCreationDTO dto) {
		try {
			interventionCreationValidator.validate(dto);
		} catch (InvalidArgument e) {
			throw new ServiceRequestException(INVALID_ARGUMENT_CODE, INVALID_ARGUMENT_MESSAGE);
		}
	}
	
	private Intervention addIntervention(InterventionCreationDTO dto) {
		try {
			entityTransaction.begin();
			Intervention intervention = interventionAssembler.assembleInterventionFromDTO(dto, patientRepository);
			interventionRepository.create(intervention);
			entityTransaction.commit();
			return intervention;
		} catch (PatientDoesNotExist e) {
			throw new ServiceRequestException(PATIENT_DOES_NOT_EXIST_CODE, PATIENT_DOES_NOT_EXIST_MESSAGE);
		} finally {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
		}
	}

	public int createSurgicalTool(CreateSurgicalToolRequestParser requestParser) throws ServiceRequestException, Exception {
		try {
			entityTransaction.begin();
			SurgicalTool newSurgicalTool = doCreateSurgicalTool(requestParser);
			entityTransaction.commit();
			return newSurgicalTool.getId();
		} catch (ServiceRequestException exception) {
			throw exception;
		} finally {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
		}
	}

	private SurgicalTool doCreateSurgicalTool(CreateSurgicalToolRequestParser requestParser) throws ServiceRequestException {
		SurgicalTool surgicalTool = createAndPersistSurgicalTool(requestParser);
		updateIntervention(requestParser, surgicalTool);
		return surgicalTool;
	}

	private SurgicalTool createAndPersistSurgicalTool(CreateSurgicalToolRequestParser requestParser) throws ServiceRequestException {
		SurgicalTool surgicalTool = buildSurgicalTool(requestParser);
		checkSurgicalToolAlreadyExists(requestParser);
		surgicalToolRepository.create(surgicalTool);
		return surgicalTool;
	}

	private void checkSurgicalToolAlreadyExists(CreateSurgicalToolRequestParser requestParser) throws ServiceRequestException {
		try {
			surgicalToolRepository.getBySerialNumber(requestParser.getSerialNumber());
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_INT011, String.format("A surgical tool with serial number '%s' already exists.",
					requestParser.getSerialNumber()));
		} catch (EntityNotFoundException e) {
			return;
		}
	}

	private SurgicalTool buildSurgicalTool(CreateSurgicalToolRequestParser requestParser) throws ServiceRequestException {
		SurgicalToolBuilder surgicalToolBuilder = new SurgicalToolBuilder()
			.serialNumber(requestParser.getSerialNumber())
			.status(requestParser.getStatus())
			.typeCode(requestParser.getTypeCode());

		return surgicalToolBuilder.build();
	}

	private void updateIntervention(CreateSurgicalToolRequestParser requestParser, SurgicalTool surgicalTool) throws ServiceRequestException {
		Intervention intervention = getIntervention(requestParser);
		validateSerialNumberRequirement(requestParser, intervention);
		intervention.addSurgicalTool(surgicalTool);
		interventionRepository.update(intervention);
	}

	private Intervention getIntervention(SurgicalToolRequestParser requestParser) throws ServiceRequestException {
		try {
			return interventionRepository.getById(requestParser.getInterventionNumber());
		} catch (EntityNotFoundException e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_INT010, e.getMessage());
		}
	}

	public void modifySurgicalTool(ModifySurgicalToolRequestParser requestParser) throws ServiceRequestException, Exception {
		try {
			entityTransaction.begin();
			doModifySurgicalTool(requestParser);
			entityTransaction.commit();
		} catch (ServiceRequestException exception) {
			throw exception;
		} finally {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
		}
	}

	private void doModifySurgicalTool(ModifySurgicalToolRequestParser requestParser) throws ServiceRequestException {
		updateSurgicalTool(requestParser);
		Intervention intervention = getIntervention(requestParser);
		validateSerialNumberRequirement(requestParser, intervention);
	}

	private void updateSurgicalTool(ModifySurgicalToolRequestParser requestParser) throws ServiceRequestException {
		SurgicalTool surgicalTool = getUpdatedSurgicalTool(requestParser);
		verifyTypeCodeMatch(requestParser, surgicalTool);
		try {
			surgicalToolRepository.update(surgicalTool);
		} catch (EntityExistsException e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_INT011, String.format("A surgical tool with serial number '%s' already exists.",
					requestParser.getSerialNumber()));
		}
	}

	private SurgicalTool getUpdatedSurgicalTool(ModifySurgicalToolRequestParser requestParser) throws ServiceRequestException {
		SurgicalTool surgicalTool = getSurgicalTool(requestParser);
		surgicalTool.setSerialNumber(requestParser.getNewSerialNumber());
		surgicalTool.setStatus(requestParser.getStatus());
		return surgicalTool;
	}

	private void verifyTypeCodeMatch(ModifySurgicalToolRequestParser requestParser, SurgicalTool surgicalTool) throws ServiceRequestException {
		if (surgicalTool.getTypeCode().compareToIgnoreCase(requestParser.getTypeCode()) != 0) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_INT011, String.format(
					"Type code of specified surgical tool ('%s') does not match the specified type code parameter ('%s').", surgicalTool.getTypeCode(),
					requestParser.getTypeCode()));
		}
	}

	private SurgicalTool getSurgicalTool(SurgicalToolRequestParser requestParser) throws ServiceRequestException {
		try {
			return surgicalToolRepository.getBySerialNumber(requestParser.getSerialNumber());
		} catch (EntityNotFoundException e) {
			return getSurgicalToolById(requestParser);
		}
	}

	private SurgicalTool getSurgicalToolById(SurgicalToolRequestParser requestParser) throws ServiceRequestException {
		try {
			int surgicalToolId = Integer.parseInt(requestParser.getSerialNumber());
			return surgicalToolRepository.getById(surgicalToolId);
		} catch (EntityNotFoundException | NumberFormatException e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_INT010, String.format("Cannot find Surgical Tool with serial or id '%s'",
					requestParser.getSerialNumber()));
		}
	}

	private void validateSerialNumberRequirement(SurgicalToolRequestParser requestParser, Intervention intervention) throws ServiceRequestException {
		if (!requestParser.hasSerialNumber() && Arrays.asList(forbiddenInterventionTypesForAnonymousSurgicalTools).contains(intervention.getType())) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_INT012,
					"An anonymous surgical tool cannot be used with this type of intervention.");
		}
	}
}
