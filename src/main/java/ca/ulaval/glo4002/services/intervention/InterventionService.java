package ca.ulaval.glo4002.services.intervention;

import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.intervention.InterventionRepository;
import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.domain.intervention.Intervention;
import ca.ulaval.glo4002.domain.intervention.InterventionBuilder;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParser;

public class InterventionService {
	private InterventionRepository interventionRepository;
	private PatientRepository patientRepository;
	private EntityTransaction entityTransaction;
	
	public InterventionService(InterventionServiceBuilder builder) {
		this.entityTransaction = builder.entityTransaction;
		this.interventionRepository = builder.interventionRepository;
		this.patientRepository = builder.patientRepository;
	}
	
	public void createIntervention(CreateInterventionRequestParser requestParser) throws BadRequestException {
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
	
	protected void doCreateIntervention(CreateInterventionRequestParser requestParser) throws BadRequestException {
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
	
	private Patient getPatient(CreateInterventionRequestParser requestParser) throws BadRequestException {
		try {
			return patientRepository.getById(requestParser.getPatient());
		} catch (EntityNotFoundException e) {
			throw new BadRequestException("INT002", e.getMessage());
		}
	}
	
}
