package ca.ulaval.glo4002.services.drug;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import ca.ulaval.glo4002.domain.drug.DrugRepository;
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
		stubEntityTransactionsMethods();
	}

	private void createMocks() {
		drugRepositoryMock = mock(DrugRepository.class);
		entityTransactionMock = mock(EntityTransaction.class);
		drugSearchRequestParserMock = mock(DrugSearchRequestParser.class);
	}

	private void buildInterventionService() {
		DrugServiceBuilder drugServiceBuilder = new DrugServiceBuilder();
		drugServiceBuilder.drugRepository(drugRepositoryMock);
		drugServiceBuilder.entityTransaction(entityTransactionMock);
		drugService = drugServiceBuilder.build();
	}

	private void stubCreateInterventionRequestMockMethods() {
		when(drugSearchRequestParserMock.getName()).thenReturn(SAMPLE_DRUG_NAME);
	}

	private void stubEntityTransactionsMethods() {
		when(entityTransactionMock.isActive()).thenReturn(true);
	}

	@Test
	public void verifySearchDrugCallsCorrectRepositoryMethods() throws Exception {
		drugService.searchDrug(drugSearchRequestParserMock);

		verify(drugRepositoryMock).findByName(SAMPLE_DRUG_NAME);
	}

	@Test
	public void verifySearchDrugTransactionHandling() throws Exception {
		drugService.searchDrug(drugSearchRequestParserMock);
		InOrder inOrder = inOrder(entityTransactionMock);

		inOrder.verify(entityTransactionMock).begin();
		inOrder.verify(entityTransactionMock).commit();
	}

	@Test(expected = Exception.class)
	public void verifySearchDrugThrowsWhenSpecifyingNonExistingPatientNumber() throws Exception {
		when(drugRepositoryMock.findByName(anyString())).thenThrow(new Exception());

		drugService.searchDrug(drugSearchRequestParserMock);

		verify(entityTransactionMock).rollback();
	}

}
