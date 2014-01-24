package ca.ulaval.glo4002.server;

public class Staff {
	private int id;

	/*
	 * ID devrait pas être un argument mais être auto Incremanté
	 * 
	 * -Vince l-g
	 */
	public Staff(int p_id) {
		id = p_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int p_id) {
		id = p_id;
	}
}
