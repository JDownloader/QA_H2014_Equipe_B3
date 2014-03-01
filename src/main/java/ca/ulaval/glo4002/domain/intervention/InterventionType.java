package ca.ulaval.glo4002.domain.intervention;

import java.io.Serializable;

public enum InterventionType implements Serializable {
	OEIL(1), COEUR(2), MOELLE(3), ONCOLOGIQUE(4), AUTRE(5);
	private int value;
	
	private InterventionType(int value) {
        this.value = value;
    }
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	public int getValue() {
		return value;
	}
}
