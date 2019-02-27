package Files;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.net.URI;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Resources {

	public static String[] Createworkflow(String form,String title)
	{
		//RestAssured.baseURI="https://sandbox.veris.in";
		Response res = given().
		headers("Content-Type","application/json").headers("Authorization","token 8f62fd0d5c5d5f43c22bf18e841d7117b3f20514").
		body(PayLoadData.AddWorkflow(form,title)).
		when().post(Resources.Workflow("6"));
		int status_code = res.getStatusCode();
		String response=res.asString();
		JsonPath path = new JsonPath(response);
		int form_id=path.get("id");
		System.out.println("Form_id "+form_id);
		System.out.println(status_code);
		 String form1 = String.valueOf(form_id);
		 String status =String.valueOf(status_code);
		 String res1[]= {form1,status};
		return res1;
	}
	public static String MapWorkflow()
	{
		String p="api/v2/organization/6/map-forms/";
		return p;
	}
	public static int[] EditCustomScreen(int custom_screen_id)
	{
		String id=String.valueOf(custom_screen_id);
		//RestAssured.baseURI="https://sandbox.veris.in";
		Response res = given().
		headers("Content-Type","application/json").headers("Authorization","token 8f62fd0d5c5d5f43c22bf18e841d7117b3f20514").
		body(PayLoadData.editNewScreen()).
		when().put("/api/v2/organization/6/screens/"+id+"/");
		int res_code=res.getStatusCode();
		String response=res.asString();
		System.out.println("EditCustomScreen response "+response);
		JsonPath path=new JsonPath(response);
		int form_field_id=path.get("forms[0].form_fields[0].id");
		System.out.println("form_id "+form_field_id);
		int result[]= {res_code,form_field_id};
		return result;
	}
	public static int[] addCustomScreens()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res = given().
		headers("Content-Type","application/json").headers("Authorization","token 8f62fd0d5c5d5f43c22bf18e841d7117b3f20514").
		body(PayLoadData.addCustomScreens()).
		when().post(Resources.screen());
		int code =res.getStatusCode();
		String response=res.asString();
		JsonPath path = new JsonPath(response);
		int form_id=path.get("forms[0].id");
		System.out.println("Custorm Screen_id "+form_id);
		int result[]= {code,form_id};
		return result;
	}public static int[] MapCustomScreen(int custom_screen_id,int form_id)
	{
		//RestAssured.baseURI="https://sandbox.veris.in";
		Response res = given().
		headers("Content-Type","application/json").headers("Authorization","token 8f62fd0d5c5d5f43c22bf18e841d7117b3f20514").
		body(PayLoadData.MapCustomScreen(custom_screen_id)).
		when().put(Resources.addScreens(form_id));
		int status=res.getStatusCode();
		String response=res.asString();
		System.out.println("MapCustomScreen Response is "+response);
		JsonPath path=new JsonPath(response);
		int custom_screen_mapping_id=path.get("screens[0].id");
		System.out.println("custom_screen_mapping_id "+custom_screen_mapping_id);
		int result[]= {status,custom_screen_mapping_id};
		return result;
	}
	public static int[] getScreens()
	{
		
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res = given().
		headers("Content-Type","application/json").headers("Authorization","token 8f62fd0d5c5d5f43c22bf18e841d7117b3f20514").
		when().get(Resources.screen());
		int code=res.getStatusCode();
		String response=res.asString();
		JsonPath path=new JsonPath(response);
		int count=path.getInt("count");
		System.out.println(count);
		int result[]= {code,count};
		return result;
	}
	public static String screen()
	{
		String s="/api/v2/organization/6/screens/";
		return s;
	}
	public static int deleteScreen(int screen_id)
	{
		String id=String.valueOf(screen_id);
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res= given().
		headers("Content-Type","application/json").headers("Authorization","token 8f62fd0d5c5d5f43c22bf18e841d7117b3f20514").
		when().delete("/api/v2/organization/6/screens/"+id+"/");
		int code=res.getStatusCode();
		System.out.println("Deletion Code");
		return code;
		
	}
	public static int MapWorkflows(int form_id)
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res= given().
		headers("Content-Type","application/json").headers("Authorization","token 8f62fd0d5c5d5f43c22bf18e841d7117b3f20514").
		body(PayLoadData.mappingWorkflow(form_id,12)).
		when().post(Resources.MapWorkflow());
		int code=res.getStatusCode();
		return code;

	}
	public static String Workflow(String or_id) {
		
		String p="/api/v2/organization/"+or_id+"/forms/";
		return p;
	}

	public static int[] getForms()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res = given().
		headers("Content-Type","application/json").headers("Authorization","token 8f62fd0d5c5d5f43c22bf18e841d7117b3f20514").
		when().get(Resources.Workflow("6"));
		int status_code=res.getStatusCode();
		String response=res.asString();
		JsonPath path = new JsonPath(response);
		int count = path.getInt("count");
		int array[]= {status_code,count};
		return array;
	}
	public static int  removeExistingScreens(int screen_workflow_mapping_id,int form_id)
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
		headers("Content-Type","application/json").headers("Authorization","token 8f62fd0d5c5d5f43c22bf18e841d7117b3f20514").
		body(PayLoadData.removeexixtingscreen(String.valueOf(screen_workflow_mapping_id))).
		when().put(Resources.addScreens(form_id));
		int statuscode=res.getStatusCode();
		return statuscode;
	}	
	public static int invalidOrganization(String o_id,String form,String title)
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
		headers("Content-Type","application/json").headers("Authorization","token 8f62fd0d5c5d5f43c22bf18e841d7117b3f20514").
		body(PayLoadData.AddWorkflow(form,title)).
		when().post(Resources.Workflow(o_id));
		int statuscode=res.getStatusCode();
		return statuscode;
	}
	public static int EditWorkFlow(String form_id)
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res=given().
		headers("Content-Type","application/json").headers("Authorization","token 8f62fd0d5c5d5f43c22bf18e841d7117b3f20514").
		body(PayLoadData.AddWorkflow("ms2","ms2")).
		when().put(Resources.editForms("6",form_id));
		int status_code=res.getStatusCode();
		return status_code;
	}
	public static int invalidToken(String form,String title)
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
		headers("Content-Type","application/json").headers("Authorization","token 8we62fd0d5c5d5f43c22bf18e841d7117b3f20514").
		body(PayLoadData.AddWorkflow(form,title)).
		when().post(Resources.Workflow("6"));
		int statuscode=res.getStatusCode();
		return statuscode;
	}
	public static String addScreens(int form_id)
	{
		
		String id=String.valueOf(form_id);
		//System.out.println("Id "+id);
		String s="/api/v2/organization/6/forms/"+id+"/";
		System.out.println(s);
		return s;
	}
	public static int[] AddExistingScreeens(int form_id)
	{
		//RestAssured.baseURI="https://sandbox.veris.in";
		Response res = given().
		headers("Content-Type","application/json").headers("Authorization","token 8f62fd0d5c5d5f43c22bf18e841d7117b3f20514").
		body(PayLoadData.addScreens()).
		when().put(Resources.addScreens(form_id));
		int code=res.statusCode();
		String response=res.asString();
		System.out.println("AddExistingScreeens Response is "+response);
		JsonPath path=new JsonPath(response);
		int screen_workflow_mapping_id=path.getInt("screens[0].id");
		System.out.println("screen_workflow_mapping_id "+screen_workflow_mapping_id);
		int result[]= {code,screen_workflow_mapping_id};
		return result;
	}
	public static int getUnfixScreens(int form_id)
	{
		
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res = given().
		headers("Content-Type","application/json").headers("Authorization","token 8f62fd0d5c5d5f43c22bf18e841d7117b3f20514").
		when().get(Resources.getunfixScreens(form_id));
		int status_code=res.getStatusCode();
		return status_code;
	}
	public static String getunfixScreens(int form_id)
	{
		String form_id1=String.valueOf(form_id);
		
		String s="api/v2/organization/6/form/"+form_id1+"/unfix-screens/";
		return s;
	}
	public static String editForms(String or_id,String form_id) {
		// TODO Auto-generated method stub
		String s="/api/v2/organization/"+or_id+"/forms/"+form_id+"/";
		System.out.println(s);
		return s;
	}
	public static int removeCustomScreens(int custom_screen_mapping_id,String form_id)
	{
		//RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
		headers("Content-Type","application/json").headers("Authorization","token 8f62fd0d5c5d5f43c22bf18e841d7117b3f20514").
		body(PayLoadData.removeexixtingscreen(String.valueOf(custom_screen_mapping_id))).
		when().put("/api/v2/organization/6/forms/"+form_id+"/");
		int status_code=res.getStatusCode();
		return status_code;
	}
	public static int DeleteWorkFlow(int form_id)
	{
		
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
		headers("Content-Type","application/json").headers("Authorization","token 8f62fd0d5c5d5f43c22bf18e841d7117b3f20514").
		when().delete(Resources.addScreens(form_id));
		int status=res.getStatusCode();
		return status;
	}
	public static int removeFormField(int custom_screen_id,int form_field_id)
	{
		String screenid=String.valueOf(custom_screen_id);

		String id = String.valueOf(form_field_id);
		//RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
		headers("Content-Type","application/json").headers("Authorization","token 8f62fd0d5c5d5f43c22bf18e841d7117b3f20514").
		body(PayLoadData.removeFormId(id))
		.when().put("/api/v2/organization/6/screens/"+screenid+"/");
		int code=res.getStatusCode();
		return code;
	}
	public static int removeWorkFlow(int form_id)
	{
		//RestAssured.baseURI="https://sandbox.veris.in";
		Response res=given().
		headers("Content-Type","application/json").headers("Authorization","token 8f62fd0d5c5d5f43c22bf18e841d7117b3f20514").
		body(PayLoadData.removeWorkflow(String.valueOf(form_id))).
		when().put(Resources.MapWorkflow());
		int status_code=res.getStatusCode();
		return status_code;
	}
}
