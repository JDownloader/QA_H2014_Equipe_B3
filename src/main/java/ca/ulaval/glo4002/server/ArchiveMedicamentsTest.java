package ca.ulaval.glo4002.server;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ArchiveMedicamentsTest {
    private static final int DIN_INVALIDE = -1;
    private static final int TRIFULUOPERAZINE_DIN = 326836;
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Test(expected=FileNotFoundException.class)
    public void ArchiveMedicamentLanceExceptionQuandFichierIntrouvable() throws IOException, FileNotFoundException, BadFileFormatException  {
	ArchiveMedicaments archiveMedicaments = new ArchiveMedicaments("UnexistingFile.txt");
	assertNull(archiveMedicaments);
    }     
    
    @Test(expected=BadFileFormatException.class)
    public void ArchiveMedicamentLanceExceptionQuandMauvaisFormatFichier() throws IOException, FileNotFoundException, BadFileFormatException  {
	ArchiveMedicaments archiveMedicaments = new ArchiveMedicaments("data/test/bad_format_drug_file.txt");
	assertNull(archiveMedicaments);
    }     
    
    @Test
    public void ArchiveMedicamentChargeFichierSansExceptions() throws IOException, FileNotFoundException, BadFileFormatException {
	ArchiveMedicaments archiveMedicaments = new ArchiveMedicaments("data/drug.txt");
	assertNotNull(archiveMedicaments);
    }
    
    @Test
    public void ArchiveMedicamentChargeFichierCorrectement() throws IOException, FileNotFoundException, BadFileFormatException, MedicamentNotFoundException {
	ArchiveMedicaments archiveMedicaments = new ArchiveMedicaments("data/drug.txt");
	Medicament medicament = archiveMedicaments.getMedicament(TRIFULUOPERAZINE_DIN);
	assertEquals("TRIFLUOPERAZINE", medicament.getNom());
    }
    
    @Test(expected=MedicamentNotFoundException.class)
    public void ArchiveMedicamentLanceExceptionQuandDinIntrouvable() throws IOException, FileNotFoundException, BadFileFormatException, MedicamentNotFoundException {
	ArchiveMedicaments archiveMedicaments = new ArchiveMedicaments("data/drug.txt");
	archiveMedicaments.getMedicament(DIN_INVALIDE);
    }
}
