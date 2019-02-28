package FormBuilderTest;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import org.testng.junit.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import Files.PayLoadData;
import Files.Resources;

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
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test;
	@BeforeTest
	public void startReport()
	{
		htmlReporter = new ExtentHtmlReporter(new File(System.getProperty("user.dir")+"/MyOwnReport.html"));

		// create ExtentReports and attach reporter(s)
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("Enviorment", "QA");
		extent.setSystemInfo("Username", "MAnsi Sahu");

		htmlReporter.config().setDocumentTitle(" Automation Testing Report");
		htmlReporter.config().setReportName("Form Builder Automation Testing Report");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
	}
	@BeforeMethod
	public void register(Method method)
	{
		String testName=method.getName();
		test=extent.createTest(testName);
	}

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
		Assert.assertEquals(wf_status, 201);
		if(wf_status == 201)
		{
			Assert.assertEquals(new_count, prev_count+1);;
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
		if(code == 201)
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
		Assert.assertEquals(code, 201);
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
	@Test(priority=15,groups="Screens",description="remove mapping of workflow and mapping",dependsOnMethods="MapWorkflow")
	public void removeVenueWFMapping()
	{
		int code=Resources.removeWorkFlow(Integer.parseInt(workflow_id));
		Assert.assertEquals(code, 200);
	}
	@Test(priority=16,groups="Screens",description="Deleting WorkFlow",dependsOnMethods="addWorflow")

	public void deleteWorkFlow()
	{
		int code=Resources.DeleteWorkFlow(Integer.parseInt(workflow_id));
		Assert.assertEquals(code, 200);
	}
	@AfterMethod 
	public void getResult(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED ", ExtentColor.RED));
            test.fail(result.getThrowable());
        }
        else if(result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" PASSED ", ExtentColor.GREEN));
        }
        else {
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" SKIPPED ", ExtentColor.ORANGE));
            test.skip(result.getThrowable());
        }
	}
	@AfterTest
	public void flushing()
	{
		extent.flush();
	}

}

