package ca.ulaval.glo4002.rest;

import java.io.IOException;

import org.eclipse.jetty.server.Server;

import ca.ulaval.glo4002.contexts.BadFileFormatException;
import ca.ulaval.glo4002.contexts.DemoRepositoryFiller;

public class DemoMain {
	private static HospitalRestConfigProvider restConfig = HospitalRestConfigProvider.getInstance();
	private static final int HTTP_SERVER_PORT = restConfig.getInt("SERVER_PORT", 8080);

	public static void main(String[] args) throws Exception {
		try {
			DemoRepositoryFiller.fillRepositories();
		} catch (IOException | BadFileFormatException e) {
			System.out.println(String.format("Une erreur est survenue lors du remplissage des entrepôts: %s", e.getMessage()));
			return;
		}

		Server server = new JettyServer().create(HTTP_SERVER_PORT);
        server.start();
        server.join();
	}
}
