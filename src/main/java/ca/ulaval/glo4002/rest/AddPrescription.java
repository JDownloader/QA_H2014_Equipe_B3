package ca.ulaval.glo4002.rest;

import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.dao.DataAccessTransaction;
import ca.ulaval.glo4002.drug.Din;
import ca.ulaval.glo4002.drug.Drug;
import ca.ulaval.glo4002.drug.DrugDAO;
import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.exceptions.ItemNotFoundException;
import ca.ulaval.glo4002.patient.Patient;
import ca.ulaval.glo4002.patient.PatientDAO;
import ca.ulaval.glo4002.prescription.Prescription;
import ca.ulaval.glo4002.prescription.PrescriptionDAO;
import ca.ulaval.glo4002.requests.PrescriptionRequest;
import ca.ulaval.glo4002.staff.StaffMember;

@Path("patient/{patient_number: [0-9]+}/prescriptions/")
public class AddPrescription extends Rest<PrescriptionRequest> {

	@PathParam("patient_number")
	private int patientNumber;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(final String input) {
		PrescriptionRequest prescriptionRequest = null;

		try {
			JSONObject jsonRequest = new JSONObject(input);
			jsonRequest.put("patient", String.valueOf(patientNumber));
			prescriptionRequest = new PrescriptionRequest(jsonRequest);
		} catch (JSONException | ParseException | IllegalArgumentException e) {
			return buildBadRequestResponse("PRES001", e.getMessage());
		}

		return executeTransaction(prescriptionRequest);
	}

	protected void executeDaoTransactions(DataAccessTransaction transaction, PrescriptionRequest prescriptionRequest) throws BadRequestException {
		Prescription prescription = createPrescription(transaction, prescriptionRequest);
		updatePatient(transaction, prescriptionRequest, prescription);
	}

	private Drug updateAndPersistDrug(DataAccessTransaction transaction, PrescriptionRequest prescriptionRequest) throws BadRequestException {
		DrugDAO drugDAO = new DrugDAO(transaction);
		try {
			return drugDAO.get(new Din(prescriptionRequest.getDin()));
		} catch (ItemNotFoundException e) {
			throw new BadRequestException("PRES001", e.getMessage());
		}
	}

	private Prescription createPrescription(DataAccessTransaction transaction, PrescriptionRequest prescriptionRequest) throws BadRequestException {
		PrescriptionDAO prescriptionDAO = new PrescriptionDAO(transaction);
		Prescription.Builder prescriptionBuilder = new Prescription.Builder();
		prescriptionBuilder.date(prescriptionRequest.getDate());
		prescriptionBuilder.renewals(prescriptionRequest.getRenewals());
		prescriptionBuilder.prescriber(new StaffMember(prescriptionRequest.getStaffMember()));
		prescriptionBuilder.drugName(prescriptionRequest.getDrugName());
		if (prescriptionRequest.hasDin()) {
			Drug drug = updateAndPersistDrug(transaction, prescriptionRequest);
			prescriptionBuilder.drug(drug);	
		}
		Prescription prescription = prescriptionBuilder.build();
		prescriptionDAO.create(prescription);
		return prescription;
	}

	private void updatePatient(DataAccessTransaction transaction, PrescriptionRequest prescriptionRequest, Prescription prescription) {
		PatientDAO patientDAO = new PatientDAO(transaction);
		Patient patient;

		try {
			patient = patientDAO.get(prescriptionRequest.getPatientNumber());
		} catch (ItemNotFoundException e) {
			patient = new Patient(prescriptionRequest.getPatientNumber());
		}

		patient.addPrescription(prescription);
		patientDAO.update(patient);
	}
}
