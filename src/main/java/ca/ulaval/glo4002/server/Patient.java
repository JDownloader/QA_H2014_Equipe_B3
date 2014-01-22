package ca.ulaval.glo4002.server;

public class Patient {
	private int id;

	public Patient(int p_id) {
		id = p_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int p_id) {
		id = p_id;
	}
}
