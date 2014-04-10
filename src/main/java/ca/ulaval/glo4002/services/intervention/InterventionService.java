package ca.ulaval.glo4002.services.intervention;

import java.util.Arrays;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.intervention.*;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.domain.surgicaltool.*;
import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.persistence.intervention.HibernateInterventionRepository;
import ca.ulaval.glo4002.persistence.patient.HibernatePatientRepository;
import ca.ulaval.glo4002.persistence.surgicaltool.HibernateSurgicalToolRepository;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParser;
import ca.ulaval.glo4002.services.assemblers.SurgicalToolAssembler;
import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;
import ca.ulaval.glo4002.services.dto.SurgicalToolModificationDTO;
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

	private InterventionAssembler interventionAssembler;
	private SurgicalToolAssembler surgicalToolAssembler;

	public InterventionService() {
		this.entityManager = new EntityManagerProvider().getEntityManager();
		this.entityTransaction = entityManager.getTransaction();

		this.interventionRepository = new HibernateInterventionRepository();
		this.patientRepository = new HibernatePatientRepository();
		this.surgicalToolRepository = new HibernateSurgicalToolRepository();

		this.interventionAssembler = new InterventionAssembler();
	}

	// Should only be used for testing
	public InterventionService(InterventionRepository interventionRepository, PatientRepository patientRepository,
			SurgicalToolRepository surgicalToolRepository, InterventionAssembler interventionAssembler,
			SurgicalToolAssembler surgicalToolAssembler, EntityManager entityManager) {
		this.entityManager = entityManager;
		this.entityTransaction = entityManager.getTransaction();

		this.interventionRepository = interventionRepository;
		this.patientRepository = patientRepository;
		this.surgicalToolRepository = surgicalToolRepository;

		this.interventionAssembler = interventionAssembler;
		this.surgicalToolAssembler = surgicalToolAssembler;

	}

	public InterventionService(InterventionServiceBuilder builder) {
		this.entityTransaction = builder.entityTransaction;
		this.interventionRepository = builder.interventionRepository;
		this.surgicalToolRepository = builder.surgicalToolRepository;
		this.patientRepository = builder.patientRepository;
	}

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

	public void createSurgicalTool(SurgicalToolCreationDTO surgicalToolCreationDTO,
			SurgicalToolCreationDTOValidator surgicalToolCreationDTOValidator,
			SurgicalToolAssembler surgicalToolAssembler) throws ServiceRequestException {
		try {

			// TODO: set le intervention type avant de valider?
			surgicalToolCreationDTOValidator.validate(surgicalToolCreationDTO);
			entityTransaction.begin();

			doCreateSurgicalTool(surgicalToolCreationDTO, surgicalToolAssembler);

			entityTransaction.commit();

		} catch (Exception e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_INT010, e.getMessage());
		} finally {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
		}
	}

	private void doCreateSurgicalTool(SurgicalToolCreationDTO surgicalToolCreationDTO,
			SurgicalToolAssembler surgicalToolAssembler) {

		SurgicalTool surgicalTool = surgicalToolAssembler.assembleFromDTO(surgicalToolCreationDTO);

		surgicalToolRepository.create(surgicalTool);
		// TODO: create plutot que persist: un peu incohérent?

		Intervention intervention = interventionRepository.getById(surgicalToolCreationDTO.getInterventionNumber());
		intervention.addSurgicalTool(surgicalTool);
		interventionRepository.update(intervention);
	}

	public void modifySurgicalTool(SurgicalToolModificationDTO surgicalToolModificationDTO,
			SurgicalToolModificationDTOValidator surgicalToolModificationDTOValidator) throws ServiceRequestException {

		try {
			surgicalToolModificationDTOValidator.validate(surgicalToolModificationDTO);
			entityTransaction.begin();

			doModifySurgicalTool(surgicalToolModificationDTO);

			entityTransaction.commit();
		} catch (Exception e) {

		} finally {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
		}
	}

	private void doModifySurgicalTool(SurgicalToolModificationDTO surgicalToolModificationDTO)
			throws ServiceRequestException {

		SurgicalTool surgicalTool = getUpdatedSurgicalTool(surgicalToolModificationDTO);
		verifyTypeCodeMatch(surgicalToolModificationDTO, surgicalTool);
		try {
			surgicalToolRepository.update(surgicalTool);
		} catch (EntityExistsException e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_INT011, String.format(
					"A surgical tool with serial number '%s' already exists.",
					surgicalToolModificationDTO.getNewSerialNumber()));
		}

	}

	private SurgicalTool getUpdatedSurgicalTool(SurgicalToolModificationDTO surgicalToolModificationDTO)
			throws ServiceRequestException {
		SurgicalTool surgicalTool = getSurgicalTool(surgicalToolModificationDTO);
		surgicalTool.setSerialNumber(surgicalToolModificationDTO.getNewSerialNumber());
		surgicalTool.setStatus(SurgicalToolStatus.fromString(surgicalToolModificationDTO.getNewStatus()));
		return surgicalTool;
	}

	private SurgicalTool getSurgicalTool(SurgicalToolModificationDTO surgicalToolModificationDTO)
			throws ServiceRequestException {
		try {
			return surgicalToolRepository.getBySerialNumber(surgicalToolModificationDTO.getOriginalSerialNumber());
		} catch (EntityNotFoundException e) {
			return getSurgicalToolById(surgicalToolModificationDTO);
		}
	}

	private SurgicalTool getSurgicalToolById(SurgicalToolModificationDTO surgicalToolModificationDTO)
			throws ServiceRequestException {
		try {
			int surgicalToolId = Integer.parseInt(surgicalToolModificationDTO.getOriginalSerialNumber());
			return surgicalToolRepository.getById(surgicalToolId);
		} catch (EntityNotFoundException | NumberFormatException e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_INT010, String.format(
					"Cannot find Surgical Tool with serial or id '%s'",
					surgicalToolModificationDTO.getOriginalSerialNumber()));
		}
	}

	private void verifyTypeCodeMatch(SurgicalToolModificationDTO surgicalToolModificationDTO, SurgicalTool surgicalTool)
			throws ServiceRequestException {
		if (surgicalTool.getTypeCode().compareToIgnoreCase(surgicalToolModificationDTO.getTypecode()) != 0) {
			throw new ServiceRequestException(
					ERROR_SERVICE_REQUEST_EXCEPTION_INT011,
					String.format(
							"Type code of specified surgical tool ('%s') does not match the specified type code parameter ('%s').",
							surgicalTool.getTypeCode(), surgicalToolModificationDTO.getTypecode()));
		}
	}


	private Intervention getIntervention(SurgicalToolModificationDTO surgicalToolModificationDTO)
			throws ServiceRequestException {
		try {
			return interventionRepository.getById(surgicalToolModificationDTO.getInterventionNumber());
		} catch (EntityNotFoundException e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_INT010, e.getMessage());
		}
	}

}
