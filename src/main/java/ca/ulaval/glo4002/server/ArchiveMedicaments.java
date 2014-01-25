package ca.ulaval.glo4002.server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import ca.ulaval.glo4002.server.Medicament;
import ca.ulaval.glo4002.error.BadFileFormatException;
import ca.ulaval.glo4002.error.MedicamentNotFoundException;

/*
 * 86% coverage, j'aime
 * 
 * @author Vincent
 * 
 */

public class ArchiveMedicaments {
	private static final int DRUG_IDENTIFICATION_NUMBER_COLUMN = 3;
	private static final int BRAND_NAME_COLUMN = 4;

	private List<Medicament> medicaments = new ArrayList<Medicament>();

	public ArchiveMedicaments(String pathOfDrugTextFile) throws FileNotFoundException, IOException, BadFileFormatException {
		CSVReader reader = new CSVReader(new FileReader(pathOfDrugTextFile), ',');

		String[] nextLine;
		int lineNumber = 0;
		while ((nextLine = reader.readNext()) != null) {
			medicaments.add(parseMedicament(nextLine, ++lineNumber));
		}

		reader.close();
	}

	private Medicament parseMedicament(final String[] line, int lineNumber) throws BadFileFormatException {
		try {
			return new Medicament(Integer.parseInt(line[DRUG_IDENTIFICATION_NUMBER_COLUMN]), line[BRAND_NAME_COLUMN]);
		} catch (NumberFormatException e) {
			throw new BadFileFormatException(String.format("Could not parse line %d due to bad data format.", lineNumber));
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new BadFileFormatException(String.format("Could not parse line %d due to invalid number of values.", lineNumber));
		}
	}

	/*
	 * il faudrait trouver une alternative à l'approche ittérative
	 * 
	 * -Vince L-G
	 */

	public Medicament getMedicament(int din) throws MedicamentNotFoundException {
		for (Medicament medicament : medicaments) {
			if (medicament.getDin() == din) {
				return medicament;
			}
		}

		throw new MedicamentNotFoundException(String.format("Cannot find 'Medicament' with id '%s'.", din));
	}
}
