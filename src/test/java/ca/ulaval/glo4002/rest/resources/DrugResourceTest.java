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
import ca.ulaval.glo4002.services.dto.DrugSearchDto;

public class DrugResourceTest {
	private DrugService drugServiceMock;
	private DrugSearchDto drugSearchDtoMock;
	private DrugResource drugResource;

	@Before
	public void init() throws Exception {
		drugServiceMock = mock(DrugService.class);
		drugSearchDtoMock = mock(DrugSearchDto.class);
		drugResource = new DrugResource(drugServiceMock);
	}

	@Test
	public void verifyDrugSearchCallsServiceMethodsCorrectly() throws Exception {
		drugResource.post(drugSearchDtoMock);
		verify(drugServiceMock).searchDrug(eq(drugSearchDtoMock));
	}

	@Test //TODO: Test Json response string
	public void verifyDrugSearchReturnsCreatedResponse() throws Exception {
		Response response = drugResource.post(drugSearchDtoMock);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifyDrugSearchReturnsBadRequestResponseWhenSpecifyingInvalidRequest() throws Exception {
		doThrow(new ServiceRequestException()).when(drugServiceMock).searchDrug(eq(drugSearchDtoMock));

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = drugResource.post(drugSearchDtoMock);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
}
