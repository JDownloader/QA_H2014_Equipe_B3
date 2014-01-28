package ca.ulaval.glo4002.drug;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import ca.ulaval.glo4002.exeptions.BadFileFormatException;

/*
 * 86% coverage, j'aime
 * 
 * @author Vincent
 * 
 */

public class DrugArchive {
	private static final int DRUG_IDENTIFICATION_NUMBER_COLUMN = 3;
	private static final int BRAND_NAME_COLUMN = 4;

	private List<Drug> medicaments = new ArrayList<Drug>();

	public DrugArchive(String pathOfDrugTextFile)
			throws FileNotFoundException, IOException, BadFileFormatException {
		CSVReader reader = new CSVReader(new FileReader(pathOfDrugTextFile),
				',');

		String[] nextLine;
		int lineNumber = 0;
		while ((nextLine = reader.readNext()) != null) {
			medicaments.add(parseMedicament(nextLine, ++lineNumber));
		}

		reader.close();
	}

	private Drug parseMedicament(final String[] line, int lineNumber)
			throws BadFileFormatException {
		try {
			return new Drug(
					Integer.parseInt(line[DRUG_IDENTIFICATION_NUMBER_COLUMN]),
					line[BRAND_NAME_COLUMN]);
		} catch (NumberFormatException e) {
			throw new BadFileFormatException(String.format(
					"Could not parse line %d due to bad data format.",
					lineNumber));
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new BadFileFormatException(String.format(
					"Could not parse line %d due to invalid number of values.",
					lineNumber));
		}
	}

	/*
	 * il faudrait trouver une alternative à l'approche ittérative
	 * 
	 * -Vince L-G
	 */

	public Drug getMedicament(int din) throws DrugNotFoundException {
		for (Drug medicament : medicaments) {
			if (medicament.getDin() == din) {
				return medicament;
			}
		}

		throw new DrugNotFoundException(String.format(
				"Cannot find 'Medicament' with id '%s'.", din));
	}
}
