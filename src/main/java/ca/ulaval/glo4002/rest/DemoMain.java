package ca.ulaval.glo4002.rest;

import java.io.FileReader;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.eclipse.jetty.server.Server;

import ca.ulaval.glo4002.contexts.BadFileFormatException;
import ca.ulaval.glo4002.contexts.DemoDrugRepositoryFiller;
import ca.ulaval.glo4002.contexts.DemoPatientRepositoryFiller;
import ca.ulaval.glo4002.entitymanager.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.persistence.HibernateDrugRepository;
import ca.ulaval.glo4002.persistence.HibernatePatientRepository;

public class DemoMain {
	private static HospitalRestConfigProvider restConfig = HospitalRestConfigProvider.getInstance();
	private static final int HTTP_SERVER_PORT = restConfig.getInt("SERVER_PORT", 8080);
	private static final String DRUG_FILE_PATH = "data/drug.txt";

	public static void main(String[] args) throws Exception {
		try {
			doDemoRepositoryFilling();
		} catch (Exception e) {
			System.out.println(String.format("Une erreur est survenue lors du remplissage des entrepôts: %s", e.getMessage()));
			return;
		}

		Server server = new JettyServer().create(HTTP_SERVER_PORT);
        server.start();
        server.join();
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
}
