package ca.ulaval.glo4002.contexts;

import java.io.FileReader;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.entitymanager.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.persistence.HibernateDrugRepository;
import ca.ulaval.glo4002.persistence.HibernatePatientRepository;

public class DemoRepositoryFiller  {
	private static final String DEFAULT_DRUG_FILE_PATH = "data/drug.txt";
	private static final String DEFAULT_DRUG_INTERACTIONS_FILE_PATH = "data/interactions.txt";
	
	private String drugFilePath;
	private String drugInteractionsPath;
	
	EntityManager entityManager;
	PatientRepository patientRepository;
	DrugRepository drugRepository;

	public DemoRepositoryFiller() {
		this.drugFilePath = DEFAULT_DRUG_FILE_PATH;
		this.drugInteractionsPath = DEFAULT_DRUG_INTERACTIONS_FILE_PATH;
	}
	
	public DemoRepositoryFiller(String drugFilePath, String drugInteractionsPath) {
		this.drugFilePath = drugFilePath;
		this.drugInteractionsPath = drugInteractionsPath;
	}
	
	public void fillRepositories() throws IOException {
		EntityManagerFactory entityManagerFactory = EntityManagerFactoryProvider.getFactory();
		entityManager = entityManagerFactory.createEntityManager();
		createDefaultRepositories();
		
		fillDrugRepository();
		fillDrugInteractions();
		fillPatientRepository();
		
		entityManager.close();
	}
	
	private void createDefaultRepositories() {
		drugRepository = new HibernateDrugRepository(entityManager);
		patientRepository = new HibernatePatientRepository(entityManager);
	}

	private void fillDrugRepository() throws IOException {
		FileReader fileReader = new FileReader(drugFilePath);
		new DemoDrugRepositoryFiller(entityManager, drugRepository).fillDrugs(fileReader);
		fileReader.close();
	}
	
	private void fillDrugInteractions() throws IOException {
		FileReader fileReader = new FileReader(drugInteractionsPath);
		new DemoDrugInteractionsFiller(entityManager, drugRepository).fillInteractions(fileReader);
		fileReader.close();
	}

	private void fillPatientRepository() {
		new DemoPatientRepositoryFiller().fill(entityManager, patientRepository);
	}
}
