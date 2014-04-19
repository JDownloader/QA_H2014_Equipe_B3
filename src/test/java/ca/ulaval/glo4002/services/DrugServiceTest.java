package ca.ulaval.glo4002.services;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.persistence.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.services.dto.DrugSearchDTO;
import ca.ulaval.glo4002.services.dto.validators.DrugSearchDTOValidator;

@RunWith(MockitoJUnitRunner.class)
public class DrugServiceTest {

	private static final String SAMPLE_DRUG_NAME = "drug_name";

	private DrugService drugService;

	private DrugRepository drugRepositoryMock;
	private EntityManager entityManagerMock;
	private EntityTransaction entityTransactionMock;

	private DrugSearchDTO drugSearchDTO = new DrugSearchDTO();
	private DrugSearchDTOValidator drugSearchDToValidatorMock;

	@Before
	public void init() {
		createMocks();
		stubMethods();
		setupDTO();
		drugService = new DrugService(drugRepositoryMock, entityManagerMock);
	}

	private void createMocks() {
		drugRepositoryMock = mock(DrugRepository.class);
		entityManagerMock = mock(EntityManager.class);
		entityTransactionMock = mock(EntityTransaction.class);
		drugSearchDToValidatorMock = mock(DrugSearchDTOValidator.class);
	}

	private void stubMethods() {
		when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
	}

	private void setupDTO() {
		drugSearchDTO.name = SAMPLE_DRUG_NAME;
	}

	@Test
	public void verifyDrugSearchCallsCorrectRepositoryMethods() throws Exception {
		drugService.searchDrug(drugSearchDTO, drugSearchDToValidatorMock);

		verify(drugRepositoryMock).search(SAMPLE_DRUG_NAME);
	}

	@Test
	public void verifyDrugSearchBeginsAndCommitsTransaction() throws Exception {
		drugService.searchDrug(drugSearchDTO, drugSearchDToValidatorMock);
		InOrder inOrder = inOrder(entityTransactionMock);

		inOrder.verify(entityTransactionMock).begin();
		inOrder.verify(entityTransactionMock).commit();
	}

	@Test
	public void verifyDrugSearchRollsbackOnException() throws Exception {
		when(entityTransactionMock.isActive()).thenReturn(true);
		when(drugRepositoryMock.search(anyString())).thenThrow(new EntityNotFoundException());

		try {
			drugService.searchDrug(drugSearchDTO, drugSearchDToValidatorMock);
		} catch (ServiceRequestException e) {
			verify(entityTransactionMock).rollback();
			return;
		}
	}

	@Test
	public void verifyDrugSearchDoesNotRollbackOnSuccessfulCommit() throws Exception {
		when(entityTransactionMock.isActive()).thenReturn(false);

		drugService.searchDrug(drugSearchDTO, drugSearchDToValidatorMock);

		verify(entityTransactionMock).commit();
		verify(entityTransactionMock, never()).rollback();
	}
}
