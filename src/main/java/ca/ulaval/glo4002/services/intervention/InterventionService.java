package ca.ulaval.glo4002.services.intervention;

import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.intervention.Intervention;
import ca.ulaval.glo4002.domain.intervention.InterventionBuilder;
import ca.ulaval.glo4002.domain.intervention.InterventionRepository;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.rest.requests.CreateInterventionRequest;

public class InterventionService {
	private InterventionRepository interventionRepository;
	private PatientRepository patientRepository;
	private EntityTransaction entityTransaction;
	
	public InterventionService(InterventionServiceBuilder builder) {
		this.entityTransaction = builder.entityTransaction;
		this.interventionRepository = builder.interventionRepository;
		this.patientRepository = builder.patientRepository;
	}
	
	public void createIntervention(CreateInterventionRequest interventionRequest) throws BadRequestException {
		try {
			entityTransaction.begin();
			doCreateIntervention(interventionRequest);
			entityTransaction.commit();
		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw e;
		}
	}
	
	protected void doCreateIntervention(CreateInterventionRequest interventionRequest) throws BadRequestException {
		InterventionBuilder interventionBuilder = new InterventionBuilder();
		interventionBuilder.date(interventionRequest.getDate());
		interventionBuilder.description(interventionRequest.getDescription());
		interventionBuilder.room(interventionRequest.getRoom());
		interventionBuilder.surgeon(new Surgeon(interventionRequest.getSurgeon()));
		interventionBuilder.type(interventionRequest.getType());
		interventionBuilder.status(interventionRequest.getStatus());
		Patient patient = getPatient(interventionRequest);
		interventionBuilder.patient(patient);
		
		Intervention intervention = interventionBuilder.build();
		interventionRepository.create(intervention);
	}
	
	private Patient getPatient(CreateInterventionRequest interventionRequest) throws BadRequestException {
		try {
			return patientRepository.getById(interventionRequest.getPatient());
		} catch (EntityNotFoundException e) {
			throw new BadRequestException("INT002", "Erreur - Patient inexistant");
		}
	}
}
