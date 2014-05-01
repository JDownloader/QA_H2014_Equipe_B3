package ca.ulaval.glo4002.domain.patient;

public interface PatientRepository {

	void persist(Patient patient);

	void merge(Patient patient);

	Patient getById(Integer id);
}
