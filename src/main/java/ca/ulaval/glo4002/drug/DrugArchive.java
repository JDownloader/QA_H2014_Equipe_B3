package ca.ulaval.glo4002.drug;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import ca.ulaval.glo4002.exceptions.BadFileFormatException;
import ca.ulaval.glo4002.exceptions.ItemNotFoundException;

public class DrugArchive {
	private List<Drug> drugs = new ArrayList<Drug>();
	
	private static final int DRUG_IDENTIFICATION_NUMBER_COLUMN = 3;
	private static final int BRAND_NAME_COLUMN = 4;
	
	DrugArchive(Reader reader) throws IOException, BadFileFormatException {
		loadDrugFile(reader);
	}
	
	DrugArchive(String path) throws IOException, BadFileFormatException {
		FileReader fileReader = new FileReader(path);
		loadDrugFile(fileReader);
		fileReader.close();
	}
	
	public void loadDrugFile(Reader reader) throws IOException, BadFileFormatException {
		CSVReader csvReader = new CSVReader(reader, ',');
		String[] nextLine;
		int lineNumber = 0;
		while ((nextLine = csvReader.readNext()) != null) {
			 drugs.add(parseDrug(nextLine, ++lineNumber));
		}
		csvReader.close();
	}

	private Drug parseDrug(final String[] line, int lineNumber) throws BadFileFormatException {
		try {
			Drug.Builder drugBuilder = new Drug.Builder();
			drugBuilder.din(new Din(Integer.parseInt(line[DRUG_IDENTIFICATION_NUMBER_COLUMN])));
			drugBuilder.name(line[BRAND_NAME_COLUMN]);
			return drugBuilder.build();
		} catch (NumberFormatException e) {
			throw new BadFileFormatException(String.format("Could not parse line %d due to bad data format.", lineNumber));
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new BadFileFormatException(String.format("Could not parse line %d due to invalid number of values.", lineNumber));
		}
	}
	
	public Drug find(Din din) throws ItemNotFoundException {
		for (Drug drug : drugs) {
		    if (drug.getDin().equals(din)) {
		    	return drug;
		    }
		}
		throw new ItemNotFoundException(String.format("Cannot find Drug with din '%s'.", din));
	}
}
