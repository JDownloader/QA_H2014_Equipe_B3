package ca.ulaval.glo4002.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.server.Server;

public class HospitalServer extends HttpServlet {

	private static int httpPort = 8080;
	private static Server server = new Server(httpPort);

	public static void start() {
		try {
			server.start();
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
