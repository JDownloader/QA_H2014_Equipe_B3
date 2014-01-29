package ca.ulaval.glo4002.server;

import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import ca.ulaval.glo4002.drug.DrugArchive;
import ca.ulaval.glo4002.prescription.PrescriptionArchive;

public class HospitalServer extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static int httpPort = 8080;
	private static Server server = new Server(httpPort);
	static ServletContextHandler servletContextHandler = new ServletContextHandler(
			server, "/");
	static PrescriptionArchive archivePrescription = new PrescriptionArchive();
	static DrugArchive archiveDrug;

	public static void main(String[] args) {
		try {
			archiveDrug = new DrugArchive("data/drug.txt");
			ServletHolder prescriptionHolder = new ServletHolder(
					PrescriptionServlet.class);
			servletContextHandler.addServlet(prescriptionHolder,
					"/prescription");
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}