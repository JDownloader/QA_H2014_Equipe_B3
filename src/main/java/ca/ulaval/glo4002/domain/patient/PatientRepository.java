package ca.ulaval.glo4002.domain.patient;

public interface PatientRepository {

	public void persist(Patient patient);

	public void update(Patient patient);

	public Patient getById(Integer id);
}
