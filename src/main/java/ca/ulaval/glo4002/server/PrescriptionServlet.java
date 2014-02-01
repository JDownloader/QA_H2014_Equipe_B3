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
import ca.ulaval.glo4002.drug.DrugNotFoundException;
import ca.ulaval.glo4002.exceptions.InvalidDateFormatException;
import ca.ulaval.glo4002.patient.Patient;
import ca.ulaval.glo4002.prescription.Prescription;
import ca.ulaval.glo4002.staff.StaffMember;

/* CODE REVIEW 25/01/2014
 * - Je ne sais pas si c'est voulu, mais il n'y présentement pas d'appel à ArchivePrescription et le médicament n'est jamais créé.
 * - La fonction doPost dépasse la maximum de 10 lignes, il faudrait refactorer en sous-fonctions (parseJsonObject(), returnErrormessage(code, message)...)
 * 
 * - Olivier R
 */

public class PrescriptionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private boolean badRequest = false;
	private int currentPatientId = -1;

	private static final String CONTENT_TYPE = "application/json; charset=UTF-8";
	private static final String ENCODING = "UTF-8";
	private static final String DIN_PARAMETER = "din";
	private static final Integer NULL_DIN = 0;
	private static final String STAFF_MEMBER_PARAMETER = "intervenant";
	private static final String NAME_PARAMETER = "nom";
	private static final String NULL_NAME = "";
	private static final String DATE_PARAMETER = "date";
	private static final String RENEWAL_PARAMETER = "renouvellements";

	private static final int WRONG_PATIENT_ID = -1;

	private static final String CODE_PARAMETER = "code";
	private static final String INVALID_PRESCRIPTION_CODE = "PRES001";
	private static final String MESSAGE_PARAMETER = "message";
	private static final String MESSAGE = "La prescription est invalide";

	private static final int INVALID_PRESCRIPTION_ID = -1;

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
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Wrong URL");
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
		JSONObject errorMessage = new JSONObject();
		errorMessage.put(CODE_PARAMETER, INVALID_PRESCRIPTION_CODE);
		errorMessage.put(MESSAGE_PARAMETER, MESSAGE);
		requestResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
				errorMessage.toString());
	}

	public void sendCreatedMessage(HttpServletResponse requestResponse) {
		requestResponse.setStatus(HttpServletResponse.SC_CREATED);
	}

	// Function should only have one purpose. Why does it return an id? Should
	// return the Prescription object instead.
	public int createPrescriptionWithDin(PrescriptionRequest myRequest) {
		int prescriptionId = INVALID_PRESCRIPTION_ID;
		try {
			StaffMember requestedStaffMember = new StaffMember(
					myRequest.getStaffMember());
			/*------ Should be done in a single line ------*/
			Drug requestedDrug = HospitalServer.archiveDrug.getDrug(myRequest
					.getDin());
			Prescription requestedPrescription = new Prescription(
					requestedDrug, requestedStaffMember);
			requestedPrescription.setDate(myRequest.getDate());
			requestedPrescription.setRenewal(myRequest.getRenewals());
			prescriptionId = requestedPrescription.getId();
			/*---------------------------------------------*/
		} catch (DrugNotFoundException | InvalidDateFormatException
				| ParseException e) {
			badRequest = true;
		}
		return prescriptionId;
	}

	public int createPrescriptionWithName(PrescriptionRequest myRequest) {

		StaffMember requestedStaffMember = new StaffMember(
				myRequest.getStaffMember());
		Drug requestedDrug = new Drug(myRequest.getName());
		Prescription requestedPrescription = new Prescription(requestedDrug,
				requestedStaffMember);
		try {
			// Why need setters after using the constructor?
			requestedPrescription.setDate(myRequest.getDate());
			requestedPrescription.setRenewal(myRequest.getRenewals());
		} catch (InvalidDateFormatException | ParseException e) {
			badRequest = true;
		}

		return requestedPrescription.getId();
	}

	public void fetchUrlPatientNumber(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String pathInfo = request.getPathInfo();
		int afterSlash = pathInfo.indexOf("p");
		String patientNumber = pathInfo.substring(1, afterSlash - 1);
		int patientId = Integer.parseInt(patientNumber);
		if (HospitalServer.idsList.contains(patientId)) {
			currentPatientId = patientId;
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					"Patient does not exist");
		}
	}

	public void addPrescriptionToPatient(int idPrescription, int idPatient) {
		int patientIndex = HospitalServer.idsList.indexOf(idPatient);
		Patient currentPatient = HospitalServer.patientsList.get(patientIndex);
		// TODO call good method to add prescription
		// currentPatient.addPrescription(idPrescription);
	}

	public boolean checkUrl(HttpServletRequest request) {
		String pathInfo = request.getPathInfo();
		int afterSlash = pathInfo.indexOf("p");
		String prescriptionsString = pathInfo.substring(afterSlash);
		if (prescriptionsString.equals("prescriptions")) {
			return true;
		} else
			return false;
	}
}
