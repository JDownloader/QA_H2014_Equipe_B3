package ca.ulaval.glo4002.uats.steps;

import static com.jayway.restassured.RestAssured.given;

import javax.ws.rs.core.Response.Status;

import org.jbehave.core.annotations.Then;
import org.json.JSONObject;
import org.junit.Assert;

import ca.ulaval.glo4002.uats.runners.JettyTestRunner;
import ca.ulaval.glo4002.uats.steps.contexts.ThreadLocalContext;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class HttpResponseSteps {
	Response response;
	public static final String LAST_RESPONSE_OBJECT_KEY = "response_key";
	
	@Then("une erreur est retournée")
	public void returnsAnError() {
		response = (Response) ThreadLocalContext.getObject(LAST_RESPONSE_OBJECT_KEY);
		
		response.then().
			statusCode(Status.BAD_REQUEST.getStatusCode());
	}
	
	@Then("cette erreur a le code \"$errorCode\"")
	public void checkErrorCode(String errorCode) {
		String bodyString = response.getBody().asString();
		JSONObject jsonObject = new JSONObject(bodyString);
		Assert.assertEquals(errorCode, jsonObject.get("code"));
	}
	
	protected static RequestSpecification getDefaultRequestSepcification(JSONObject jsonObject) {
		return given().port(JettyTestRunner.JETTY_TEST_PORT)
				.body(jsonObject.toString())
				.contentType("application/json; charset=UTF-8")
				.when();
	}
}
