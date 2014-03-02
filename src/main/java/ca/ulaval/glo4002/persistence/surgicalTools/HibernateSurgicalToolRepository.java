package ca.ulaval.glo4002.persistence.surgicalTools;

import javax.persistence.EntityManager;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalTool;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolRepository;
import ca.ulaval.glo4002.persistence.HibernateRepository;

public class HibernateSurgicalToolRepository extends HibernateRepository implements SurgicalToolRepository {
	
	public void create(SurgicalTool surgicalTool) {
		entityManager.persist(surgicalTool);
	}
	
	public HibernateSurgicalToolRepository() {
		super();
	}
	
	public HibernateSurgicalToolRepository(EntityManager entityManager) {
		super(entityManager);
	}
}
