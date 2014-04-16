package ca.ulaval.glo4002.uats.steps;

import javax.ws.rs.core.Response.Status;

import org.jbehave.core.annotations.Then;
import org.json.JSONObject;
import org.junit.Assert;

import com.jayway.restassured.response.Response;

public class HttpResponseSteps {
	Response response;
	public static final String RESPONSE_OBJECT_KEY = "response";
	
	@Then("une erreur est retournée")
	public void returnsAnError() {
		response = (Response) ThreadLocalMap.getObject(RESPONSE_OBJECT_KEY);
		
		response.then().
			statusCode(Status.BAD_REQUEST.getStatusCode());
	}
	
	@Then("cette erreur a le code \"$errorCode\"")
	public void checkErrorCode(String errorCode) {
		String bodyString = response.getBody().asString();
		JSONObject jsonObject = new JSONObject(bodyString);
		Assert.assertEquals(errorCode, jsonObject.get("code"));
	}

}
