
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class Amazon_Automation   {

	public static void main(String[] args) {

		//initialize the report to the desired location
		ExtentReports report = new ExtentReports("C:\\AutomationReports\\Report.html",true);		
		
	   //Opening selenium webdriver to work in Firefox	
	   WebDriver driver = new FirefoxDriver();
	   driver.manage().window().maximize();
	   driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		   	
		   
		   	   
	   //Calling functions based on test cases
	   Test_Wrong_Login(driver, report);	
	   Test_Empty_Login(driver, report);
	   //Test_Correct_Login(driver, report); //Please fill in correct username and password in the method to execute this test

	   //close the webdriver
	   driver.close();	
	   
	   //write the report to the file
	   report.flush();
       }
	
	
	//Test for wrong login user and password
	public static void Test_Wrong_Login(WebDriver driver, ExtentReports report){				
				
			String userLoginUrl ="https://www.amazon.com/ap/signin";
			String userLoginErrorMessage = "To better protect your account, please re-enter your password and then enter the characters as they are shown in the image below.";
			ExtentTest test = report.startTest("Test_Wrong_Login","System should display an error message while  enering wrong user login and password info.");
				
			test.log(LogStatus.INFO,  "Start Amazon User Loging Start");
			
			Amazon_User_signin(driver, "testuser@email.com", "test123");
			
			test.log(LogStatus.INFO,  "Start Amazon User Login attempted");
			
		   //1 Check if the user is still on login page
		   Assert.assertTrue(driver.getCurrentUrl().equals(userLoginUrl) , "Test_Wrong_Login - Login page URL Mismatch");
		   test.log(LogStatus.PASS,  "Check 1: User still on login Page");
		   
		   //2 Check for error message
		   Assert.assertTrue(driver.findElement(By.className("a-list-item")).getText().equals(userLoginErrorMessage),"Test_Wrong_Login - Login page error message mismatch");
		   test.log(LogStatus.PASS,  "Check 2: Invalid login error message exists");
		   
		   System.out.println("Test for wrong login user and password Passes.");
		   
		   report.endTest(test);	
	}
	
	
	//Test for empty login user and password
	public static void Test_Empty_Login(WebDriver driver, ExtentReports report){
		
			String userLoginUrl ="https://www.amazon.com/ap/signin";
			String userLoginErrorMessage = "Missing e-mail or mobile phone number. Please correct and try again.";
			ExtentTest test = report.startTest("Test_Empty_Login", "System should display an error message while entering empty user login and password info.");
					
			test.log(LogStatus.INFO,  "Start Amazon User Loging Start");
			
			Amazon_User_signin(driver, "", "");
			
			test.log(LogStatus.INFO,  "Start Amazon User Login attempted");
		
		   //1 Check if the user is still on login page
		   Assert.assertTrue(driver.getCurrentUrl().equals(userLoginUrl) , "Test_Empty_Login - Login page URL Mismatch");
		   test.log(LogStatus.PASS,  "Check 1: User still on login Page");
		   
		   //2 Check for error message		  
		   Assert.assertTrue(driver.findElement(By.className("a-list-item")).getText().equals(userLoginErrorMessage),"Test_Empty_Login - Login page error message mismatch");
		   test.log(LogStatus.PASS,  "Check 2: Invalid login error message exists");
		   
		   System.out.println("Test for No login user and password Passes.");
		   
		
	}
	
	
	
	//Test for correct user and password
	public static void Test_Correct_Login(WebDriver driver, ExtentReports report) {
		
		String correctEmail = ""; // Set user's correct email for test
		String correctPassword = "";  // Set user's correct password for test	
		String homePage = "https://www.amazon.com/";
		ExtentTest test = report.startTest("Test_Empty_Login");
		
		test.log(LogStatus.INFO,  "Start Amazon User Loging Start");
		
		Amazon_User_signin(driver,correctEmail, correctPassword);
		
		test.log(LogStatus.INFO,  "Start Amazon User Login attempted");
	
		
		//1.Check if user is directed on homepage for successful login
		Assert.assertTrue(driver.getCurrentUrl().equals(homePage) , "Test_Correct_Userlogin - home page URL Mismatch");
		System.out.println("On successful login user was able to redirect");
		test.log(LogStatus.PASS,  "Check 1: User Redirected to home Page");
		
		//2.Check if user successfully loggedin
		Assert.assertTrue(driver.findElement(By.className("nav-line-1")).getText().contains("Hello"),"Test_Correct_Userlogin - Home page message mismatch");
		test.log(LogStatus.PASS,  "Check 2: 'Hello, Username' message exists on home page");
		
		System.out.println("Test for successful user login passes");
	}
	

	
	
	//Reusable method for Amazon Homepage login 
	public static void Amazon_User_signin(WebDriver driver, String email, String password)
	
	{
		driver.get("http://www.amazon.com");
		driver.findElement(By.id("nav-link-yourAccount")).click();
		
		WebElement apEmail = driver.findElement(By.id("ap_email"));
		apEmail.sendKeys(email);
		
		WebElement apPassword = driver.findElement(By.id("ap_password"));
		apPassword.sendKeys(password);
		
		driver.findElement(By.id("signInSubmit")).click();
	}
	
	
}
