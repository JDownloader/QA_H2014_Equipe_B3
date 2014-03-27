package ca.ulaval.glo4002.services.drug;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.drug.DrugSearchRequestParser;

public class DrugServiceTest {
	private static final String SAMPLE_DRUG_NAME = "drug_name";

	private DrugRepository drugRepositoryMock;
	private EntityTransaction entityTransactionMock;
	private DrugService drugService;

	private DrugSearchRequestParser drugSearchRequestParserMock;

	@Before
	public void init() {
		createMocks();
		buildInterventionService();
		stubCreateInterventionRequestMockMethods();
	}

	private void createMocks() {
		drugRepositoryMock = mock(DrugRepository.class);
		entityTransactionMock = mock(EntityTransaction.class);
		drugSearchRequestParserMock = mock(DrugSearchRequestParser.class);
	}

	private void buildInterventionService() {
		DrugServiceBuilder drugServiceBuilder = new DrugServiceBuilder()
			.drugRepository(drugRepositoryMock)
			.entityTransaction(entityTransactionMock);
		drugService = drugServiceBuilder.build();
	}

	private void stubCreateInterventionRequestMockMethods() {
		when(drugSearchRequestParserMock.getName()).thenReturn(SAMPLE_DRUG_NAME);
	}

	@Test
	public void verifySearchDrugCallsCorrectRepositoryMethods() throws Exception {
		drugService.searchDrug(drugSearchRequestParserMock);

		verify(drugRepositoryMock).search(SAMPLE_DRUG_NAME);
	}

	@Test
	public void verifySearchDrugBeginsAndCommitsTransaction() throws Exception {
		drugService.searchDrug(drugSearchRequestParserMock);
		InOrder inOrder = inOrder(entityTransactionMock);

		inOrder.verify(entityTransactionMock).begin();
		inOrder.verify(entityTransactionMock).commit();
	}

	@Test(expected = ServiceRequestException.class)
	public void verifySearchDrugThrowsWhenSpecifyingNonExistingPatientNumber() throws Exception {
		when(drugRepositoryMock.search(anyString())).thenThrow(new ServiceRequestException());

		drugService.searchDrug(drugSearchRequestParserMock);
	}
	
	@Test
	public void verifySearchDrugRollsbackOnException() throws Exception {
		when(entityTransactionMock.isActive()).thenReturn(true);
		when(drugRepositoryMock.search(anyString())).thenThrow(new ServiceRequestException());

		try {
			drugService.searchDrug(drugSearchRequestParserMock);
		} catch(ServiceRequestException e) {
			verify(entityTransactionMock).rollback();
			return;
		}
		
		fail();
	}
	
	@Test
	public void verifySearchDrugDoesNotRollbackOnSuccessfulCommit() throws Exception {
		when(entityTransactionMock.isActive()).thenReturn(false);

		drugService.searchDrug(drugSearchRequestParserMock);
		
		verify(entityTransactionMock).commit();
		verify(entityTransactionMock, never()).rollback();
	}

}
