package ca.ulaval.glo4002.domain.drug;

public class DrugFactory {

	public Drug createDrug(String name) {
		return new DrugWithNoDin(name);
	}

	public Drug createDrug(Din din, String name, String description) {
		return new DrugWithDin(din, name, description);
	}
}
