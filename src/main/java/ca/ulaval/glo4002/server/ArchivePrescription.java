package ca.ulaval.glo4002.server;

import java.util.ArrayList;

public class ArchivePrescription {

	ArrayList<Prescription> archive;

	public ArchivePrescription() {
		archive = new ArrayList<Prescription>();
	}

	public Prescription getPrescription(int id) throws Exception {

		for (Prescription prescription : archive) {
			if (prescription.getId() == id)
				return prescription;
		}

		throw new Exception("Unable to find prescription"); // TODO: Define an
															// exception class
	}

	public void addPrescription(Prescription prescription) {
		archive.add(prescription);
		/*
		 * je pense qu'il faudrait que je fasse un builder dans prescription et
		 * que cela soit appeler par add, comme cela le id serait autoincrement
		 * et on pourrait implenter get(), qui est plus rapide que la technique
		 * itterative.
		 * 
		 * -vince L-G
		 */
	}

}
