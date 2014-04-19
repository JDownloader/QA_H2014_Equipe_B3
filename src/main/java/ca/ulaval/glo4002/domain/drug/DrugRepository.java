package ca.ulaval.glo4002.domain.drug;

import java.util.List;

public interface DrugRepository {

	void persist(Drug drug);

	Drug getByDin(Din din);

	List<Drug> search(String name);
}
