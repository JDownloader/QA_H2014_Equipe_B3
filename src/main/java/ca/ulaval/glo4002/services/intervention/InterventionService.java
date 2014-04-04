package ca.ulaval.glo4002.services.intervention;

import java.util.Arrays;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.intervention.*;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.domain.surgicaltool.*;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.*;

public class InterventionService {
	private static final String ERROR_SERVICE_REQUEST_EXCEPTION_INT002 = "INT002";
	private static final String ERROR_SERVICE_REQUEST_EXCEPTION_INT010 = "INT010";
	private static final String ERROR_SERVICE_REQUEST_EXCEPTION_INT011 = "INT011";
	private static final String ERROR_SERVICE_REQUEST_EXCEPTION_INT012 = "INT012";

	private static final InterventionType[] forbiddenInterventionTypesForAnonymousSurgicalTools = { InterventionType.EYE, InterventionType.HEART,
			InterventionType.MARROW };

	private InterventionRepository interventionRepository;
	private PatientRepository patientRepository;
	private SurgicalToolRepository surgicalToolRepository;
	private EntityTransaction entityTransaction;

	public InterventionService(InterventionServiceBuilder builder) {
		this.entityTransaction = builder.entityTransaction;
		this.interventionRepository = builder.interventionRepository;
		this.surgicalToolRepository = builder.surgicalToolRepository;
		this.patientRepository = builder.patientRepository;
	}

	public int createIntervention(CreateInterventionRequestParser requestParser) throws ServiceRequestException, Exception {
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

	protected Intervention doCreateIntervention(CreateInterventionRequestParser requestParser) throws ServiceRequestException {
		Intervention intervention = buildIntervention(requestParser);
		interventionRepository.create(intervention);
		return intervention;
	}

	private Intervention buildIntervention(CreateInterventionRequestParser requestParser) throws ServiceRequestException {
		InterventionBuilder interventionBuilder = new InterventionBuilder()
				.date(requestParser.getDate())
				.description(requestParser.getDescription())
				.room(requestParser.getRoom())
				.surgeon(new Surgeon(requestParser.getSurgeon()))
				.type(requestParser.getType())
				.status(requestParser.getStatus())
				.patient(getPatient(requestParser));
		return interventionBuilder.build();
	}

	private Patient getPatient(CreateInterventionRequestParser requestParser) throws ServiceRequestException {
		try {
			return patientRepository.getById(requestParser.getPatient());
		} catch (EntityNotFoundException e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_INT002, e.getMessage());
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
