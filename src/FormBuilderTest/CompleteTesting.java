package FormBuilderTest;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.junit.*;
import Files.PayLoadData;
import Files.Resources;
import Modules.*;
import Modules.CustomException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class CompleteTesting {

	int Screens_result[];
	int Screens_add_result[];
	int getWorkflows[];
	int screen_workflow_mapping_id;
	int custom_screen_workflow_mapping_id;
	String workflow_id;
	int custom_screen_id;
	int field_id;
	int count=0;
	@Test(priority=1,description="Getting WorkFlows",groups="Workflow")
	public void getWorkflows()
	{
		getWorkflows=Resources.getForms();
		int status_code=getWorkflows[0];
		Assert.assertEquals(status_code, 200);
	}
	@Test(priority=2,description="Add Workflows",groups="Workflow")
	public void addWorflow()
	{
		int prev_count=getWorkflows[1];
		String[] add_WF_status=Resources.Createworkflow("Myworkflow", "Myworkflow");
		workflow_id=add_WF_status[0];
		int wf_status=Integer.parseInt(add_WF_status[1]);
		int result[]=Resources.getForms();
		int new_count=result[1];
		if(wf_status == 201)
		{
			Assert.assertEquals(new_count, prev_count+1);;
		}
		else
		{
			Assert.assertTrue(false);
		}
	}
	@Test(priority=3,description="Update Workflows",dependsOnMethods="addWorflow",groups="Workflow")
	public void UpdateWorkflow()
	{
		int edit_status=Resources.EditWorkFlow(workflow_id);
		System.out.println("edit status"+edit_status);
		Assert.assertEquals(edit_status, 200);
	}
	@Test(priority=4,dependsOnMethods="addWorflow",groups="Workflow",description="Mapping workflow with venue")
	public void MapWorkflow()
	{
		int map_code=Resources.MapWorkflows(Integer.parseInt(workflow_id));
		System.out.println("Map_Code "+map_code);
		Assert.assertEquals(map_code, 201);
	}
	@Test(priority=5,dependsOnMethods="addWorflow",description="Getting Existing screens of a workflow",groups="Workflow")
	public void getExistingScreen()
	{
		int get_screens_code=Resources.getUnfixScreens(Integer.parseInt(workflow_id));
		System.out.println(get_screens_code);
		Assert.assertEquals(get_screens_code, 200);
	}
	@Test(priority=6,dependsOnMethods="addWorflow",description="Mapping Existing screens with a workflow",groups="Workflow")
	public void addExistingScreens()
	{
		int existing_screen[]=Resources.AddExistingScreeens(Integer.parseInt(workflow_id));
		int screen_code=existing_screen[0];
		System.out.println(screen_code);
		screen_workflow_mapping_id=existing_screen[1];
		Assert.assertEquals(screen_code, 200);
	}
	@Test(priority=7,dependsOnMethods="addExistingScreens",description="removing mapping of Existing screens",groups="Workflow")
	public void removeExistingSCresn()
	{
		int remove_code=Resources.removeExistingScreens(screen_workflow_mapping_id,Integer.parseInt(workflow_id));
		Assert.assertEquals(remove_code, 200);
	}
	@Test(priority=8,groups="Screens",description="GetAll Screens")
	public void getScreens()
	{
		Screens_result=Resources.getScreens();
		int code=Screens_result[0];
		Assert.assertEquals(code, 200);
	}
	@Test(priority=9,groups="Screens",description="Adding Custom Screens")
	public void AddCustomScreens()
	{

		int prev_count=Screens_result[1];
		Screens_add_result=Resources.addCustomScreens();
		int code=Screens_add_result[0];
		int arr[]=Resources.getScreens();
		int next_count=arr[1];
		if(code == 200)
		{
			custom_screen_id=Screens_add_result[1];
			Assert.assertEquals(next_count, prev_count+1);
		}
		else
		{
			Assert.assertTrue(false);
		}
	}
	@Test(priority=10,groups="Screens",description="Updating Custom Screens(Adding form fileds in custom as screen)",dependsOnMethods="AddCustomScreens")
	public void updateCustomScreens()
	{
		int result[]=Resources.EditCustomScreen(custom_screen_id);
		int code=result[0];
		field_id=result[1];
		Assert.assertEquals(code, 200);
	}
	@Test(priority=11,groups="Screens",dependsOnMethods= {"addWorflow","AddCustomScreens"},description="Mapping Custom Screens with workflow")
	public void MapScreensWithWorkflows()
	{
		int result[]=Resources.MapCustomScreen(custom_screen_id,Integer.parseInt(workflow_id));
		int code=result[0];
		custom_screen_workflow_mapping_id=result[1];
		Assert.assertEquals(code, 200);
	}
	@Test(priority=12,groups="Screens",description="Delete form fields from utom Screens",dependsOnMethods="updateCustomScreens")
	public void deleteFormFields()
	{
		int code=Resources.removeFormField(custom_screen_id, field_id);
		Assert.assertEquals(code, 200);
	}
	@Test(priority=13,groups="Screens",description="Remove mapping of workflow and custom screen",dependsOnMethods="MapScreensWithWorkflows")
	public void removeMappingScreenWf()
	{
		int code=Resources.removeCustomScreens(custom_screen_workflow_mapping_id, workflow_id);
		Assert.assertEquals(code, 200);
	}
	
	@Test(priority=14,groups="Screens",description="deleting custom screen",dependsOnMethods="AddCustomScreens")
	public void deleteScreens()
	{
		int code=Resources.deleteScreen(custom_screen_id);
		Assert.assertEquals(code, 200);
	}
	@Test(priority=14,groups="Screens",description="remove mapping of workflow and mapping",dependsOnMethods="MapWorkflow")
	public void removeVenueWFMapping()
	{
		int code=Resources.removeWorkFlow(Integer.parseInt(workflow_id));
		Assert.assertEquals(code, 200);
	}
	@Test(priority=15,groups="Screens",description="Deleting WorkFlow",dependsOnMethods="addWorflow")
	
		public void deleteWorkFlow()
		{
			int code=Resources.DeleteWorkFlow(Integer.parseInt(workflow_id));
			Assert.assertEquals(code, 200);
		}

}

	