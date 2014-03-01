package ca.ulaval.glo4002.services.prescription;

import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.domain.drug.*;
import ca.ulaval.glo4002.domain.patient.*;
import ca.ulaval.glo4002.domain.prescription.*;
import ca.ulaval.glo4002.domain.staff.StaffMember;
import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.rest.requests.AddPrescriptionRequest;
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;

public class PrescriptionService {
	private PrescriptionRepository prescriptionRepository;
	private DrugRepository drugRepository;
	private PatientRepository patientRepository;
	private EntityTransaction entityTransaction;
	
	public PrescriptionService(PrescriptionServiceBuilder builder) {
		this.entityTransaction = builder.entityTransaction;
		this.prescriptionRepository = builder.prescriptionRepository;
		this.drugRepository = builder.drugRepository;
		this.patientRepository = builder.patientRepository;
	}
	
	public Response addPrescription(AddPrescriptionRequest prescriptionRequest) {
		Response response = null;

		try {
			entityTransaction.begin();
			doAddPrescription(prescriptionRequest);
			entityTransaction.commit();
			response = Response.status(Status.CREATED).build();
		} catch (BadRequestException e) {
			response = BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		} catch (Exception e) {
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} finally {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
		}

		return response;
	}
	
	protected void doAddPrescription(AddPrescriptionRequest prescriptionRequest) throws BadRequestException {
		Prescription prescription = createPrescription(prescriptionRequest);
		updatePatient(prescriptionRequest, prescription);
	}

	private Prescription createPrescription(AddPrescriptionRequest prescriptionRequest) throws BadRequestException {
		PrescriptionBuilder prescriptionBuilder = new PrescriptionBuilder();
		prescriptionBuilder.date(prescriptionRequest.getDate());
		prescriptionBuilder.allowedRenewalCount(prescriptionRequest.getRenewals());
		prescriptionBuilder.prescriber(new StaffMember(prescriptionRequest.getStaffMember()));
		prescriptionBuilder.drugName(prescriptionRequest.getDrugName());
		if (prescriptionRequest.hasDin()) {
			Drug drug = getDrug(prescriptionRequest);
			prescriptionBuilder.drug(drug);	
		}
		Prescription prescription = prescriptionBuilder.build();
		prescriptionRepository.create(prescription);
		return prescription;
	}
	
	private Drug getDrug(AddPrescriptionRequest prescriptionRequest) throws BadRequestException {
		try {
			return drugRepository.get(new Din(prescriptionRequest.getDin()));
		} catch (EntityNotFoundException e) {
			throw new BadRequestException("PRES001", e.getMessage());
		}
	}

	private void updatePatient(AddPrescriptionRequest prescriptionRequest, Prescription prescription) throws BadRequestException {
		Patient patient = getPatient(prescriptionRequest);
		patient.addPrescription(prescription);
		patientRepository.update(patient);
	}
	
	private Patient getPatient(AddPrescriptionRequest prescriptionRequest) throws BadRequestException {
		try {
			return patientRepository.get(prescriptionRequest.getPatientNumber());
		} catch (EntityNotFoundException e) {
			throw new BadRequestException("PRES001", e.getMessage());
		}
	}
	
}
