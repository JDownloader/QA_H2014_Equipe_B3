package ca.ulaval.glo4002.rest;

import java.io.FileReader;
import java.io.IOException;
import java.util.EnumSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import ca.ulaval.glo4002.contexts.BadFileFormatException;
import ca.ulaval.glo4002.contexts.DemoDrugRepositoryFiller;
import ca.ulaval.glo4002.contexts.DemoPatientRepositoryFiller;
import ca.ulaval.glo4002.entitymanager.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.persistence.HibernateDrugRepository;
import ca.ulaval.glo4002.persistence.HibernatePatientRepository;
import ca.ulaval.glo4002.rest.filters.EntityManagerContextFilter;

import com.sun.jersey.spi.container.servlet.ServletContainer;

public class HospitalRestMain {
	private static HospitalRestConfigProvider restConfig = HospitalRestConfigProvider.getInstance();
	private static final int HTTP_SERVER_PORT = restConfig.getInt("SERVER_PORT", 8080);
	private static final String DRUG_FILE_PATH = "data/drug.txt";

	public static void main(String[] args) throws Exception {
		try {
			doDemoRepositoryFilling();
		} catch (Exception e) {
			System.out.println(String.format("An error occured while filling one or more repositories: %s", e.getMessage()));
			return;
		}

		new HospitalRestMain().execute();
	}

	private static void doDemoRepositoryFilling() throws IOException, BadFileFormatException {
		EntityManagerFactory entityManagerFactory = EntityManagerFactoryProvider.getFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		fillDrugRepository(entityManager);
		fillPatientRepository(entityManager);
		entityManager.close();
	}

	private static void fillDrugRepository(EntityManager entityManager) throws IOException, BadFileFormatException {
		HibernateDrugRepository hibernateDrugRepository = new HibernateDrugRepository(entityManager);
		FileReader fileReader = new FileReader(DRUG_FILE_PATH);
		new DemoDrugRepositoryFiller().fill(entityManager, hibernateDrugRepository, fileReader);
		fileReader.close();
	}

	private static void fillPatientRepository(EntityManager entityManager) {
		HibernatePatientRepository hibernatePatientRepository = new HibernatePatientRepository(entityManager);
		new DemoPatientRepositoryFiller().fill(entityManager, hibernatePatientRepository);
	}

	public void execute() throws Exception {
		Server server = new Server(HTTP_SERVER_PORT);
		
		ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");
		servletContextHandler.addFilter(EntityManagerContextFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));

		configureJersey(servletContextHandler);

		server.start();
		server.join();
	}

	private void configureJersey(ServletContextHandler servletContextHandler) {
		ServletHolder jerseyServletHolder = new ServletHolder(ServletContainer.class);
		jerseyServletHolder.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
		jerseyServletHolder.setInitParameter("com.sun.jersey.config.property.packages", "ca.ulaval.glo4002.rest");
		servletContextHandler.addServlet(jerseyServletHolder, "/*");
	}
}
