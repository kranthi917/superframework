package pages;

import java.io.IOException;
import org.openqa.selenium.WebDriver;

import base.BaseClass;
import utilities.CommonMethod;

/**
 * @author Rahul Rana
 * 12-Apr-2017
 * This page is used to store page objects
 */
public class ContactForm extends BaseClass {

	public String cname;
	public String cemail;
	public String cmessage;

	// click on contact link of Home page
	public void clickOnContact(WebDriver driver) throws IOException, InterruptedException {
		CommonMethod.click(driver, "HOME_CONTACT_LINK");
		Thread.sleep(5000);
	}

	// getting contact data from Excel
	public void getDataForContactForm(String sheetName) {
		cname = data.getCellData(sheetName, "ContactName", 2);
		cemail = data.getCellData(sheetName, "ContactEmail", 2);
		cmessage = data.getCellData(sheetName, "ContactMessage", 2);
	}

	// filling contact form
	public void fillContactForm(WebDriver driver) throws IOException {
		CommonMethod.clear(driver, "CONTACT_NAME");
		CommonMethod.sendKeys(driver, "CONTACT_NAME", cname);

		CommonMethod.clear(driver, "CONTACT_EMAIL");
		CommonMethod.sendKeys(driver, "CONTACT_EMAIL", cemail);

		CommonMethod.clear(driver, "CONTACT_EMAIL_MESSAGE");
		CommonMethod.sendKeys(driver, "CONTACT_EMAIL_MESSAGE", cmessage);
	}

	// click on submit button
	public void clickOnSubmit(WebDriver driver) throws IOException {
		CommonMethod.click(driver, "CONTACT_SUBMIT");
	}

}
