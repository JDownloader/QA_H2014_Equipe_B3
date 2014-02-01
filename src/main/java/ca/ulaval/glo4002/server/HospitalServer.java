package ca.ulaval.glo4002.server;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import ca.ulaval.glo4002.drug.DrugArchive;
import ca.ulaval.glo4002.patient.Patient;
import ca.ulaval.glo4002.persistence.EM;
import ca.ulaval.glo4002.prescription.PrescriptionArchive;

public class HospitalServer extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static int httpPort = 8080;
	private static Server server = new Server(httpPort);
	static ServletContextHandler servletContextHandler = new ServletContextHandler(
			server, "/");
	static PrescriptionArchive archivePrescription = new PrescriptionArchive();
	static DrugArchive archiveDrug;

	static List<Patient> patientsList = new ArrayList<Patient>();
	static List<Integer> idsList = new ArrayList<Integer>();

	public static void main(String[] args) {
		try {
			EM.setEntityManager();
			String myFile = "data/drug.txt";
			archiveDrug = new DrugArchive(new FileReader(myFile));
			createDefaultPatients();
			ServletHolder prescriptionHolder = new ServletHolder(
					PrescriptionServlet.class);
			servletContextHandler.addServlet(prescriptionHolder, "/patient/*");
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createDefaultPatients() {
		Patient patientUn = new Patient();
		Patient patientDeux = new Patient();
		Patient patientTrois = new Patient();
		EM.getUserTransaction().begin();
		EM.getEntityManager().persist(patientUn);
		EM.getEntityManager().persist(patientDeux);
		EM.getEntityManager().persist(patientTrois);
		EM.getUserTransaction().commit();
	}
}