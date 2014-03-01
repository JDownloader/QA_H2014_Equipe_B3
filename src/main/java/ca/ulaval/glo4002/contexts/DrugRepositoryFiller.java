package ca.ulaval.glo4002.contexts;

import java.io.IOException;
import java.io.Reader;

import javax.persistence.EntityManager;

import au.com.bytecode.opencsv.CSVReader;
import ca.ulaval.glo4002.domain.drug.*;
import ca.ulaval.glo4002.exceptions.BadFileFormatException;

public class DrugRepositoryFiller {
	
	private static final int DRUG_IDENTIFICATION_NUMBER_COLUMN = 3;
	private static final int BRAND_NAME_COLUMN = 4;
	
	public void fill(EntityManager entityManager, DrugRepository drugRepository, Reader reader) throws IOException, BadFileFormatException {
		entityManager.getTransaction().begin();
		loadDrugFile(drugRepository, reader);
		entityManager.getTransaction().commit();
	}
	
	public void loadDrugFile(DrugRepository drugRepository, Reader reader) throws IOException, BadFileFormatException {
		CSVReader csvReader = new CSVReader(reader, ',');
		String[] nextLine;
		int lineNumber = 0;
		while ((nextLine = csvReader.readNext()) != null) {
			Drug drug = parseDrug(nextLine, ++lineNumber);
			drugRepository.create(drug);
		}
		csvReader.close();
	}

	private Drug parseDrug(final String[] line, int lineNumber) throws BadFileFormatException {
		try {
			DrugBuilder drugBuilder = new DrugBuilder();
			drugBuilder.din(new Din(Integer.parseInt(line[DRUG_IDENTIFICATION_NUMBER_COLUMN])));
			drugBuilder.name(line[BRAND_NAME_COLUMN]);
			return drugBuilder.build();
		} catch (NumberFormatException e) {
			throw new BadFileFormatException(String.format("Could not parse line %d due to bad data format.", lineNumber));
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new BadFileFormatException(String.format("Could not parse line %d due to invalid number of values.", lineNumber));
		}
	}
}
