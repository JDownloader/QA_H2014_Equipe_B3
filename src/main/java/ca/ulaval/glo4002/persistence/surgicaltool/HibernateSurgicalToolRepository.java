package ca.ulaval.glo4002.persistence.surgicaltool;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalTool;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolRepository;
import ca.ulaval.glo4002.persistence.HibernateRepository;

public class HibernateSurgicalToolRepository extends HibernateRepository implements SurgicalToolRepository {

	public void create(SurgicalTool surgicalTool) {
		entityManager.persist(surgicalTool);
	}

	public void update(SurgicalTool surgicalTool) {
		entityManager.merge(surgicalTool);
	}

	public SurgicalTool getById(int id) throws EntityNotFoundException {
		SurgicalTool surgicalTool = entityManager.find(SurgicalTool.class, id);
		if (surgicalTool == null) {
			throw new EntityNotFoundException(String.format("Cannot find Surgical Tool with id '%s'.", id));
		}
		return surgicalTool;
	}

	public SurgicalTool getBySerialNumber(String serialNumber) throws EntityNotFoundException {
		String queryString = String.format("SELECT s FROM SURGICAL_TOOL s WHERE s.serialNumber = '%s'", serialNumber);
		TypedQuery<SurgicalTool> query = entityManager.createQuery(queryString, SurgicalTool.class);

		try {
			SurgicalTool result = query.getSingleResult();
			return result;
		} catch (NoResultException e) {
			throw new EntityNotFoundException(
					String.format("Cannot find Surgical Tool with serial '%s'.", serialNumber));
		}
	}
	
	//TODO: Fonction et requête écrite mais aucunement testée, ne fonctionne certainement pas
	public SurgicalTool getByTypeCode(String typeCode) throws EntityNotFoundException {
		String queryString = String.format("SELECT s FROM SURGICAL_TOOL s WHERE s.typecode = '%s' AND WHERE s.serialNumber = null", typeCode);
	
		TypedQuery<SurgicalTool> query = entityManager.createQuery(queryString, SurgicalTool.class);

		try {
			SurgicalTool result = (SurgicalTool) query.getParameter(query.getFirstResult());
			return result;
		} catch (NoResultException e) {
			throw new EntityNotFoundException(
					String.format("Cannot find anonymous Surgical Tool with typecode '%s'.", typeCode));
		}
	}

	public HibernateSurgicalToolRepository() {
		super();
	}

	public HibernateSurgicalToolRepository(EntityManager entityManager) {
		super(entityManager);
	}
}
