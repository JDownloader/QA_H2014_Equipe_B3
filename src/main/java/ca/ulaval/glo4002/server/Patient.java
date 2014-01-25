package ca.ulaval.glo4002.server;

import java.util.ArrayList;

public class Patient {
	private int id;
	private static int idMax = 0;
	private ArrayList<Integer> presciptionId;

	public Patient() {
		incrementAutoId();
	}

	public int getId() {
		return id;
	}

	public void addPrescription(int idPrescription) {
		presciptionId.add(idPrescription);
	}

	private void incrementAutoId() {
		this.id = idMax;
		idMax++;
	}
}
