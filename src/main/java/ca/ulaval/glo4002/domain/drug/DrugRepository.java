package ca.ulaval.glo4002.domain.drug;

import java.util.List;

public interface DrugRepository {

	public void persist(Drug drug);

	public Drug getByDin(Din din);

	public List<Drug> search(String name);
}
