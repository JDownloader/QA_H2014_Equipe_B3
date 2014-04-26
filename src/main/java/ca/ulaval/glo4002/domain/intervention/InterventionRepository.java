package ca.ulaval.glo4002.domain.intervention;

public interface InterventionRepository {
	void persist(Intervention intervention);
	
	Intervention getById(Integer id);
}
