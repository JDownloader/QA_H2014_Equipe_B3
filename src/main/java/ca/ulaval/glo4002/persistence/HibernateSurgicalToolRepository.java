package ca.ulaval.glo4002.persistence;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.surgicaltool.*;

public class HibernateSurgicalToolRepository extends HibernateRepository implements SurgicalToolRepository {

	private static final String SELECT_SURGICALTOOL_BY_SERIALNUMBER_QUERY = "SELECT s FROM SURGICAL_TOOL s WHERE s.serialNumber = :serialNumber";
	private static final String SERIAL_NUMBER_PARAMETER = "serialNumber";

	public HibernateSurgicalToolRepository() {
		super();
	}

	public HibernateSurgicalToolRepository(EntityManager entityManager) {
		super(entityManager);
	}

	public void persist(SurgicalTool surgicalTool) {
		try {
			entityManager.persist(surgicalTool);
			entityManager.flush();
		} catch (PersistenceException e) {
			throw new SurgicalToolExistsException(String.format("Un instrument avec le numéro de série '%s' existe déjà.", surgicalTool.getSerialNumber()), e);
		}
	}

	public SurgicalTool getBySerialNumberOrId(String serialNumberOrId) {
		try {
			return this.getBySerialNumber(serialNumberOrId);
		} catch (SurgicalToolNotFoundException e) {
			try {
				return this.getById(Integer.parseInt(serialNumberOrId));
			} catch (NumberFormatException numberFormatException) {
				throw new SurgicalToolNotFoundException(String.format("Impossible de trouver l'instrument avec numéro de série ou no unique '%s'.",
						serialNumberOrId), numberFormatException);
			}
		}
	}

	public SurgicalTool getBySerialNumber(String serialNumber) {
		TypedQuery<SurgicalTool> query = entityManager.createQuery(SELECT_SURGICALTOOL_BY_SERIALNUMBER_QUERY, SurgicalTool.class).setParameter(
				SERIAL_NUMBER_PARAMETER, serialNumber);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			throw new SurgicalToolNotFoundException(String.format("Impossible de trouver l'instrument avec numéro de série '%s'.", serialNumber), e);
		}
	}

	public SurgicalTool getById(Integer id) {
		SurgicalTool surgicalTool = entityManager.find(SurgicalTool.class, id);
		if (surgicalTool == null) {
			throw new SurgicalToolNotFoundException(String.format("Impossible de trouver l'instrument avec id '%s'.", id));
		}
		return surgicalTool;
	}
}
