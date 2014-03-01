package ca.ulaval.glo4002.domain.intervention;

public enum InterventionStatus {
	PLANIFIEE(1), EN_COURS(2), TERMINEE(3), ANNULEE(4), REPORTEE(5);
	private int value;
	
	private InterventionStatus(int value) {
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
