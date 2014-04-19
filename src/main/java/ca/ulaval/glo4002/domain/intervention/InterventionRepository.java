package ca.ulaval.glo4002.domain.intervention;

public interface InterventionRepository {
	public void persist(Intervention intervention);

	public void update(Intervention intervention);

	public Intervention getById(int id);
}
