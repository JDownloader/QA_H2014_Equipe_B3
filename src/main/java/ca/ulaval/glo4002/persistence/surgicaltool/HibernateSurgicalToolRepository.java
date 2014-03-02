package ca.ulaval.glo4002.persistence.surgicaltool;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

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
	
	public SurgicalTool getBySerialNumber(String serialNumber) throws EntityNotFoundException {
		SurgicalTool surgicalTool = entityManager.find(SurgicalTool.class, serialNumber);
		if (surgicalTool == null) {
			throw new EntityNotFoundException(String.format("Cannot find Patient with serial '%s'.", serialNumber));
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
