package ca.ulaval.glo4002.persistence;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.surgicaltool.*;

public class HibernateSurgicalToolRepository extends HibernateRepository implements SurgicalToolRepository {

	public void persist(SurgicalTool surgicalTool) throws SurgicalToolExistsException {
		try {
			entityManager.persist(surgicalTool);
		} catch (PersistenceException e) {
			throw new SurgicalToolExistsException("Erreur - Numéro de série déjà utilisé", e);
		}
	}

	public void update(SurgicalTool surgicalTool) {
		entityManager.merge(surgicalTool);
	}
	
	public SurgicalTool getBySerialNumberOrId(String serialNumberOrId) throws SurgicalToolNotFoundException {
		try {
			return this.getBySerialNumber(serialNumberOrId);
		} catch (SurgicalToolNotFoundException e) {
			try {
				return this.getById(Integer.parseInt(serialNumberOrId));
			} catch (NumberFormatException numberFormatException) {
				throw new SurgicalToolNotFoundException(String.format("Impossible de trouver l'instrument avec numéro de série ou no unique '%s'.", serialNumberOrId), numberFormatException);
			}
		}
	}
	
	public SurgicalTool getBySerialNumber(String serialNumber) throws SurgicalToolNotFoundException {
		final String QUERY = "SELECT s FROM SURGICAL_TOOL s WHERE s.serialNumber = :serialNumber";
		
		TypedQuery<SurgicalTool> query = entityManager.createQuery(QUERY, SurgicalTool.class)
				.setParameter("serialNumber", serialNumber);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			throw new SurgicalToolNotFoundException(String.format("Impossible de trouver l'instrument avec numéro de série '%s'.", serialNumber), e);
		}
	}

	public SurgicalTool getById(Integer id) throws SurgicalToolNotFoundException {
		SurgicalTool surgicalTool = entityManager.find(SurgicalTool.class, id);
		if (surgicalTool == null) {
			throw new SurgicalToolNotFoundException(String.format("Impossible de trouver l'instrument avec id '%s'.", id));
		}
		return surgicalTool;
	}

	public HibernateSurgicalToolRepository() {
		super();
	}

	public HibernateSurgicalToolRepository(EntityManager entityManager) {
		super(entityManager);
	}
}
