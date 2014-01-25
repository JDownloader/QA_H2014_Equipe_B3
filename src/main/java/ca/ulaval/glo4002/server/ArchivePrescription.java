package ca.ulaval.glo4002.server;

import java.util.ArrayList;

/* CODE REVIEW 25/01/2014
 * - Il faudrait définir une classe exception pour l'archivePrescription non trouvée (dans le style de ArchivePrescriptionNotFoundException)
 * - Je suis pas trop sur de comprendre le sens de ton commentaire, le id est autoincrement quand le caller va faire un new Prescription avant de le passer à addPrescription, la classe ArchivePrescription n'a pas à se soucier du id..
 * - Pour la technique itérative, d'accord si vous avez une meilleur façon de faire, en autant que ça soit aussi clean!
 * 
 * - Olivier R
 */

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
