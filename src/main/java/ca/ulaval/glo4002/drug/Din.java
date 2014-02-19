package ca.ulaval.glo4002.drug;

import java.io.Serializable;

public class Din implements Serializable {
	private static final long serialVersionUID = 7369022975618045987L;
	private int din = 0;

	public Din(int Din) {
		this.din = Din;
	}

	@Override
	public String toString() {
		return String.valueOf(din);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Din otherDin = (Din) obj;
		if (this.din != otherDin.din)
			return false;
		return true;
	}
}
