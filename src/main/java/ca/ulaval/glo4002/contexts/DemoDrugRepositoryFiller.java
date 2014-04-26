package ca.ulaval.glo4002.contexts;

import java.io.IOException;
import java.io.Reader;

import javax.persistence.EntityManager;

import au.com.bytecode.opencsv.CSVReader;
import ca.ulaval.glo4002.domain.drug.*;

public class DemoDrugRepositoryFiller {

	private static final char DRUG_FILE_VALUE_SEPARATOR = ',';
	private static final int DRUG_IDENTIFICATION_NUMBER_COLUMN = 3;
	private static final int BRAND_NAME_COLUMN = 4;
	private static final int DESCRIPTOR_COLUMN = 5;
	
	private EntityManager entityManager;
	private DrugRepository drugRepository;
	
	public DemoDrugRepositoryFiller(EntityManager entityManager, DrugRepository drugRepository) {
		this.entityManager = entityManager;
		this.drugRepository = drugRepository;
	}

	public void fillDrugs(Reader reader) throws IOException {
		entityManager.getTransaction().begin();
		loadDrugsFromStream(reader);
		entityManager.getTransaction().commit();
	}

	public void loadDrugsFromStream(Reader reader) throws IOException {
		CSVReader csvReader = new CSVReader(reader, DRUG_FILE_VALUE_SEPARATOR);
		String[] nextLine;
		int lineNumber = 0;
		
		while ((nextLine = csvReader.readNext()) != null) {
			Drug drug = parseDrug(nextLine, ++lineNumber);
			drugRepository.persist(drug);
		}
		
		csvReader.close();
	}

	private Drug parseDrug(final String[] line, int lineNumber) {
		try {
			Din din = new Din(line[DRUG_IDENTIFICATION_NUMBER_COLUMN]);
			return new Drug(din, line[BRAND_NAME_COLUMN], line[DESCRIPTOR_COLUMN]);
		} catch (NumberFormatException e) {
			throw new BadFileFormatException(String.format("Could not parse line %d due to bad data format.", lineNumber));
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new BadFileFormatException(String.format("Could not parse line %d due to invalid number of values.", lineNumber));
		}
	}
}
