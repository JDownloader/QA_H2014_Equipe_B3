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

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");
		InputStream requestBody = request.getInputStream();
		StringWriter writer = new StringWriter();
		IOUtils.copy(requestBody, writer, "UTF-8");
		String requestString = writer.toString();
			parseJsonObject(requestString);
			if (badRequest == true) {
				sendBadRequestMessage(response);
			} 
			else
				sendCreatedMessage(response);

	}

	public void parseJsonObject(String jsonRequest) {
		String name = null;
		Integer din = null;
		JSONObject objectBody = new JSONObject(jsonRequest);
		Integer staffMember = objectBody.getInt("intervenant");
		if (objectBody.has("din")) {
			din = objectBody.getInt("din");
		}
		if (objectBody.has("nom")) {
			name = objectBody.getString("nom");
		}
		String date = objectBody.getString("date");
		int renewals = objectBody.getInt("renouvellements");
		if (din != null && name != null) {
			badRequest = true;
		} else {
			dinNameValidation(din, name, staffMember, renewals, date);
			badRequest = false;
		}
	}

	public void sendBadRequestMessage(HttpServletResponse requestResponse)
			throws IOException {
		JSONObject errorMessage = new JSONObject();
		errorMessage.put("code", "PRES001");
		errorMessage.put("message", "La prescription est invalide");
		requestResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
				errorMessage.toString());
	}

	public void sendCreatedMessage(HttpServletResponse requestResponse) {
		requestResponse.setStatus(HttpServletResponse.SC_CREATED);
	}
	
	public void dinNameValidation(Integer din, String name,
			Integer staffMember, int renewals,
			String date) {

		StaffMember requestedStaffMember = new StaffMember(staffMember);

		if (din != null && name == null) {
			Drug requestedDrug;
			try {
				requestedDrug = HospitalServer.archiveDrug.getDrug(din);
				Prescription requestedPrescription = new Prescription(requestedDrug,requestedStaffMember);
				requestedPrescription.setDate(date);
				requestedPrescription.setRenewal(renewals);
				HospitalServer.archivePrescription.addPrescription(requestedPrescription);
			} catch (DrugNotFoundException | InvalidDateFormatException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (din == null && name != null) {
			Drug requestedDrug = new Drug(name);
			Prescription requestedPrescription = new Prescription(requestedDrug,
					requestedStaffMember);
			try {
				requestedPrescription.setDate(date);
			} catch (InvalidDateFormatException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			requestedPrescription.setRenewal(renewals);
			HospitalServer.archivePrescription.addPrescription(requestedPrescription);
		}
	}
}
