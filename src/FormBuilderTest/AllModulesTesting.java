package FormBuilderTest;

import org.testng.annotations.Test;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import Files.PayLoadData;
import Files.Resources;
import Modules.*;
import Modules.CustomException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
public class AllModulesTesting {

	int prev_count;
	int next_count;
	int statuscode;
	int custom_screen_id;
	@Test(description="Adding workflow with invalid token")
	public void invalidToken()
	{		statuscode=Resources.invalidToken("Workflow5","Workflow5");
			System.out.println("token "+statuscode);
			try
			{
				if(statuscode != 401)
				{
					throw new CustomException("Invalid Token Test case is not returning the valid code");
				}
			}
			catch(CustomException e)
			{
				System.out.println(e.getMessage());
			}
	}
	@Test(description="invalid organization id")
	public void invalidOrganization()
	{
		statuscode =Resources.invalidOrganization("100","Workflow5","Workflow5");
		System.out.println("organization "+statuscode);
		
		try
		{
			if(statuscode != 403)
			{
				throw new CustomException("Invalid Organization Test case is not returning the valid code");
			}
		}
		catch(CustomException e)
		{
			System.out.println(e.getMessage());
		}

	}
	@Test(description="Adding WorkFlow")
	public void WorkFlowModule()
	{
		int result[]=Resources.getForms();
		int get_status=result[0];
		System.out.println(get_status);
		try {
		if(get_status != 200)
		{
			throw new CustomException("Unable To get Worklows , GetWorkFlow test case failed");
		}
		System.out.println("Get Workflow Test case passed");
		}
		catch (CustomException e) {
			System.out.println(e.getMessage());
		}
		prev_count=result[1];
		System.out.println("Previous count "+prev_count);
		String res[]=Resources.Createworkflow("wf6", "wf6");
		int status=Integer.parseInt(res[1]);
		int form_id=Integer.parseInt(res[0]);
		int result2[]=Resources.getForms();
		int next_count=result2[1];
		System.out.println("next count "+next_count);
		try
		{
			if(status!=201 || (prev_count != (next_count-1)))
			{
				throw new CustomException("Unable to Add Workflow,form_id not generated");
			}
			else
			{
				int edit_status=Resources.EditWorkFlow(String.valueOf(form_id));
				System.out.println("edit status"+edit_status);
				try
				{
				if(edit_status!=200)
				{
					throw new CustomException("Edit Workflow TestCase Failed");
				}
				else
				{
					System.out.println("Able to edit workflow");
					
				}
				}
				catch(CustomException e)
				{
					System.out.println(e.getMessage());
				}
				
					int map_code=Resources.MapWorkflows(form_id);
					System.out.println("Map_Code "+map_code);
					try
					{if(map_code !=201)
					{
						throw new CustomException("Unable to map Workflow with venue");
					}
					else
					{
						System.out.println("Able to map workflow");
					}
					}	
					catch(CustomException e)
					{
						System.out.println(e.getMessage());
					}
					int get_screens_code=Resources.getUnfixScreens(form_id);
					System.out.println(get_screens_code);
					try {
						if(get_screens_code != 200)
						{
							throw new CustomException("Get unfix Screens Test is failed");
						}
						System.out.println("Able to get Unfix Screens Passed");
					} catch (CustomException e) {
						// TODO: handle exception
						System.out.println(e.getMessage());
					}
					int existing_screen[]=Resources.AddExistingScreeens(form_id);
					int screen_code=existing_screen[0];
					System.out.println(screen_code);
					try
					{if(screen_code != 200)
					{
						throw new CustomException("Unable to Add Existing Screens");
					}
					else
					{
						int screen_workflow_mapping=existing_screen[1];
						int remove_code=Resources.removeExistingScreens(screen_workflow_mapping,form_id);
						try {
						if(remove_code != 200)
						{
							throw new CustomException("Remove Existing Screens Test case failed");
						}
						}
						catch (CustomException e) {
							// TODO: handle exception
							System.out.println(e.getMessage());
						}
					}
					}
					catch(CustomException e){
						System.out.println(e.getMessage());
					}
		}
		}
		catch(CustomException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	//@Parameters("form_id")
	

}
