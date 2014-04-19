package ca.ulaval.glo4002.domain.patient;

public interface PatientRepository {

	void persist(Patient patient);

	void update(Patient patient);

	Patient getById(Integer id);
}
