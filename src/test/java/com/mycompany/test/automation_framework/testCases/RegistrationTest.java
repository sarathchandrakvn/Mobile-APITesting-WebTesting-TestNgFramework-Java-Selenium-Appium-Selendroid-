/**
 * 
 */
package com.mycompany.test.automation_framework.testCases;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.InfluxDB.ConsistencyLevel;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.json.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mycompany.test.automation_framework.pageObject.Service_RegistrationPageObject;
import com.mycompany.test.automation_framework.pageObject.UserInfoPageObject;
import com.mycompany.test.automation_framework.utils.Constants;

import io.selendroid.standalone.SelendroidLauncher;

/**
 * @author Sarath
 * Email Sarath.qaengineer@gmail.com
 */
public class RegistrationTest {
	
	private String var1,var2;
	private String filename;
	private ClassLoader loader;
	private InputStream resourceStream;
	private Properties props;
	private WebDriver driver;
	private static Logger log;
	private SelendroidLauncher selendroidServer = null;
	 InfluxDB influxDB ;
	 String dbName ; 
	 BatchPoints batchPoints;
	 Point point1 ;
	 long startTime;
	 long endTime;
	 long totalTime;
	 WebDriverWait wait;
	  static JavascriptExecutor js;
	  private   String env;
	  private   WebElement element;
	  private String bodyText;
	  static JSONObject user_info;
	 
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
			log.info(props.getProperty("key"));
			env = props.getProperty("env");
			log = Logger.getLogger(RegistrationTest.class);			
			// Make connection to InfluxDB to Push test Results
			influxDB = InfluxDBFactory.connect("Influx DB Url"); 
		    dbName = "DBName";
		    batchPoints = BatchPoints
		    .database(dbName)
		    .retentionPolicy("autogen")
		    .consistency(ConsistencyLevel.ALL)
		    .build();
		    wait = new WebDriverWait(driver, 10);
		    user_info = new JSONObject();
		   //Launch Driver
			driver =WebDriverFactory.getBrowser("chrome");
		  	js = (JavascriptExecutor) driver;		
	}
	
	@Test
	  @Parameters({ "param1", "param2" }) // Pass test params throught testNG Xml file.
	public void testRegistration(String param1, String param2) throws InterruptedException
	{	
		JSONObject userobj = xmltoJSONCoverter();
		driver.get(Constants.url);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		Service_RegistrationPageObject.getFirstName(driver).sendKeys(userobj.get("Firstname").toString());
		Service_RegistrationPageObject.getLastName(driver).clear();
		Service_RegistrationPageObject.getLastName(driver).sendKeys((userobj.get("Lastname").toString()));
		Service_RegistrationPageObject.getAddressField(driver).clear();
		Service_RegistrationPageObject.getAddressField(driver).sendKeys(props.getProperty("key"));
		Service_RegistrationPageObject.getEmailField(driver).clear();
		Service_RegistrationPageObject.getEmailField(driver).sendKeys(param1);
		Service_RegistrationPageObject.getPhoneField(driver).clear();
		Service_RegistrationPageObject.getPhoneField(driver).sendKeys(param2);
		Service_RegistrationPageObject.getPhoneField(driver).click();
		
		try	
	 		{
				js.executeScript("arguments[0].scrollIntoView(true);",Service_RegistrationPageObject.getSubmitButton(driver));
				Thread.sleep(1000);
				Service_RegistrationPageObject.getSubmitButton(driver).click();
	 		}	
	 	catch(Exception e)
			{
	 		 	log.info(e);
			}
 
		startTime = System.currentTimeMillis();
		Thread.sleep(5000);
		driver.switchTo().alert().dismiss();
		//Accept Second alert
		driver.switchTo().alert().accept();
		log.info(driver.switchTo().alert().getText());
        // Wait for Alert to be present
        Alert myAlert = wait.until(ExpectedConditions.alertIsPresent());
        myAlert.accept();
        log.info("Alert Accepted");
		endTime = System.currentTimeMillis();
		totalTime=endTime-startTime;
		log.info("TotalTime=" +totalTime);
		
		 driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		 Thread.sleep(50000);
		 
		if ((env.equalsIgnoreCase("Somevalue"))) 
		{
	      	Thread.sleep(1000);
	      	element = wait.until(ExpectedConditions.elementToBeClickable(By.id("")));
	      	Thread.sleep(2000);
	      	driver.findElement(By.id("")).click();
	      	Thread.sleep(2000);
	      	element = wait.until(ExpectedConditions.elementToBeClickable(By.id("")));
	      	Thread.sleep(2000);
	      	driver.findElement(By.id("")).click();
	      	Thread.sleep(2000);
	        bodyText = driver.findElement(By.tagName("body")).getText();
	      	try 
	      	{
	      			Assert.assertEquals(true, bodyText.contains("Something"));
	      			log.info("Registration successful \n" +bodyText);
	      	} 
	      	catch (AssertionError e) 
	      	{
	      		log.error(e);
	      	}
	    }
	
	
		else 
			{
			element = wait.until(ExpectedConditions.elementToBeClickable(By.id(" ")));
			Thread.sleep(2000);
			element.click();
			element = wait.until(ExpectedConditions.elementToBeClickable(By.id("")));
			Thread.sleep(2000);
			driver.findElement(By.id("")).click();
			Thread.sleep(2000);
			element = wait.until(ExpectedConditions.elementToBeClickable(By.id("")));
			Thread.sleep(2000);
			driver.findElement(By.id("")).click();
			Thread.sleep(2000);
			element = wait.until(ExpectedConditions.elementToBeClickable(By.id("")));
			Thread.sleep(1000);
		    bodyText = driver.findElement(By.id("")).getText();
		    log.info(bodyText);
		    if( (bodyText.contains("something")==Boolean.TRUE) && (bodyText.contains("")==Boolean.TRUE) )
		    	log.info("Registration successful \n" +bodyText);
		 }
	
		driver.navigate().to(Constants.url);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(" ")));
		js.executeScript("arguments[0].scrollIntoView(true);",element);
		element.click();
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		Thread.sleep(1000);
		element = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("")));
		log.info(driver.findElement(By.partialLinkText(" ")).getAttribute("href"));//click();
		log.info("Firstname saved in =	" +UserInfoPageObject.getEditFirstName(driver).getAttribute("value"));
     	log.info("Lastname Saved ="  +UserInfoPageObject.getEditLastName(driver).getAttribute("value"));
     	log.info("Addresslane2 Saved =	"  +UserInfoPageObject.getEditAddressField(driver).getAttribute("value"));
      	 
     	try
 		{		
    		if(UserInfoPageObject.getEditFirstName(driver).getAttribute("value").length()!=0)
    			{
    				UserInfoPageObject.getEditFirstName(driver).click();
    				js.executeScript("arguments[0].setAttribute('style', arguments[1]);",UserInfoPageObject.getEditFirstName(driver),"color: blue; border: 2px solid blue;");    	
    				Assert.assertEquals(UserInfoPageObject.getEditFirstName(driver).getAttribute("value").equalsIgnoreCase(Constants.fn),true);
    				Thread.sleep(500);
    				js.executeScript("arguments[0].setAttribute('style', arguments[1]);",UserInfoPageObject.getEditFirstName(driver),"color: green; border: 2px solid green;");
    				Assert.assertEquals(UserInfoPageObject.getEditFirstName(driver).getAttribute("value").length()>0,true);
    				log.info("First Name is not Null");
     			}    
    		 
    		if(UserInfoPageObject.getEditLastName(driver).getAttribute("value").length()!=0)
			{
				UserInfoPageObject.getEditLastName(driver).click();
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);",UserInfoPageObject.getEditLastName(driver),"color: blue; border: 2px solid blue;");    	
				Assert.assertEquals(UserInfoPageObject.getEditLastName(driver).getAttribute("value").equalsIgnoreCase(Constants.fn),true);
				Thread.sleep(500);
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);",UserInfoPageObject.getEditLastName(driver),"color: green; border: 2px solid green;");
				Assert.assertEquals(UserInfoPageObject.getEditLastName(driver).getAttribute("value").length()>0,true);
				log.info("Last Name is not Null");
			} 
    		
    		if(UserInfoPageObject.getEditAddressField(driver).getAttribute("value").length()!=0)
			{
				UserInfoPageObject.getEditAddressField(driver).click();
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);",UserInfoPageObject.getEditAddressField(driver),"color: blue; border: 2px solid blue;");    	
				Assert.assertEquals(UserInfoPageObject.getEditAddressField(driver).getAttribute("value").equalsIgnoreCase(Constants.fn),true);
				Thread.sleep(500);
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);",UserInfoPageObject.getEditAddressField(driver),"color: green; border: 2px solid green;");
				Assert.assertEquals(UserInfoPageObject.getEditAddressField(driver).getAttribute("value").length()>0,true);
				log.info("Last Name is not Null");
			} 
 		}
     		catch(AssertionError e)
     		{
     			log.error(e);
     		}
 
		
		 try {
	    		wait = new WebDriverWait(driver, 20);
	    		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("")));
	    		if (driver.findElement(By.xpath("")).isDisplayed())
	    			driver.findElement(By.xpath("")).click();
	    		Thread.sleep(2000);
	      	} 
	    	catch (Exception e) 
	    	{
	    	 log.error(e);
	     	} 	
		 
 	}
	
	
	
	public static JSONObject xmltoJSONCoverter()
	{
		 try {	
			 
	         File inputFile = new File("pathtofile");
	         DocumentBuilderFactory dbFactory 
	            = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         System.out.println("Root element :" 
	            + doc.getDocumentElement().getNodeName());
	         NodeList nList = doc.getElementsByTagName("User");
	         System.out.println("----------------------------");
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            System.out.println("\nCurrent Element :" 
	               + nNode.getNodeName());
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	               Element eElement = (Element) nNode;
	               System.out.println(" Firstname : " 
	                  + eElement.getAttribute("Firstname"));
	               System.out.println("last Name : " 
	                  + eElement
	                  .getElementsByTagName("lastname")
	                  .item(0)
	                  .getTextContent());
	               System.out.println("Address : " 
	               + eElement
	                  .getElementsByTagName("address")
	                  .item(0)
	                  .getTextContent());
	               
	               user_info.put("FirstName", eElement.getAttribute("Firstname"));
	               user_info.put("LastName", eElement.getAttribute("lastname"));
	               user_info.put("Address", eElement.getAttribute("address"));
 
	            }
	         }
	      } 
 
		 		catch (Exception e) 
		 			{
		 					e.printStackTrace();
		 			}
		 	return user_info;
	   	}
	
 
 
	
	
	@AfterClass
	public void tearDown()
	{	
		driver.close();
		log.info("Exiting the WeBdriver");
		
		//Write Results to InfluxDB
			 try
			 	{
 				 	
 				 	point1 = Point.measurement("cdi")
				 			.addField("responseTime", totalTime)
 				 			.build();
				 	batchPoints.point(point1);
				   	influxDB.write(batchPoints);
				  	influxDB.close();
				 	log.info("Write to influx success \n");

			 	}
			 catch(Exception e)
			 {
				 log.error("Exception Occured "+e);
			 }
		 }
	}

 