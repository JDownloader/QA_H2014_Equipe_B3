package ca.ulaval.glo4002.rest.utils;

import static org.mockito.Mockito.*;
import static org.unitils.reflectionassert.ReflectionAssert.*;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.services.dto.BadRequestDTO;

@RunWith(MockitoJUnitRunner.class)
public class ResponseAssemblerTest {
	
	private static final Status SAMPLE_STATUS = Status.BAD_REQUEST;
	private static final String SAMPLE_ERROR_CODE = "INT0011";
	private static final String SAMPLE_MESSAGE = "message";
	private static final String SAMPLE_LOCATION_STRING = "/my/location/";
	
	private Object objectMock;
	private URI uri;
	
	@Before
	public void init() throws URISyntaxException {
		objectMock = mock(Object.class);
		uri = new URI(SAMPLE_LOCATION_STRING);
	}
	
	@Test
	public void assemblesOkResponseCorrectly() {
		Response expectedResponse = Response.status(Status.OK).build();
		Response actualResponse = ResponseAssembler.assembleOkResponse();
		assertReflectionEquals(expectedResponse, actualResponse);
	}
	
	@Test
	public void assemblesOkResponseWithObjectCorrectly() {
		Response expectedResponse = Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(objectMock).build();
		Response actualResponse = ResponseAssembler.assembleOkResponse(objectMock);
		assertReflectionEquals(expectedResponse, actualResponse);
	}
	
	@Test
	public void assemblesCreatedResponseCorrectly() {
		Response expectedResponse = Response.status(Status.CREATED).location(uri).build();
		Response actualResponse = ResponseAssembler.assembleCreatedResponse(uri);
		assertReflectionEquals(expectedResponse, actualResponse);
	}
	
	@Test
	public void assemblesErrorResponseCorrectly() {
		Response expectedResponse = Response.status(SAMPLE_STATUS).entity(new BadRequestDTO(SAMPLE_ERROR_CODE, SAMPLE_MESSAGE)).build();
		Response actualResponse = ResponseAssembler.assembleErrorResponse(SAMPLE_STATUS, SAMPLE_ERROR_CODE, SAMPLE_MESSAGE);
		assertReflectionEquals(expectedResponse, actualResponse);
	}
}
