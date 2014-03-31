package ca.ulaval.glo4002.rest.resources;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.dto.DrugSearchDto;
import ca.ulaval.glo4002.services.DrugService;

public class DrugResourceTest {

	private static final String SAMPLE_JSON_REQUEST = "{attrib: value}";

	private DrugService drugServiceMock;
	private DrugSearchDto drugSearchDtoMock;
	private ObjectMapper objectMapperMock;
	private DrugResource drugResource;

	@Before
	public void init() throws Exception {
		drugServiceMock = mock(DrugService.class);
		drugSearchDtoMock = mock(DrugSearchDto.class);
		objectMapperMock = mock(ObjectMapper.class);
		drugResource = new DrugResource(drugServiceMock, objectMapperMock);
		when(objectMapperMock.readValue(anyString(), eq(DrugSearchDto.class))).thenReturn(drugSearchDtoMock);
	}

	@Test
	public void verifyDrugSearchCallsServiceMethodsCorrectly() throws Exception {
		drugResource.post(SAMPLE_JSON_REQUEST);
		verify(drugServiceMock).searchDrug(eq(drugSearchDtoMock));
	}

	@Test //TODO: Test Json response string
	public void verifyDrugSearchReturnsCreatedResponse() throws Exception {
		Response response = drugResource.post(SAMPLE_JSON_REQUEST);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifyDrugSearchReturnsBadRequestResponseWhenSpecifyingInvalidRequest() throws Exception {
		doThrow(new ServiceRequestException()).when(drugServiceMock).searchDrug(eq(drugSearchDtoMock));

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = drugResource.post(SAMPLE_JSON_REQUEST);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
	
	@Test
	public void verifyDrugSearchReturnsBadRequestResponseWhenSpecifyingInvalidJsonRequestString() throws Exception {
		doThrow(new JsonMappingException("")).when(objectMapperMock).readValue(anyString(), eq(DrugSearchDto.class));

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = drugResource.post(SAMPLE_JSON_REQUEST);
		
		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
}
