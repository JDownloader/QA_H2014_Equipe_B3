package ca.ulaval.glo4002.server;

public class Patient {
	private int id;

	/*
	 * ID devrait pas être un argument mais être auto Incremanté
	 * 
	 * -Vince l-g
	 */
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
