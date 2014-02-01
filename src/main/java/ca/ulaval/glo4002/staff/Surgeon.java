package ca.ulaval.glo4002.staff;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "SURGEON")
public class Surgeon extends StaffMember {

	@Column(name = "SURGEON_ID")
	private int id;

	public Surgeon(int id) {
		super(id);
	}

	public int getId() {
		return this.id;
	}
}
