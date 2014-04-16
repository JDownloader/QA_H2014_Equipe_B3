package ca.ulaval.glo4002.contexts;

import java.io.FileReader;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ca.ulaval.glo4002.entitymanager.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.persistence.HibernateDrugRepository;
import ca.ulaval.glo4002.persistence.HibernatePatientRepository;

public class DemoRepositoryFiller {
	private static final String DRUG_FILE_PATH = "data/drug.txt";
	
	public static void fillRepositories() throws IOException {
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
