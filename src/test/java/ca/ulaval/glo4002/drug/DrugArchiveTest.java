package ca.ulaval.glo4002.drug;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ca.ulaval.glo4002.exceptions.BadFileFormatException;

public class DrugArchiveTest {
	private static final int INVALID_DIN = -1;
	private static final int TRIFULUOPERAZINE_DIN = 326836;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test(expected = FileNotFoundException.class)
	public void ArchiveMedicamentLanceExceptionQuandFichierIntrouvable()
			throws IOException, FileNotFoundException, BadFileFormatException {
		DrugArchive drugsArchive = new DrugArchive("UnexistingFile.txt");
		assertNull(drugsArchive);
	}

	@Test(expected = BadFileFormatException.class)
	public void ArchiveMedicamentLanceExceptionQuandMauvaisFormatFichier()
			throws IOException, FileNotFoundException, BadFileFormatException {
		DrugArchive drugsArchive = new DrugArchive(
				"data/test/drug_file_w_missing_values.txt");
		assertNull(drugsArchive);
	}

	@Test(expected = BadFileFormatException.class)
	public void ArchiveMedicamentLanceExceptionQuandMauvaisFormatFichier2()
			throws IOException, FileNotFoundException, BadFileFormatException {
		DrugArchive drugsArchive = new DrugArchive(
				"data/test/drug_file_w_invalid_din.txt");
		assertNull(drugsArchive);
	}

	@Test
	public void ArchiveMedicamentChargeFichierSansExceptions()
			throws IOException, FileNotFoundException, BadFileFormatException {
		DrugArchive drugsArchive = new DrugArchive("data/drug.txt");
		assertNotNull(drugsArchive);
	}

	@Test
	public void ArchiveMedicamentChargeFichierCorrectement()
			throws IOException, FileNotFoundException, BadFileFormatException,
			DrugNotFoundException {
		DrugArchive drugsArchive = new DrugArchive("data/drug.txt");
		Drug medicament = drugsArchive.getDrug(TRIFULUOPERAZINE_DIN);
		assertEquals("TRIFLUOPERAZINE", medicament.getName());
	}

	@Test(expected = DrugNotFoundException.class)
	public void ArchiveMedicamentLanceExceptionQuandDinIntrouvable()
			throws IOException, FileNotFoundException, BadFileFormatException,
			DrugNotFoundException {
		DrugArchive drugsArchive = new DrugArchive("data/drug.txt");
		drugsArchive.getDrug(INVALID_DIN);
	}
}
