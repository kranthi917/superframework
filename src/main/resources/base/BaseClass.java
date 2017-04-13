package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import extent.ExtentManager;
import extent.ExtentTestManager;
import utilities.JSWaiter;
import utilities.XlsReader;


public class BaseClass {

	public static WebDriver driver;
	public static XlsReader data;
	public Properties prop;
	public static String browserType;
	ExtentReports extent;
	ExtentTest test;

	/**
	 * @author Rahul Rana
	 * 12-Apr-2017
	 * Takes browsername and environment as input to fire up desired browser and the specific environment
	 */
	@BeforeClass
	@Parameters({ "browserName", "environment" })
	public void setup(String browserName, String environment) throws InterruptedException, IOException {

		// Excel path configuration
		data = new XlsReader(System.getProperty("user.dir") + "/src/main/inputs/TestData.xlsx");

		// selecting browser based on parameter from TestNG.xml
		if (browserName.equalsIgnoreCase("firefox")) {
			try {
				System.out.println("----------FIREFOX Browser--------");
				// driver = new FirefoxDriver();
				FirefoxProfile profile = new FirefoxProfile();
				DesiredCapabilities caps = DesiredCapabilities.firefox();
				profile.setAcceptUntrustedCertificates(false);
				profile.setAssumeUntrustedCertificateIssuer(true);
				profile.setPreference("browser.download.folderList", 2);
				profile.setPreference("browser.helperApps.alwaysAsk.force", false);
				profile.setPreference("browser.download.manager.showWhenStarting", false);
				profile.setPreference("browser.download.dir", "C:\\Downloads");
				profile.setPreference("browser.download.downloadDir", "C:\\Downloads");
				profile.setPreference("browser.download.defaultFolder", "C:\\Downloads");
				profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
						"text/anytext ,text/plain,text/html,application/plain");
				caps = DesiredCapabilities.firefox();
				browserType = caps.getBrowserName();
				caps.setCapability(FirefoxDriver.PROFILE, profile);
				driver = new FirefoxDriver(caps);
				//Send driver object to JSWaiter Class
		        JSWaiter.setDriver(driver);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		
		else if (browserName.equalsIgnoreCase("chrome")) {
			// work with chrome
			try {
				System.out.println("----------CHROME Browser--------");
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/DriverFiles/chromedriver.exe");
				DesiredCapabilities caps = DesiredCapabilities.chrome();
				browserType = caps.getBrowserName();
				driver = new ChromeDriver();
				//Send driver object to JSWaiter Class
		        JSWaiter.setDriver(driver);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (browserName.equalsIgnoreCase("opera")) {
			// work with opera
			System.setProperty("webdriver.opera.driver", System.getProperty("user.dir") + "/DriverFiles/operadriver.exe");
			DesiredCapabilities caps = DesiredCapabilities.operaBlink();
			browserType = caps.getBrowserName();
			driver = new OperaDriver();
			//Send driver object to JSWaiter Class
	        JSWaiter.setDriver(driver);
		}

		else if (browserName.equalsIgnoreCase("ie")) {
			// work with Internet explorer
			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir") + "/DriverFiles/IEDriverServer.exe");
			try {
				System.out.println("----------IE Browser--------");
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "/DriverFiles/IEDriverServer.exe");
				//driver = new InternetExplorerDriver();
				DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
				browserType = caps.getBrowserName();
				caps.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
				caps.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
				caps.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
				caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				driver = new InternetExplorerDriver(caps);
				//Send driver object to JSWaiter Class
		        JSWaiter.setDriver(driver);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if (browserName.equals("Safari")) {
			try {
				System.out.println("----------SAFARI Browser--------");
				// driver = new SafariDriver();			
				// Pass the IP address of the Mac system on which safari is running
				// 5555 is the port no of the node		 
				URL url = new URL("http://172.27.106.11:5555/wd/hub");
				DesiredCapabilities caps = DesiredCapabilities.safari();
				browserType = caps.getBrowserName();
				caps.setBrowserName("safari");
				driver = new RemoteWebDriver(url, caps);
				//Send driver object to JSWaiter Class
		        JSWaiter.setDriver(driver);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(600, TimeUnit.SECONDS);	
		

		prop = new Properties();
		// takes Environment path
		FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/src/main/inputs/Environment.properties");

		prop.load(file);
		
		/**
		 * @author Rahul Rana
		 * 12-Apr-2017
		 * Fire up a specific environment provided from testng.xml
		 */
		String qaurl = prop.getProperty("ENV_QA");
		String stageurl = prop.getProperty("ENV_STAGING");
		String productionurl = prop.getProperty("ENV_PRODUCTION");

		if (environment.equalsIgnoreCase("qa")) {
			driver.get(qaurl);
		}
		else if (environment.equalsIgnoreCase("staging")) {
			driver.get(stageurl);
		}
		else if (environment.equalsIgnoreCase("production")) {
			driver.get(productionurl);
		}
		Thread.sleep(5000);
	}	
	
	/**
	 * @author Rahul Rana
	 * 12-Apr-2017
	 * Creates a single extent report for the entire day and appends multiple test results for multiple runs in a single report
	 */
	@BeforeSuite
	public void startReport(){
		extent = ExtentManager.getReporter();
	}
	
	/**
	 * @author Rahul Rana
	 * 12-Apr-2017
	 * Runs after every test and writes test result in extent report
	 */
	@AfterMethod
	protected void testResult(ITestResult result) throws Exception {
		
		if(result.getStatus() == ITestResult.FAILURE) {
			String path = ExtentManager.takeScreenshot(driver, result.getName().concat(randomString(10)));
			String imagePath = ExtentTestManager.getTest().addScreenCapture(path);
			ExtentTestManager.getTest().log(LogStatus.INFO, result.getThrowable());
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed", imagePath);
		}
		
		else if (result.getStatus() == ITestResult.SUCCESS) {
			ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
		}	
		
		else {
			String path = ExtentManager.takeScreenshot(driver, result.getName().concat(randomString(10)));
			String imagePath = ExtentTestManager.getTest().addScreenCapture(path);
			ExtentTestManager.getTest().log(LogStatus.INFO, result.getThrowable());
			ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped", imagePath);
		}
		
		if(driver != null)
		ExtentManager.getReporter().endTest(ExtentTestManager.getTest());        
        ExtentManager.getReporter().flush();
		driver.quit();
		
	}
	
	public static String randomString(int length) {
		final String data = "abcdefghijklmnopqrtuvwxyz";
		Random random = new Random();
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++)
			sb.append(data.charAt(random.nextInt(data.length())));
		return sb.toString();
	}

}
