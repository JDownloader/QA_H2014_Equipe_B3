package ca.ulaval.glo4002.rest.resources;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.services.DrugService;
import ca.ulaval.glo4002.services.ServiceException;
import ca.ulaval.glo4002.services.dto.DrugSearchDTO;
import ca.ulaval.glo4002.services.dto.validators.DrugSearchDTOValidator;

@RunWith(MockitoJUnitRunner.class)
public class DrugResourceTest {
	private static final String SAMPLE_DRUG_NAME = "ROSAVIL";
	
	private DrugService drugServiceMock;
	private DrugResource drugResource;

	@Before
	public void init() throws Exception {
		drugServiceMock = mock(DrugService.class);
		drugResource = new DrugResource(drugServiceMock);
	}

	@Test
	public void verifyDrugSearchCallsServiceMethodsCorrectly() throws Exception {
		drugResource.get(SAMPLE_DRUG_NAME);
		verify(drugServiceMock).searchDrug(any(DrugSearchDTO.class), any(DrugSearchDTOValidator.class));
	}

	@Test
	public void verifyDrugSearchReturnsOkResponse() throws Exception {
		Response response = drugResource.get(SAMPLE_DRUG_NAME);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifyDrugSearchReturnsBadRequestResponseOnServiceRequestException() throws Exception {
		doThrow(new ServiceException()).when(drugServiceMock).searchDrug(any(DrugSearchDTO.class), any(DrugSearchDTOValidator.class));
		Response receivedResponse = drugResource.get(SAMPLE_DRUG_NAME);
		assertEquals(Status.BAD_REQUEST.getStatusCode(), receivedResponse.getStatus());
	}
}
