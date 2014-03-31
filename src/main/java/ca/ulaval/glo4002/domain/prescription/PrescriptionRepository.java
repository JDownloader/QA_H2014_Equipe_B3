package ca.ulaval.glo4002.domain.prescription;

import javax.persistence.EntityExistsException;

public interface PrescriptionRepository {
	void persist(Prescription prescription) throws EntityExistsException;
}
