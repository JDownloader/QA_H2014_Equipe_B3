package ca.ulaval.glo4002.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import ca.ulaval.glo4002.drug.Drug;
import ca.ulaval.glo4002.exceptions.DrugNotFoundException;
import ca.ulaval.glo4002.exceptions.InvalidDateFormatException;
import ca.ulaval.glo4002.patient.Patient;
import ca.ulaval.glo4002.persistence.EM;
import ca.ulaval.glo4002.prescription.Prescription;
import ca.ulaval.glo4002.requests.PrescriptionRequest;
import ca.ulaval.glo4002.staff.StaffMember;

public class PrescriptionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private boolean badRequest = false;
	private Integer currentPatientId = -1;

	private static final String CONTENT_TYPE = "application/json; charset=UTF-8";
	private static final String ENCODING = "UTF-8";
	
	private static final String CODE_PARAMETER = "code";
	private static final String INVALID_PRESCRIPTION_CODE = "PRES001";
	private static final String MESSAGE_PARAMETER = "message";
	private static final String MESSAGE = "La prescription est invalide";
	private static final String WRONG_URL_MESSAGE = "Wrong URL";

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		InputStream requestBody = request.getInputStream();

		StringWriter writer = new StringWriter();
		IOUtils.copy(requestBody, writer, ENCODING);
		String requestString = writer.toString();
		fetchUrlPatientNumber(request, response);
		if (checkUrl(request) == true) {
			parseJsonObject(requestString);
			if (badRequest == true)
				sendBadRequestMessage(response);
			else
				sendCreatedMessage(response);
		} else
			response.sendError(HttpServletResponse.SC_NOT_FOUND, WRONG_URL_MESSAGE);
	}

	public void parseJsonObject(String jsonRequest) {
		JSONObject parsedJson = this.fetchJsonObject(jsonRequest);
		PrescriptionRequest myRequest = new PrescriptionRequest(parsedJson);
		if (!myRequest.isValid())
			badRequest = true;
		else {
			badRequest = false;
			verifyWhichPrescriptionToCreate(myRequest);
		}
	}


	public void verifyWhichPrescriptionToCreate(PrescriptionRequest myRequest) {
		if (myRequest.getDin() != 0 && myRequest.getName() == "") {
			int prescriptionId = createPrescriptionWithDin(myRequest);
			addPrescriptionToPatient(prescriptionId, currentPatientId);
		} else if (myRequest.getDin() == 0 && myRequest.getName() != "") {
			int prescriptionId = createPrescriptionWithName(myRequest);
			addPrescriptionToPatient(prescriptionId, currentPatientId);
		}
	}

	public JSONObject fetchJsonObject(String jsonRequest) {
		JSONObject objectBody = new JSONObject(jsonRequest);
		return objectBody;
	}

	public void sendBadRequestMessage(HttpServletResponse requestResponse)
			throws IOException {
		// Considérer les errors messages : possibles variables statiques
		JSONObject errorMessage = new JSONObject();
		errorMessage.put(CODE_PARAMETER, INVALID_PRESCRIPTION_CODE);
		errorMessage.put(MESSAGE_PARAMETER, MESSAGE);
		requestResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
				errorMessage.toString());
	}

	public void sendCreatedMessage(HttpServletResponse requestResponse) {
		requestResponse.setStatus(HttpServletResponse.SC_CREATED);
	}

	public Integer createPrescriptionWithDin(PrescriptionRequest myRequest) {
		StaffMember requestedStaffMember = new StaffMember(myRequest.getStaffMember());
		Integer prescriptionId = 1;
		try {
			Drug requestedDrug = HospitalServer.archiveDrug.getDrug(myRequest.getDin());
			Prescription requestedPrescription = new Prescription(
					requestedDrug, requestedStaffMember);
			requestedPrescription.setDate(myRequest.getDate());
			requestedPrescription.setRenewal(myRequest.getRenewals());
			prescriptionId = requestedPrescription.getId();
			if (EM.getEntityManager().find(StaffMember.class,
					requestedStaffMember.getId()) != null) {
				EM.persist(requestedPrescription);
			} else {
				EM.persist(requestedStaffMember);
				EM.persist(requestedPrescription);
			}
		} catch (DrugNotFoundException | InvalidDateFormatException
				| ParseException e) {
			badRequest = true;
		}
		return prescriptionId;
	}

	public Integer createPrescriptionWithName(PrescriptionRequest myRequest) {

		//WAAAAAAY TOO LONG
		StaffMember requestedStaffMember = new StaffMember(myRequest.getStaffMember());
		Drug requestedDrug = new Drug(myRequest.getName());
		Prescription requestedPrescription = new Prescription(requestedDrug,requestedStaffMember);
		
		try {
			// Why need setters after using the constructor?
			requestedPrescription.setDate(myRequest.getDate());
			requestedPrescription.setRenewal(myRequest.getRenewals());
		} catch (InvalidDateFormatException | ParseException e) {
			badRequest = true;
		}
		
		if (EM.getEntityManager().find(StaffMember.class,
				requestedStaffMember.getId()) != null
				|| EM.getEntityManager().find(Drug.class, myRequest.getDin()) != null) {
			EM.persist(requestedPrescription);
		} else {
			EM.persist(requestedStaffMember);
			EM.persist(requestedDrug);
			EM.persist(requestedPrescription);
		}
		return requestedPrescription.getId();
	}

	public void fetchUrlPatientNumber(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String pathInfo = request.getPathInfo();
		Integer afterSlash = pathInfo.indexOf("p");
		String patientNumber = pathInfo.substring(1, afterSlash - 1);
		Integer patientId = Integer.parseInt(patientNumber);
		if (EM.getEntityManager().find(Patient.class, patientId) != null) {
			currentPatientId = patientId;
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					"Patient does not exist");
		}
	}

	public void addPrescriptionToPatient(Integer idPrescription,
			Integer idPatient) {
		EM.getUserTransaction().begin();
		Patient currentPatient = EM.getEntityManager().find(Patient.class,
				idPatient);
		currentPatient.addPrescription(idPrescription);
		EM.getUserTransaction().commit();

	}

	public boolean checkUrl(HttpServletRequest request) {
		String pathInfo = request.getPathInfo();
		Integer afterSlash = pathInfo.indexOf("p");
		String prescriptionsString = pathInfo.substring(afterSlash);
		if (prescriptionsString.equals("prescriptions")) {
			return true;
		} else
			return false;
	}

}
