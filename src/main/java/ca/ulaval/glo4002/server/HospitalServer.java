package ca.ulaval.glo4002.server;

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
import ca.ulaval.glo4002.staff.StaffMember;

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
			archiveDrug = new DrugArchive("data/drug.txt");
			ServletHolder prescriptionHolder = new ServletHolder(
					PrescriptionServlet.class);
			servletContextHandler.addServlet(prescriptionHolder, "/patient/*");
			createDefaultPatients();
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createDefaultPatients() {
		int MAGIC_VALUE_ONE = 1;
		int MAGIC_VALUE_TWO = 2;
		Patient patientUn = new Patient();
		Patient patientDeux = new Patient();
		Patient patientTrois = new Patient();
		StaffMember staffUn = new StaffMember(MAGIC_VALUE_ONE);
		StaffMember staffDeux = new StaffMember(MAGIC_VALUE_TWO);
		/*
		 * patientsList.add(patientUn); patientsList.add(patientDeux);
		 * patientsList.add(patientTrois); Integer idPatientUn =
		 * patientsList.get(0).getId(); Integer idPatientDeux =
		 * patientsList.get(1).getId(); Integer idPatientTrois =
		 * patientsList.get(2).getId(); idsList.add(idPatientUn);
		 * idsList.add(idPatientDeux); idsList.add(idPatientTrois);
		 */
		EM.setEntityManager();
		EM.getUserTransaction().begin();
		EM.getEntityManager().persist(patientUn);
		EM.getEntityManager().persist(patientDeux);
		EM.getEntityManager().persist(patientTrois);
		EM.getEntityManager().persist(staffUn);
		EM.getEntityManager().persist(staffDeux);
		EM.getUserTransaction().commit();
	}
}