package ca.ulaval.glo4002.services.intervention;

import java.util.Arrays;

import javax.persistence.*;

import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.domain.intervention.*;
import ca.ulaval.glo4002.domain.patient.*;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.domain.surgicaltool.*;
import ca.ulaval.glo4002.rest.requestparsers.intervention.*;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.*;

public class InterventionService {
	private static final InterventionType forbiddenInterventionTypesForAnonymousSurgicalTools[] = {InterventionType.OEIL, InterventionType.COEUR, InterventionType.MOELLE};
	
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
	
	public void createIntervention(CreateInterventionRequestParser requestParser) throws ServiceRequestException {
		try {
			entityTransaction.begin();
			doCreateIntervention(requestParser);
			entityTransaction.commit();
		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw e;
		}
	}
	
	protected void doCreateIntervention(CreateInterventionRequestParser requestParser) throws ServiceRequestException {
		InterventionBuilder interventionBuilder = new InterventionBuilder();
		interventionBuilder.date(requestParser.getDate());
		interventionBuilder.description(requestParser.getDescription());
		interventionBuilder.room(requestParser.getRoom());
		interventionBuilder.surgeon(new Surgeon(requestParser.getSurgeon()));
		interventionBuilder.type(requestParser.getType());
		interventionBuilder.status(requestParser.getStatus());
		Patient patient = getPatient(requestParser);
		interventionBuilder.patient(patient);
		
		Intervention intervention = interventionBuilder.build();
		interventionRepository.create(intervention);
	}
	
	private Patient getPatient(CreateInterventionRequestParser requestParser) throws ServiceRequestException {
		try {
			return patientRepository.getById(requestParser.getPatient());
		} catch (EntityNotFoundException e) {
			throw new ServiceRequestException("INT002", e.getMessage());
		}
	}
	
	public void createSurgicalTool(CreateSurgicalToolRequestParser requestParser) throws ServiceRequestException {
		try {
			entityTransaction.begin();
			doCreateSurgicalTool(requestParser);
			entityTransaction.commit();
		} catch (ServiceRequestException exception) {
			throw exception;
		} finally {
			if(entityTransaction.isActive())
				entityTransaction.rollback();
		}
	}
	
	private void doCreateSurgicalTool(CreateSurgicalToolRequestParser requestParser) throws ServiceRequestException {
		SurgicalTool surgicalTool = buildSurgicalTool(requestParser);
		try {
			surgicalToolRepository.create(surgicalTool);
		} catch (EntityExistsException e) {
			throw new ServiceRequestException("INT011", String.format("A surgical tool with serial number '%s' already exists.", requestParser.getSerialNumber()));
		}
		updateIntervention(requestParser, surgicalTool);
	}
	
	private SurgicalTool buildSurgicalTool(CreateSurgicalToolRequestParser requestParser) throws ServiceRequestException {
		SurgicalToolBuilder surgicalToolBuilder = new SurgicalToolBuilder();
		surgicalToolBuilder.serialNumber(requestParser.getSerialNumber());
		surgicalToolBuilder.status(requestParser.getStatus());
		surgicalToolBuilder.typeCode(requestParser.getTypeCode());
		
		SurgicalTool surgicalTool = surgicalToolBuilder.build();
		return surgicalTool;
	}
	
	private void updateIntervention(CreateSurgicalToolRequestParser requestParser, SurgicalTool surgicalTool) throws ServiceRequestException {
		Intervention intervention = getIntervention(requestParser);
		validateSerialNumberRequirement(requestParser, intervention);
		intervention.addSurgicalTool(surgicalTool);
		interventionRepository.update(intervention);
	}
	
	private Intervention getIntervention(CreateSurgicalToolRequestParser requestParser) throws ServiceRequestException {
		try {
			return interventionRepository.getById(requestParser.getInterventionId());
		} catch (EntityNotFoundException e) {
			throw new ServiceRequestException("INT010", e.getMessage());
		}
	}
	
	public void modifySurgicalTool(ModifySurgicalToolRequestParser requestParser) throws ServiceRequestException {
		try {
			entityTransaction.begin();
			doModifySurgicalTool(requestParser);
			entityTransaction.commit();
		} catch (ServiceRequestException exception) {
			throw exception;
		} finally {
			if(entityTransaction.isActive())
				entityTransaction.rollback();
		}
	}
	
	private void doModifySurgicalTool(ModifySurgicalToolRequestParser requestParser) throws ServiceRequestException {
		updateSurgicalTool(requestParser);
		Intervention intervention = getIntervention(requestParser);
		validateSerialNumberRequirement(requestParser, intervention);
	}

	private void updateSurgicalTool(ModifySurgicalToolRequestParser requestParser) throws ServiceRequestException {
		SurgicalTool surgicalTool = getSurgicalTool(requestParser);
		surgicalTool.setSerialNumber(requestParser.getSerialNumber());
		surgicalTool.setStatus(requestParser.getStatus());
		surgicalToolRepository.update(surgicalTool);
	}
	
	private SurgicalTool getSurgicalTool(ModifySurgicalToolRequestParser requestParser) throws ServiceRequestException {
		try {
			return surgicalToolRepository.getBySerialNumber(requestParser.getSerialNumber());
		} catch (EntityNotFoundException e) {
			throw new ServiceRequestException("INT010", e.getMessage());
		}
	}


	private void validateSerialNumberRequirement(CreateSurgicalToolRequestParser requestParser, Intervention intervention) throws ServiceRequestException {
		if (requestParser.hasSerialNumber() 
				&& Arrays.asList(forbiddenInterventionTypesForAnonymousSurgicalTools).contains(intervention.getType())) {
							throw new ServiceRequestException("INT012", "An anonymous surgical cannot be used with this type of intervention.");
		}
	}
}
