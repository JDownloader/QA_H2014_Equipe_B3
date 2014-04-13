package ca.ulaval.glo4002.domain.patient;

public interface PatientRepository {

	void create(Patient patient) throws PatientExistsException;

	void update(Patient patient);

	Patient getById(Integer id) throws PatientNotFoundException;
}
