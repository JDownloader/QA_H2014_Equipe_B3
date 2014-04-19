package ca.ulaval.glo4002.services.assemblers;

import java.text.ParseException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.unitils.reflectionassert.ReflectionAssert.*;
import ca.ulaval.glo4002.domain.drug.Din;
import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.domain.prescription.Prescription;
import ca.ulaval.glo4002.domain.staff.StaffMember;
import ca.ulaval.glo4002.services.dto.PrescriptionCreationDTO;

public class PrescriptionAssemblerTest {
	private static final Date SAMPLE_DATE_PARAMETER = new Date(3);
	private static final Integer SAMPLE_RENEWALS_PARAMETER = 2;
	private static final String SAMPLE_DRUG_NAME_PARAMETER = "drug_name";
	private static final String SAMPLE_STAFF_MEMBER_PARAMETER = "3";
	private static final Din SAMPLE_DIN_PARAMETER = new Din("098423");
	
	PrescriptionAssembler prescriptionAssembler = new PrescriptionAssembler();
	PrescriptionCreationDTO prescriptionCreationDTO = new PrescriptionCreationDTO();
	DrugRepository drugRepositoryMock;
	Drug drugMock;
	
	@Before
	public void init() {
		setupDTO();
		drugRepositoryMock = mock(DrugRepository.class);
		drugMock = mock(Drug.class);
		when(drugRepositoryMock.getByDin(any(Din.class))).thenReturn(drugMock);
	}

	private void setupDTO() {
		prescriptionCreationDTO.staffMember = SAMPLE_STAFF_MEMBER_PARAMETER;
		prescriptionCreationDTO.date = SAMPLE_DATE_PARAMETER;
		prescriptionCreationDTO.renewals = SAMPLE_RENEWALS_PARAMETER;
		prescriptionCreationDTO.drugName = SAMPLE_DRUG_NAME_PARAMETER;
		prescriptionCreationDTO.din = SAMPLE_DIN_PARAMETER;
	}
	
	@Test
	public void callsCorrectRepositoryMethod() {
		prescriptionAssembler.assembleFromDTO(prescriptionCreationDTO, drugRepositoryMock);
		verify(drugRepositoryMock).getByDin(any(Din.class));
	}
	
	@Test
	public void assemblesPrescriptionWithDinCorrectly() throws ParseException {
		Prescription assembledPrescription = prescriptionAssembler.assembleFromDTO(prescriptionCreationDTO, drugRepositoryMock);
		Prescription expectedprescription = new Prescription(drugMock, SAMPLE_RENEWALS_PARAMETER, 
				SAMPLE_DATE_PARAMETER, new StaffMember(SAMPLE_STAFF_MEMBER_PARAMETER));
		
		assertReflectionEquals(expectedprescription, assembledPrescription);
	}
	
	@Test
	public void assemblesPrescriptionWithNoDinCorrectly() throws ParseException {
		prescriptionCreationDTO.din = null;
		
		Prescription assembledPrescription = prescriptionAssembler.assembleFromDTO(prescriptionCreationDTO, drugRepositoryMock);
		Prescription expectedPrescription = new Prescription(SAMPLE_DRUG_NAME_PARAMETER, SAMPLE_RENEWALS_PARAMETER, 
				SAMPLE_DATE_PARAMETER, new StaffMember(SAMPLE_STAFF_MEMBER_PARAMETER));
		
		assertReflectionEquals(expectedPrescription, assembledPrescription);
	}
}
