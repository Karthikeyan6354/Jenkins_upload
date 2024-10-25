package testingReports.Jenkins_upload;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportDemo {
	
	ExtentSparkReporter reporter;
	ExtentReports extent;
	ExtentTest test;
	WebDriver driver;
	
	@BeforeTest
	public void config()
	{
		String path=System.getProperty("user.dir")+"\\reports\\index.html";
	    reporter=new ExtentSparkReporter(path);
	    reporter.config().setReportName("Demo Test results");
	    reporter.config().setDocumentTitle("Logi test results");
	    
	    extent=new ExtentReports();
	    extent.attachReporter(reporter);
	    extent.setSystemInfo("Tester", "Karthi");
	 }
	
	@Test
	public void login1()
	{
		test=extent.createTest("First Test");
		driver=new ChromeDriver();
		driver.get("https://www.saucedemo.com/");
		driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
		driver.findElement(By.xpath("//input[@id='login-button']")).click();
		String actualtitle="Swag Labs";
		String expectedtitle=driver.getTitle();
		//Assert.assertTrue(true);
		Assert.assertEquals(actualtitle, expectedtitle);
		System.out.println("This is method 1");
	}
	
	@Test
	public void login2()
	{
		test=extent.createTest("second Test");
		driver=new ChromeDriver();
		//driver.manage().timeouts().implicitlyWait(null)
		driver.get("https://www.saucedemo.com/");
		driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
		driver.findElement(By.xpath("//input[@id='login-button']")).click();
		String actualtitle="Swag Lab";
		String expectedtitle=driver.getTitle();
		//Assert.assertTrue(true);
		 TakesScreenshot ts=(TakesScreenshot) driver; 
		 File Sourcefile=ts.getScreenshotAs(OutputType.FILE);
		 File targetfile=new File (System.getProperty("user.dir")+"\\screenshot\\pages.png");
		 Sourcefile.renameTo(targetfile);
		Assert.assertEquals(actualtitle, expectedtitle);
		//Assert.assertTrue(false);
		System.out.println("This is method 2");
	}
	
	@AfterTest
	public void alltest()
	{
		extent.flush();
	}
	
	@AfterMethod
	public void getResult(ITestResult result) throws IOException {
		if (result.getStatus()==ITestResult.FAILURE)
		{
			test.log(Status.FAIL, result.getThrowable());
			
			/*
			 * TakesScreenshot ts=(TakesScreenshot) driver; File
			 * Sourcefile=ts.getScreenshotAs(OutputType.FILE);
			 * 
			 * File targetfile=new File
			 * (System.getProperty("user.dir")+"\\screenshot\\pages.png");
			 * Sourcefile.renameTo(targetfile);
			 */
            File source=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			
			String screenshotpath=System.getProperty("user.dir")+"/screenshot/"+System.currentTimeMillis()+".png";
			
			FileUtils.copyFile(source, new File(screenshotpath));
			
			test.fail(MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());
		}
	}
	
	

}
