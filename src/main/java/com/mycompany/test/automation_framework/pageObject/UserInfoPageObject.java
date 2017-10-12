/**
 * 
 */
package com.mycompany.test.automation_framework.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Sarath
 * Email Sarath.qaengineer@gmail.com
 */
public class UserInfoPageObject {
	
	static private WebDriverWait wait;
	static public WebElement element;
	
	
		public static WebElement getEditFirstName(WebDriver driver)
		{
		     wait = new WebDriverWait(driver,60); // Implicit wait
		   	 element=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("Xpath-Expression")));
		   	 element.clear(); // clear the element if needed.
		   	 return element;
		}
		
		public static WebElement getEditLastName(WebDriver driver)
		{
		     wait = new WebDriverWait(driver,60); // Implicit wait
		   	 element=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("Xpath-Expression")));
		   	 element.clear(); // clear the element if needed.
		   	 return element;
		}
		
		public static WebElement getEditAddressField(WebDriver driver)
		{
		     wait = new WebDriverWait(driver,60); // Implicit wait
		   	 element=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("Xpath-Expression")));
		   	 element.clear(); // clear the element if needed.
		   	 return element;
		}
		
		public static WebElement getSubmitButton(WebDriver driver)
		{
		     wait = new WebDriverWait(driver,60); // Implicit wait
		   	 element=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("Xpath-Expression")));
		   	  return element;
		}
		
	
}
