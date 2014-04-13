package ca.ulaval.glo4002.acceptation;

import javax.ws.rs.core.Response;

import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.junit.JUnitStory;

import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

public class InterventionCreationAcceptationTest extends JUnitStory {
	//First scenario : créer une intervention avec des informations manquantes
	@Given("a $patient")
	@Aliases(values={"a new intervention : a $patient"})
	public void s1g() {
		
	}
	
	@When("I send in a new $request")
	public void s1w() {
		
	}
	
	@Then("the response should be $response")
	@Aliases(values={"the response should be $response"})
	public void s1t(Response response) {
		
	}
}
