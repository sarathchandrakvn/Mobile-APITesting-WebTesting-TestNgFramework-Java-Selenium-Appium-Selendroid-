/**
 * 
 */
package com.mycompany.test.automation_framework.testCases;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

/**
 * @author Sarath
 * Email Sarath.qaengineer@gmail.com
 */
public class WebDriverFactory {

	private static Logger log = Logger.getLogger(WebDriverFactory.class);

	  private static WebDriver driver = null;

	  private static final String RESOURCE_PATH = "." + File.separator + "src" + File.separator + "test"
	      + File.separator + "resources" + File.separator;

	  private static final String OS_NAME = System.getProperty("os.name");

	  private WebDriverFactory() {}

	  public static WebDriver getBrowser(String browserType) {

	    browserType = browserType.trim().toLowerCase();
	    switch (browserType) {
	      case "chrome":
	        return createChromeBrowser();
	      case "ie":
	        return createIEBrowser();
	      case "opera":
	        return createOperaBrowser();
	      case "safari":
	        return createSafariBrowser();
	      case "android":
	        return createAndroidBrowser();
	      case "ios":
	        return createMobileSafariBrowser();
	      default:
	        return createFirefoxBrowser();
	    }

	  }

	  /**
	   * Create android web driver to run selenium test cases on an android phone. Prerequisites:
	   * running selendroid jar file.
	   * 
	   * @return WebDriver
	   */
	  private static WebDriver createAndroidBrowser() {

	    // Return existing created webdriver
	    if (driver != null) {
	      return driver;
	    }

	    // Check if port used by selendroid is in use or not
	    boolean isPortInUse = false;
	    try {
	      Socket s = new Socket(InetAddress.getByName("localhost"), 4444);
	      isPortInUse = true;
	      log.info("Server is listening on port 4444");
	      s.close();
	    } catch (IOException ex) {
	      ex.printStackTrace();
	      log.info("Selendroid is not running on port 4444");
	    }
	    // If port is not in use, run selendroid
	    if (!isPortInUse) {
	      runSelendroid();
	    }

	    DesiredCapabilities capabilities = new DesiredCapabilities();
	    // Name of mobile web browser to automate. Should be an empty string if automating an app.
	    capabilities.setCapability(CapabilityType.BROWSER_NAME, "Windows");

	    capabilities.setCapability("platformName", "Android");
	    capabilities.setCapability("deviceName", "Android");

	    // Mobile OS version
	    capabilities.setCapability("platformVersion", "4.4.4");

	    // device name – since this is an actual device name is found using ADB
	    capabilities.setCapability("udid", "");

	    // the absolute local path to the APK
	    // capabilities.setCapability("app", app.getAbsolutePath());

	    // Java package of the tested Android app
	    // capabilities.setCapability("appPackage", "com.example.android.contactmanager");

	    // activity name for the Android activity you want to run from your package. This need to be
	    // preceded by a . (example: .MainActivity)
	    // capabilities.setCapability("appActivity", ".ContactManager");

	    // constructor to initialize driver object
	    // SelendroidCapabilities capa =
	    // SelendroidCapabilities.device("io.selendroid.androiddriver:0.16.0");
	    // capa.setCapability("aut", "io.selendroid.androiddriver:0.16.0");
	    // capa.setCapability("browserName", "selendroid");
	    // capa.setEmulator(true);
	    // SelendroidCapabilities.emulator("emulator-5554");
	    // SelendroidCapabilities.android();

	    // try {
	    // driver = new SelendroidDriver(capa);
	    // } catch (Exception e1) {
	    // e1.printStackTrace();
	    // }
	    WebDriver driver = new RemoteWebDriver(DesiredCapabilities.android());

	    // For selenium server apk
	    // DesiredCapabilities browser = DesiredCapabilities.android();
	    // try {
	    // driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), browser);
	    // } catch (MalformedURLException e) {
	    // e.printStackTrace();
	    // }

	    // try {
	    // driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	    // } catch (MalformedURLException e) {
	    // e.printStackTrace();
	    // }
	    return driver;
	  }

	  private static WebDriver createSafariBrowser() {
	    if (OS_NAME.contains("Windows")) {
	      System.setProperty("webdriver.safari.driver", RESOURCE_PATH + "Safari.exe");
	      driver = new SafariDriver();
	    } else {
	      SafariOptions options = new SafariOptions();
	      options.setUseCleanSession(true); // if you wish safari to forget session everytime
	      driver = new SafariDriver(options);
	    }
	    return driver;
	  }

