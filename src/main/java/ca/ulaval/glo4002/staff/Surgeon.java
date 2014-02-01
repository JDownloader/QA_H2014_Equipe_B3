package ca.ulaval.glo4002.staff;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "SURGEON")
public class Surgeon extends StaffMember {

	@Id
	@Column(name = "SURGEON_ID", nullable = false)
	private int id;

	public Surgeon(int id) {
		super(id);
	}

	public int getId() {
		return this.id;
	}
}
