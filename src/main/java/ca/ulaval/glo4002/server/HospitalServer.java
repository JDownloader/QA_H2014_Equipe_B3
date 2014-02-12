package ca.ulaval.glo4002.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import ca.ulaval.glo4002.drug.DrugArchive;
import ca.ulaval.glo4002.prescription.PrescriptionArchive;

import com.sun.jersey.spi.container.servlet.ServletContainer;

public class HospitalServer {
	
	static PrescriptionArchive archivePrescription = new PrescriptionArchive();
	static DrugArchive archiveDrug;

	public static void main(String[] args) throws Exception {
		int httpPort = 8080;
		Server server = new Server(httpPort);
		
		ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");
		ServletHolder jerseyServletHolder = new ServletHolder(ServletContainer.class);
		jerseyServletHolder.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
		jerseyServletHolder.setInitParameter("com.sun.jersey.config.property.packages", "ca.ulaval.glo4002.rest");
		servletContextHandler.addServlet(jerseyServletHolder, "/*");
		
		server.start();
		server.join();
	}
	
}