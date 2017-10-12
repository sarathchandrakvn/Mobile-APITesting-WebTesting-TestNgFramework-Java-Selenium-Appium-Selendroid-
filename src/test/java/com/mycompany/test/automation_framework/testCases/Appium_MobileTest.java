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

/**
 * @author Sarath
 * Email Sarath.qaengineer@gmail.com
 * The Below Automation Script runs on a Android Phone for testing Mobile WEB. 
 * Before running the test, Start appium server using -- appium -U Udid -p 4723
 * Connect the Android Device using Android Debugging Bridge ADB & USB
 */
public class Appium_MobileTest {

	private WebDriver driver;
	@BeforeTest
	public void appTest() throws MalformedURLException, InterruptedException
	{
	 
		 DesiredCapabilities caps = new DesiredCapabilities();
		 caps.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);
		 caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Device Name - Galaxy S6");
		 caps.setCapability(MobileCapabilityType.VERSION, "6.1");
		 caps.setCapability(MobileCapabilityType.UDID, "UDID of device");
		 caps.setCapability("--session-override", Boolean.TRUE);
		 URL url = new URL("http://0.0.0.0:4723/wd/hub/");
		 driver = new AndroidDriver(url,caps);
		 driver.get(Constants.url);
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
		driver.close();
	}
}
