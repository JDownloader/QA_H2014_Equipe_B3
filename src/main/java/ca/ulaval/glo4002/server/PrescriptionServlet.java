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
import ca.ulaval.glo4002.prescription.Prescription;
import ca.ulaval.glo4002.staff.StaffMember;

/*
 * il faudrait automatiser les test
 * 
 * @author Vincent
 * 
 */

/* CODE REVIEW 25/01/2014
 * - Je ne sais pas si c'est voulu, mais il n'y présentement pas d'appel à ArchivePrescription et le médicament n'est jamais créé.
 * - La fonction doPost dépasse la maximum de 10 lignes, il faudrait refactorer en sous-fonctions (parseJsonObject(), returnErrormessage(code, message)...)
 * 
 * - Olivier R
 */

public class PrescriptionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private boolean badRequest = false;

	private static final String CONTENT_TYPE = "application/json; charset=UTF-8";
	private static final String ENCODING = "UTF-8";
	private static final String DIN_PARAMETER = "din";
	private static final String STAFF_MEMBER_PARAMETER = "intervenant";
	private static final String NAME_PARAMETER = "nom";
	private static final String DATE_PARAMETER = "date";
	private static final String RENEWAL_PARAMETER = "renouvellements";

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
		parseJsonObject(requestString);
		if (badRequest == true) {
			sendBadRequestMessage(response);
		} else
			sendCreatedMessage(response);

	}

	public void parseJsonObject(String jsonRequest) {
		String name = null;
		Integer din = null;
		JSONObject objectBody = new JSONObject(jsonRequest);
		Integer staffMember = objectBody.getInt(STAFF_MEMBER_PARAMETER);
		if (objectBody.has(DIN_PARAMETER)) {
			din = objectBody.getInt(DIN_PARAMETER);
		}
		if (objectBody.has(NAME_PARAMETER)) {
			name = objectBody.getString(NAME_PARAMETER);
		}
		String date = objectBody.getString(DATE_PARAMETER);
		int renewals = objectBody.getInt(RENEWAL_PARAMETER);
		if (din != null && name != null) {
			badRequest = true;
		} else {
			dinNameValidation(din, name, staffMember, renewals, date);
			// redondance
			// badRequest = false;
		}
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

	public void dinNameValidation(Integer din, String name,
			Integer staffMember, int renewals, String date) {

		StaffMember requestedStaffMember = new StaffMember(staffMember);

		if (din != null && name == null) {
			Drug requestedDrug;
			try {
				requestedDrug = HospitalServer.archiveDrug.getDrug(din);
				Prescription requestedPrescription = new Prescription(
						requestedDrug, requestedStaffMember);
				requestedPrescription.setDate(date);
				requestedPrescription.setRenewal(renewals);
				HospitalServer.archivePrescription
						.addPrescription(requestedPrescription);
			} catch (DrugNotFoundException | InvalidDateFormatException
					| ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (din == null && name != null) {
			Drug requestedDrug = new Drug(name);
			Prescription requestedPrescription = new Prescription(
					requestedDrug, requestedStaffMember);
			try {
				requestedPrescription.setDate(date);
			} catch (InvalidDateFormatException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			requestedPrescription.setRenewal(renewals);
			HospitalServer.archivePrescription
					.addPrescription(requestedPrescription);
		}
	}
}
