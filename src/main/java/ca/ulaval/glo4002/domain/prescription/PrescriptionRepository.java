package ca.ulaval.glo4002.domain.prescription;

import javax.persistence.EntityExistsException;

public interface PrescriptionRepository {
	void create(Prescription prescription) throws EntityExistsException;
}
