package ca.ulaval.glo4002.rest.resources;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.services.DrugService;
import ca.ulaval.glo4002.services.dto.DrugSearchDTO;
import ca.ulaval.glo4002.services.dto.validators.DrugSearchDTOValidator;

public class DrugResourceTest {
	private static final String SAMPLE_DRUG_NAME = "ROSAVIL";
	
	private DrugService drugServiceMock;
	private DrugSearchDTO drugSearchDTOMock;
	private DrugResource drugResource;

	@Before
	public void init() throws Exception {
		drugServiceMock = mock(DrugService.class);
		drugSearchDTOMock = mock(DrugSearchDTO.class);
		drugResource = new DrugResource(drugServiceMock);
	}

	@Test
	public void verifyDrugSearchCallsServiceMethodsCorrectly() throws Exception {
		drugResource.get(SAMPLE_DRUG_NAME);
		verify(drugServiceMock).searchDrug(eq(drugSearchDTOMock), any(DrugSearchDTOValidator.class));
	}

	@Test
	public void verifyDrugSearchReturnsOkResponse() throws Exception {
		Response response = drugResource.get(SAMPLE_DRUG_NAME);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifyDrugSearchReturnsBadRequestResponseOnServiceRequestException() throws Exception {
		doThrow(new ServiceRequestException()).when(drugServiceMock).searchDrug(eq(drugSearchDTOMock), any(DrugSearchDTOValidator.class));

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = drugResource.get(SAMPLE_DRUG_NAME);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
}
