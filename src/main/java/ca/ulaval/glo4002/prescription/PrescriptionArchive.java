package ca.ulaval.glo4002.prescription;

import java.util.ArrayList;

public class PrescriptionArchive {

	ArrayList<Prescription> archive;

	public PrescriptionArchive() {
		archive = new ArrayList<Prescription>();
	}

	public Prescription getPrescription(int id) throws Exception {

		for (Prescription prescription : archive) {
			if (prescription.getId() == id)
				return prescription;
		}

		throw new Exception("Unable to find prescription");
	}

}
