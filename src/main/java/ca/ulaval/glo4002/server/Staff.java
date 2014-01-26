package ca.ulaval.glo4002.server;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "STAFF")
public class Staff {

	@Id
	@Column(name = "STAFF_ID", nullable = false)
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
