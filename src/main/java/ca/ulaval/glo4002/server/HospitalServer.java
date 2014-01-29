package ca.ulaval.glo4002.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import ca.ulaval.glo4002.persistence.EM;

/*
 * il faudrait automatiser les test
 * 
 * @author Vincent
 * 
 */
public class HospitalServer extends HttpServlet {

	private static int httpPort = 8080;
	private static Server server = new Server(httpPort);
	static ServletContextHandler servletContextHandler = new ServletContextHandler(
			server, "/");

	public static void main(String[] args) {
		try {
			servletContextHandler.addServlet(new ServletHolder(
					PrescriptionServlet.class), "/prescription");
			server.start();
			EM.setEntityManager();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void init() {
		try {
			super.init();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stop() {
		try {
			server.stop();
			EM.closeEntityManager();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
