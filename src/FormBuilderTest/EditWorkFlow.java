package FormBuilderTest;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import Files.PayLoadData;

import Files.Resources;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class EditWorkFlow {

	@Test
	public void invalidToken()
	{
		Resources.invalidToken("Workflow5","Workflow5");
	}
	@Test

	public void invalidOrganization()
	{
		Resources.invalidOrganization("100", "workflow5", "workflow5");
	}
	@Test
	public void EditWorkFlow()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res=given().
		headers("Content-Type","application/json").headers("Authorization","token 8f62fd0d5c5d5f43c22bf18e841d7117b3f20514").
		body(PayLoadData.AddWorkflow("ms2","ms2")).
		when().put(Resources.editForms("6","79"));
		int status_code=res.getStatusCode();
		Assert.assertEquals(status_code, 200);
		//return status_code;
		
	}
}
