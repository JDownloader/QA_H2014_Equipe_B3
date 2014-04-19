package ca.ulaval.glo4002.contexts;

import java.io.FileReader;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ca.ulaval.glo4002.entitymanager.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.persistence.HibernateDrugRepository;
import ca.ulaval.glo4002.persistence.HibernatePatientRepository;

public class DemoRepositoryFiller {
	private static final String DEFAULT_DRUG_FILE_PATH = "data/drug.txt";
	private String drugFilePath;
	
	public DemoRepositoryFiller() {
		drugFilePath = DEFAULT_DRUG_FILE_PATH;
	}
	
	public DemoRepositoryFiller(String drugFilePath) {
		this.drugFilePath = drugFilePath;
	}
	
	public void fillRepositories() throws IOException {
		EntityManagerFactory entityManagerFactory = EntityManagerFactoryProvider.getFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		fillDrugRepository(entityManager);
		fillPatientRepository(entityManager);
		
		entityManager.close();
	}

	private void fillDrugRepository(EntityManager entityManager) throws IOException, BadFileFormatException {
		HibernateDrugRepository hibernateDrugRepository = new HibernateDrugRepository(entityManager);
		FileReader fileReader = new FileReader(drugFilePath);
		
		new DemoDrugRepositoryFiller().fill(entityManager, hibernateDrugRepository, fileReader);
		
		fileReader.close();
	}

	private void fillPatientRepository(EntityManager entityManager) {
		HibernatePatientRepository hibernatePatientRepository = new HibernatePatientRepository(entityManager);
		
		new DemoPatientRepositoryFiller().fill(entityManager, hibernatePatientRepository);
	}
}
