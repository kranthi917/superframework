package extent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;

import base.BaseClass;

public class ExtentManager extends BaseClass {

	static ExtentReports extent;

	/**
	 * @author Rahul Rana
	 * 12-Apr-2017
	 * Creates report in date wise folders
	 */
	public synchronized static ExtentReports getReporter() {
		String directory = System.getProperty("user.dir") + "/ExtentReports/";
		String todayAsString = new SimpleDateFormat("dd-MM-yyyy/dd_MM_yyyy").format(new Date());
		String html = ".html";
		String Path = directory + todayAsString + html;
		if (extent == null) {
			extent = new ExtentReports(Path, false);
			extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
			extent.addSystemInfo("Selenium", "v2.53.1");
			extent.addSystemInfo("Environment", "QA");
			extent.addSystemInfo("User Name", "Rahul Rana");		
		}
		return extent;
	}

	/**
	 * @author Rahul Rana
	 * 12-Apr-2017
	 * Takes screenshot and embeds in the report and 
	 * also places in the same date folder on which it was run
	 */
	public static String takeScreenshot(WebDriver driver, String filename) throws Exception {
		filename = filename + ".png";
		String dir = System.getProperty("user.dir") + "/ExtentReports/";
		String todayAsString = new SimpleDateFormat("dd-MM-yyyy/ddMMyyyy").format(new Date());
		String directory = dir + todayAsString;
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(source, new File(directory + filename));
		String destination = directory + filename;
		return destination;
	}

}

