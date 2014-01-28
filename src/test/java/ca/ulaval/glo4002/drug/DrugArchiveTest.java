package ca.ulaval.glo4002.drug;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ca.ulaval.glo4002.drug.DrugArchive;
import ca.ulaval.glo4002.exeptions.BadFileFormatException;

public class DrugArchiveTest {
	private static final int DIN_INVALIDE = -1;
	private static final int TRIFULUOPERAZINE_DIN = 326836;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test(expected=FileNotFoundException.class)
	public void ArchiveMedicamentLanceExceptionQuandFichierIntrouvable() throws IOException, FileNotFoundException, BadFileFormatException  {
		DrugArchive archiveMedicaments = new DrugArchive("UnexistingFile.txt");
		assertNull(archiveMedicaments);
	}     

	@Test(expected=BadFileFormatException.class)
	public void ArchiveMedicamentLanceExceptionQuandMauvaisFormatFichier() throws IOException, FileNotFoundException, BadFileFormatException  {
		DrugArchive archiveMedicaments = new DrugArchive("data/test/drug_file_w_missing_values.txt");
		assertNull(archiveMedicaments);
	}    

	@Test(expected=BadFileFormatException.class)
	public void ArchiveMedicamentLanceExceptionQuandMauvaisFormatFichier2() throws IOException, FileNotFoundException, BadFileFormatException  {
		DrugArchive archiveMedicaments = new DrugArchive("data/test/drug_file_w_invalid_din.txt");
		assertNull(archiveMedicaments);
	}     

	@Test
	public void ArchiveMedicamentChargeFichierSansExceptions() throws IOException, FileNotFoundException, BadFileFormatException {
		DrugArchive archiveMedicaments = new DrugArchive("data/drug.txt");
		assertNotNull(archiveMedicaments);
	}

	@Test
	public void ArchiveMedicamentChargeFichierCorrectement() throws IOException, FileNotFoundException, BadFileFormatException, DrugNotFoundException {
		DrugArchive archiveMedicaments = new DrugArchive("data/drug.txt");
		Drug medicament = archiveMedicaments.getMedicament(TRIFULUOPERAZINE_DIN);
		assertEquals("TRIFLUOPERAZINE", medicament.getNom());
	}

	@Test(expected=DrugNotFoundException.class)
	public void ArchiveMedicamentLanceExceptionQuandDinIntrouvable() throws IOException, FileNotFoundException, BadFileFormatException, DrugNotFoundException {
		DrugArchive archiveMedicaments = new DrugArchive("data/drug.txt");
		archiveMedicaments.getMedicament(DIN_INVALIDE);
	}
}
