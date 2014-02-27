package ca.ulaval.glo4002.drug;

import java.io.IOException;

import ca.ulaval.glo4002.dao.DataAccessObject;
import ca.ulaval.glo4002.dao.DataAccessTransaction;
import ca.ulaval.glo4002.exceptions.BadFileFormatException;
import ca.ulaval.glo4002.exceptions.ItemNotFoundException;

public class DrugDAO extends DataAccessObject implements IDrugDAO {
	
	private static final String DRUG_FILE_PATH = "data/drug.txt";
	private static DrugArchive drugArchive = null;
	
	DataAccessTransaction transaction = null;
	
	
	public DrugDAO(DataAccessTransaction transaction) {
		this.transaction = transaction;
	}
	
	public static void init() throws IOException, BadFileFormatException {
		drugArchive = new DrugArchive(DRUG_FILE_PATH);
	}
	
	public void create(Drug entity) {
		transaction.getEntityManager().persist(entity);
	}

	public void update(Drug entity) {
		throw new UnsupportedOperationException();
	}

	public void delete(Drug entity) {
		throw new UnsupportedOperationException();
	}

	public Drug get(Din din) throws ItemNotFoundException {
		Drug drug = transaction.getEntityManager().find(Drug.class, din);
		if (drug == null) {
			//Fallback search in archive
			drug = drugArchive.find(din);
			create(drug); //Persist
		}
		return drug;
	}
}
