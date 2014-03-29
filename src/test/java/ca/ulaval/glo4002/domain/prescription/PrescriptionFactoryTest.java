package ca.ulaval.glo4002.domain.prescription;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import ca.ulaval.glo4002.domain.drug.Din;
import ca.ulaval.glo4002.domain.drug.DrugFactory;
import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.rest.dto.PrescriptionCreationDto;

public class PrescriptionFactoryTest {
	private static final int SAMPLE_DIN_PARAMETER = 3;
	private static final String SAMPLE_DRUG_NAME_PARAMETER = "drug_name";
	
	PrescriptionFactory prescriptionFactory;
	PrescriptionCreationDto prescriptionCreationDtoMock;
	DrugRepository drugRepositoryMock;
	DrugFactory drugFactoryMock;
	
	@Before
	public void init() {
		drugFactoryMock = mock(DrugFactory.class);
		prescriptionFactory = new PrescriptionFactory(drugFactoryMock);
		prescriptionCreationDtoMock = mock(PrescriptionCreationDto.class);
		drugRepositoryMock = mock(DrugRepository.class);
	}
	
	@Test
	public void retrievesExistingDrugFromRepositoryWhenDtoContainsDin() {
		when(prescriptionCreationDtoMock.hasDin()).thenReturn(true);
		when(prescriptionCreationDtoMock.getDin()).thenReturn(SAMPLE_DIN_PARAMETER);
		
		prescriptionFactory.createPrescription(prescriptionCreationDtoMock, drugRepositoryMock);
		
		verify(drugRepositoryMock).getByDin(new Din(SAMPLE_DIN_PARAMETER));
	}
	
	@Test
	public void createsNewDrugWhenDtoContainsDrugName() {
		when(prescriptionCreationDtoMock.hasDin()).thenReturn(false);
		when(prescriptionCreationDtoMock.getDrugName()).thenReturn(SAMPLE_DRUG_NAME_PARAMETER);
		
		prescriptionFactory.createPrescription(prescriptionCreationDtoMock, drugRepositoryMock);
		
		verify(drugFactoryMock).createDrug(SAMPLE_DRUG_NAME_PARAMETER);
	}
}