	  private static WebDriver createOperaBrowser() {
	    // System.setProperty("webdriver.opera.driver", RESOURCE_PATH + "opera.exe");
	    DesiredCapabilities capabilities = DesiredCapabilities.operaBlink();
	    capabilities.setCapability("opera.binary", RESOURCE_PATH + "opera.exe");
	    driver = new RemoteWebDriver(capabilities);
	    // driver = new OperaDriver(capabilities);
	    return driver;
	  }

	  private static WebDriver createFirefoxBrowser() {
	    driver = new FirefoxDriver();
	    return driver;
	  }

	  private static WebDriver createIEBrowser() {
	    System.setProperty("webdriver.ie.driver", RESOURCE_PATH + "IEDriverServer.exe");
	    DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
	    capabilities.setCapability(
	        InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	    WebDriver driver = new InternetExplorerDriver(capabilities);
	    return driver;
	  }

	  private static WebDriver createChromeBrowser() {
	    String path = RESOURCE_PATH + "chromedriver";
	    System.setProperty("webdriver.chrome.driver", path);
	    driver = new ChromeDriver();
	    return driver;
	  }

	  // For use with appium and safariLauncher
	  private static WebDriver createMobileSafariBrowser() {
	    DesiredCapabilities capabilities = new DesiredCapabilities();
	    capabilities.setCapability("platformName", "iOS");
	    capabilities.setCapability("browserName", "Safari");

	    capabilities.setCapability("deviceName", "iPhone");
	    capabilities.setCapability("udid", "6ee15d44b904a9f592e82a5c3af4ffc9407a51e1");
	    capabilities.setCapability("bundleId", "com.verizon.safariLauncher");
	    capabilities.setCapability("appium-version", "1.4.8");

	    // capabilities.setCapability("deviceName", " iPhone");
	    // capabilities.setCapability("udid", "BF06A79F5A3826390D40780EEC7B1C20FFB52B9F");
	    // capabilities.setCapability("bundleId", "com.verizon.safariLauncher");
	    // capabilities.setCapability("appium-version", "1.4.13");

	    // Use for ios simulator
	    // capabilities.setCapability("deviceName", "iPhone 5s");
	    // capabilities.setCapability("safariAllowPopups", "true");
	    // capabilities.setCapability("unexpectedAlertBehaviour", "accept");
	    // capabilities.setCapability("safariIgnoreFraudWarning", true);
	    try {
	      driver = new RemoteWebDriver(new URL("http://localhost:4723/wd/hub"), capabilities);
	    } catch (MalformedURLException e) {
	      e.printStackTrace();
	    }
	    return driver;
	  }

	  // For use with iwebdriver in simulator
	  // private static WebDriver createMobileSafariBrowser() {
	  // try {
	  // driver = new RemoteWebDriver(new URL("http://68.140.241.123:3001/wd/hub/"),
	  // DesiredCapabilities.iphone());
	  // // driver.get("http://68.140.241.123:3001/wd/hub/");
	  // driver.get("google.com");
	  // Thread.sleep(Constants.INTERVAL_ACTION * 10);
	  // } catch (Exception e) {
	  // e.printStackTrace();
	  // }
	  // return driver;
	  // }

	  /**
	   * Run selendroid in a separate thread
	   */
	  private static void runSelendroid() {

	    // Prepare script for running selendroid in a terminal
	    createScript();

	    // Open a terminal and run selendroid
	    Thread thread = new Thread(new Runnable() {
	      public void run() {
	        try {
	          if (OS_NAME.contains("Windows")) {
	            Runtime.getRuntime().exec("cmd.exe /c start" + RESOURCE_PATH + "script");
	          } else {
	            Runtime.getRuntime()
	                .exec("open -a /Applications/Utilities/Terminal.app/ " + RESOURCE_PATH + "script");
	          }
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	      }
	    });
	    thread.start();

	    // Wait for selendroid to complete starting
	    try {
	      Thread.sleep(16000);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }

	  }

	  /**
	   * Create a script for running selendroid in a terminal
	   */
	  private static void createScript() {
	    PrintWriter pw = null;
	    File file = null;
	    try {
	      if (OS_NAME.contains("Windows")) {
	        file = new File(RESOURCE_PATH + "script.bat");
	      } else {
	        file = new File(RESOURCE_PATH + "script");
	      }
	      // file.setExecutable(true, false);
	      FileWriter fw = new FileWriter(file, false);
	      pw = new PrintWriter(fw);
	      pw.println("cd " + new File(RESOURCE_PATH).getCanonicalPath());
	      pw.println("java -jar selendroid.jar");
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	      if (pw != null) {
	        pw.close();
	      }
	    }
	  }
	}
