package ca.ulaval.glo4002.domain.drug;

public class DrugFactory {

	public Drug createDrug(String name) {
		return new DrugWithoutDin(name);
	}

	public Drug createDrug(Din din, String name) {
		return new DrugWithDin(din, name);
	}

	public Drug createDrug(Din din, String name, String description) {
		return new DrugWithDin(din, name, description);
	}
}
