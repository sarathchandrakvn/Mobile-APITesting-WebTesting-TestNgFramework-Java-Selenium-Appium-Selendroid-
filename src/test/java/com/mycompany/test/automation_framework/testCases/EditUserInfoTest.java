/**
 * 
 */
package com.mycompany.test.automation_framework.testCases;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.mycompany.test.automation_framework.pageObject.UserInfoPageObject;
import com.mycompany.test.automation_framework.utils.Constants;

/**
 * @author Sarath
 * Email Sarath.qaengineer@gmail.com
 */
public class EditUserInfoTest {
	
	private String var1,var2;
	private String filename;
	private ClassLoader loader;
	private InputStream resourceStream;
	private Properties props;
	private WebDriver driver;
	
	@BeforeClass
	public void init()
	{		
		
			// Pass Build Parameters through Maven
			// mvn clean test -Dtest=EditUserInfoTest var1='value1' var2='var2'
			var1 = System.getProperty("var1");
			var2 = System.getProperty("var2");
			filename = var1;
			loader = Thread.currentThread().getContextClassLoader();
			props = new Properties();
		    try {
		    		resourceStream = loader.getResourceAsStream(filename);
		    		props.load(resourceStream);
		    	} 
		    catch (Exception e) 
		    	{
		    		System.out.println(e);
		    	}      
			System.out.println(props.getProperty("key"));
		    System.setProperty("webdriver.chrome.driver","driver location");
		    driver = new ChromeDriver();
		    driver.manage().window().maximize();
		
	}
	
	@Test
	public void testEditUserInfo()
	{
		driver.get(Constants.url);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		UserInfoPageObject.getEditFirstName(driver).sendKeys(Constants.fn);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		UserInfoPageObject.getEditLastName(driver).clear();
		UserInfoPageObject.getEditLastName(driver).sendKeys(Constants.ln);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		UserInfoPageObject.getEditAddressField(driver).clear();
		UserInfoPageObject.getEditLastName(driver).sendKeys(props.getProperty("key"));
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		UserInfoPageObject.getSubmitButton(driver).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		try
		{
			// Assert.assert
		}
		catch(AssertionError e)
		{
			//handle assertion error here
		}
		
	}
	
	@AfterClass
	public void tearDown()
	{
		driver.close();
	}
}
