package ca.ulaval.glo4002.server;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.JSONTokener;

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

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");
		InputStream requestBody = request.getInputStream();
		JSONTokener tokenerBody = new JSONTokener(requestBody);
		JSONObject objectBody = new JSONObject(tokenerBody);
		String staff = objectBody.getString("intervenant");
		String date = objectBody.getString("date");
		int renewal = objectBody.getInt("renouvellements");
		String din = objectBody.getString("din");
		String name = objectBody.getString("nom");
		if (!din.isEmpty() && !name.isEmpty()) {
			JSONObject errorMessage = new JSONObject();
			errorMessage.put("code", "PRES001");
			errorMessage.put("message", "La prescription est invalide");
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMessage.toString());
		} else
			response.setStatus(HttpServletResponse.SC_CREATED);
	}

}
