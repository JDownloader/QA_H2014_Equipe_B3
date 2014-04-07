package ca.ulaval.glo4002.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.persistence.HibernateDrugRepository;
import ca.ulaval.glo4002.services.dto.DrugSearchDTO;
import ca.ulaval.glo4002.services.dto.validators.DrugSearchDTOValidator;

public class DrugService {
	public static final String ERROR_SERVICE_REQUEST_EXCEPTION_DIN001 = "DIN001";
	
	private DrugRepository drugRepository;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;

	public DrugService() {
		this.drugRepository =  new HibernateDrugRepository();
		this.entityManager = new EntityManagerProvider().getEntityManager();
		this.entityTransaction = entityManager.getTransaction();
	}
	
	public DrugService(DrugRepository drugRepository, EntityManager entityManager) {
		this.drugRepository =  drugRepository;
		this.entityManager = entityManager;
		this.entityTransaction = entityManager.getTransaction();		
	}

	public List<Drug> searchDrug(DrugSearchDTO drugSearchDTO, DrugSearchDTOValidator drugSearchDTOValidator) throws ServiceRequestException {
		try {
			drugSearchDTOValidator.validate(drugSearchDTO);
			entityTransaction.begin();
			List<Drug> drugResults = drugRepository.search(drugSearchDTO.getName());
			entityTransaction.commit();
			return drugResults;
		} catch (Exception e) {
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_DIN001, e.getMessage());
		} finally {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
		}
	}
}
