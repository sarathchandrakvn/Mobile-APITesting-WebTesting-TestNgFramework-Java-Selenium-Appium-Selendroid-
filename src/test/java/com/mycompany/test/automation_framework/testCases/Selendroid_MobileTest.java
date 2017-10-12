/**
 * 
 */
package com.mycompany.test.automation_framework.testCases;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mycompany.test.automation_framework.pageObject.UserInfoPageObject;
import com.mycompany.test.automation_framework.utils.Constants;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.selendroid.client.SelendroidDriver;
import io.selendroid.common.SelendroidCapabilities;
import io.selendroid.standalone.SelendroidConfiguration;
import io.selendroid.standalone.SelendroidLauncher;

/**
 * @author Sarath
 * Email Sarath.qaengineer@gmail.com
 * The Below Automation Script runs on a Android Phone for testing Mobile WEB or APP. 
 * Before running the test,  Connect the Android Device using Android Debugging Bridge ADB & USB
 *
 */
public class Selendroid_MobileTest {

	  private SelendroidLauncher selendroidServer;
	  private SelendroidConfiguration config;
	  private SelendroidDriver driver;
	  
	  @BeforeTest
	  public void testSel() throws Exception
	  {
		  config = new SelendroidConfiguration();
		  selendroidServer = new SelendroidLauncher(config);
		  selendroidServer.launchSelendroid();
		  DesiredCapabilities caps = SelendroidCapabilities.android();
		  caps.setCapability(SelendroidCapabilities.SERIAL, "UDID");
		  driver = new SelendroidDriver(caps);  
		  driver.get("https://www.google.com");
		  Thread.sleep(10000);
		  
	  }
	
	@Test
	public void testWebPageOnMobile()
	{
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS); 	// Wait for pageload
		UserInfoPageObject.getEditFirstName(driver).sendKeys("Firstname");
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		UserInfoPageObject.getEditLastName(driver).sendKeys("Firstname");
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		UserInfoPageObject.getSubmitButton(driver).click();
		

		
		/*
		 * 
		 * Write code to Click elements and do something
		 */
		
		try
		{
			Assert.assertTrue(UserInfoPageObject.getEditFirstName(driver).isDisplayed());
			// Assert....
			// ..... Assert
 		}
		catch(Exception  e)
		{
			
		}
		
	}
	
	@AfterTest
	public void tearDown()
	{	
		selendroidServer.stopSelendroid();
		driver.close();
	}
}
