package ca.ulaval.glo4002.domain.drug;


public class DrugWithoutDin implements Drug {
	
	private String name = "";

	private String description = "";

	public DrugWithoutDin(String name) {
		this.name=name;
	}
	
	@Override
	public Din getDin() throws DrugDoesntHaveDinExeption {
		throw new DrugDoesntHaveDinExeption();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

}
