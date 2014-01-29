package ca.ulaval.glo4002.staff;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "STAFF")
public class StaffMember {

	@Id
	@Column(name = "STAFF_ID", nullable = false)
	private int id;

	public StaffMember(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

}
