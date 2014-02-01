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
import ca.ulaval.glo4002.persistence.EM;
import ca.ulaval.glo4002.prescription.Prescription;
import ca.ulaval.glo4002.staff.StaffMember;

/* CODE REVIEW 25/01/2014
 * - Je ne sais pas si c'est voulu, mais il n'y présentement pas d'appel à ArchivePrescription et le médicament n'est jamais créé.
 * - La fonction doPost dépasse la maximum de 10 lignes, il faudrait refactorer en sous-fonctions (parseJsonObject(), returnErrormessage(code, message)...)
 * 
 * - Olivier R
 */

/*
 * Check that you need to put staffMember as an Integer.
 * Also, think about creating a "request" object.
 * 
 * @author Marie-Hélène
 *
 */

public class PrescriptionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private boolean badRequest = false;
	private Integer currentPatientId = -1;

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
		JSONObject parsedJson = fetchJsonObject(jsonRequest);
		if (!validateJsonObject(parsedJson)) {
			badRequest = true;
		} else {
			badRequest = false;
			Integer din = fetchDinInJson(parsedJson);
			String name = fetchNameInJson(parsedJson);
			Integer staffMember = fetchStaffMemberInJson(parsedJson);
			int renewals = fetchRenewalsInJson(parsedJson);
			String date = fetchDateInJson(parsedJson);
			verifyWhichPrescriptionToCreate(din, name, staffMember, renewals,
					date);
		}
	}

	public void verifyWhichPrescriptionToCreate(Integer din, String name,
			Integer staffMember, Integer renewals, String date) {
		if (din != 0 && name == "") {
			Integer prescriptionId = createPrescriptionWithDin(din, name,
					staffMember, renewals, date);
			addPrescriptionToPatient(prescriptionId, currentPatientId);

		} else if (din == 0 && name != "") {
			Integer prescriptionId = createPrescriptionWithName(din, name,
					staffMember, renewals, date);
			addPrescriptionToPatient(prescriptionId, currentPatientId);
		}
	}

	public JSONObject fetchJsonObject(String jsonRequest) {
		JSONObject objectBody = new JSONObject(jsonRequest);
		return objectBody;
	}

	public boolean validateJsonObject(JSONObject jsonRequest) {
		if (validateDinAndName(jsonRequest)
				&& jsonRequest.has(STAFF_MEMBER_PARAMETER)
				&& jsonRequest.has(DATE_PARAMETER)
				&& jsonRequest.has(RENEWAL_PARAMETER)) {
			return true;
		}
		return false;
	}

	public boolean validateDinAndName(JSONObject jsonRequest) {
		boolean validJson = true;
		if ((jsonRequest.has(DIN_PARAMETER) && jsonRequest.has(NAME_PARAMETER))
				|| (!jsonRequest.has(DIN_PARAMETER) && !jsonRequest
						.has(NAME_PARAMETER))) {
			validJson = false;
		}
		return validJson;
	}

	public Integer fetchDinInJson(JSONObject jsonRequest) {
		if (jsonRequest.has(DIN_PARAMETER)) {
			return jsonRequest.getInt(DIN_PARAMETER);
		}
		return NULL_DIN;
	}

	public String fetchNameInJson(JSONObject jsonRequest) {
		if (jsonRequest.has(NAME_PARAMETER)) {
			return jsonRequest.getString(NAME_PARAMETER);
		}
		return NULL_NAME;
	}

	public Integer fetchStaffMemberInJson(JSONObject jsonRequest) {
		return jsonRequest.getInt(STAFF_MEMBER_PARAMETER);
	}

	public String fetchDateInJson(JSONObject jsonRequest) {
		return jsonRequest.getString(DATE_PARAMETER);
	}

	public Integer fetchRenewalsInJson(JSONObject jsonRequest) {
		return jsonRequest.getInt(RENEWAL_PARAMETER);
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

	public Integer createPrescriptionWithDin(Integer din, String name,
			Integer staffMember, Integer renewals, String date) {
		StaffMember requestedStaffMember = new StaffMember(staffMember);
		Integer prescriptionId = 1;
		try {
			Drug requestedDrug = HospitalServer.archiveDrug.getDrug(din);
			Prescription requestedPrescription = new Prescription(
					requestedDrug, requestedStaffMember);
			requestedPrescription.setDate(date);
			requestedPrescription.setRenewal(renewals);
			prescriptionId = requestedPrescription.getId();
			EM.persist(requestedPrescription);
		} catch (DrugNotFoundException | InvalidDateFormatException
				| ParseException e) {
			badRequest = true;
			e.printStackTrace();
		}
		return prescriptionId;

	}

	public Integer createPrescriptionWithName(Integer din, String name,
			Integer staffMember, Integer renewals, String date) {
		StaffMember requestedStaffMember = new StaffMember(staffMember);
		Drug requestedDrug = new Drug(name);
		Prescription requestedPrescription = new Prescription(requestedDrug,
				requestedStaffMember);
		try {
			requestedPrescription.setDate(date);
			requestedPrescription.setRenewal(renewals);
		} catch (InvalidDateFormatException | ParseException e) {
			badRequest = true;
			e.printStackTrace();
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
			// if (HospitalServer.idsList.contains(patientId)) {
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
		// int patientIndex = HospitalServer.idsList.indexOf(idPatient);
		// Patient currentPatient =
		// HospitalServer.patientsList.get(patientIndex);
		// TODO call good method to add prescription

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
