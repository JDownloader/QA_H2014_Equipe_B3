package ca.ulaval.glo4002.services.drug;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.persistence.drug.HibernateDrugRepository;
import ca.ulaval.glo4002.rest.dto.DrugSearchDto;
import ca.ulaval.glo4002.rest.dto.DrugSearchResultDto;

public class DrugService {
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

	public DrugSearchResultDto searchDrug(DrugSearchDto drugSearchDto) throws ServiceRequestException, Exception {
		try {
			entityTransaction.begin();
			List<Drug> drugResults = drugRepository.search(drugSearchDto.getName());
			entityTransaction.commit();
			return buildDrugSearchResultDto(drugResults);
		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw e;
		}
	}
	
	private DrugSearchResultDto buildDrugSearchResultDto(List<Drug> drugResults) throws NoSuchFieldException {
		DrugSearchResultDto drugSearchResultDto = new DrugSearchResultDto();
		drugSearchResultDto.setDrugSearchResultEntries(drugResults);
		return drugSearchResultDto;
	}

}
