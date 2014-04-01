package ca.ulaval.glo4002.services;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.drug.*;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.services.DrugService;
import ca.ulaval.glo4002.services.dto.DrugSearchDto;

@RunWith(MockitoJUnitRunner.class)
public class DrugServiceTest {

	private static final String SAMPLE_DRUG_NAME = "drug_name";
	
	private DrugService drugService;
	
	private DrugRepository drugRepositoryMock;
	private EntityManager entityManagerMock;
	private EntityTransaction entityTransactionMock;

	private DrugSearchDto drugSearchDtoMock;

	@Before
	public void init() {
		createMocks();
		stubMethods();
		drugService = new DrugService(drugRepositoryMock, entityManagerMock);
	}

	private void createMocks() {
		drugRepositoryMock = mock(DrugRepository.class);
		entityManagerMock = mock(EntityManager.class);
		entityTransactionMock = mock(EntityTransaction.class);
		drugSearchDtoMock = mock(DrugSearchDto.class);
	}

	private void stubMethods() {
		when(drugSearchDtoMock.getName()).thenReturn(SAMPLE_DRUG_NAME);
		when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
	}

	@Test
	public void verifySearchDrugCallsCorrectRepositoryMethods() throws Exception {
		drugService.searchDrug(drugSearchDtoMock);

		verify(drugRepositoryMock).search(SAMPLE_DRUG_NAME);
	}

	@Test
	public void verifyAddPrescriptionBeginsAndCommitsTransaction() throws Exception {
		drugService.searchDrug(drugSearchDtoMock);
		InOrder inOrder = inOrder(entityTransactionMock);

		inOrder.verify(entityTransactionMock).begin();
		inOrder.verify(entityTransactionMock).commit();
	}
	
	@Test
	public void verifyAddPrescriptionRollsbackOnException() throws Exception {
		when(entityTransactionMock.isActive()).thenReturn(true);
		when(drugRepositoryMock.search(anyString())).thenThrow(new EntityNotFoundException());

		try {
			drugService.searchDrug(drugSearchDtoMock);
		} catch(ServiceRequestException e) {
			verify(entityTransactionMock).rollback();
			return;
		}
	}
	
	@Test
	public void verifyAddPrescriptionDoesNotRollbackOnSuccessfulCommit() throws Exception {
		when(entityTransactionMock.isActive()).thenReturn(false);

		drugService.searchDrug(drugSearchDtoMock);
		
		verify(entityTransactionMock).commit();
		verify(entityTransactionMock, never()).rollback();
	}
}
