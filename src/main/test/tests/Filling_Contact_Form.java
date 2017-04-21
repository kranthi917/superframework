package tests;

import java.io.IOException;

import org.testng.annotations.Test;

import base.BaseClass;
import extent.ExtentTestManager;
import pages.ContactForm;

/**
 * @author Rahul Rana
 * 12-Apr-2017
 * This page is used to write test scripts
 */
public class Filling_Contact_Form extends BaseClass {
	
	ContactForm form = new ContactForm();
	
	/**
	 * TC-001 - Verify Contact Form Functionality
	 * @throws IOException
	 */
	@Test(priority=1)
	public void fill_contact_Form() throws IOException {
		try
		{
			String testname = "fill_contact_Form";
			ExtentTestManager.startTest(testname + " - " + browserType, "Filling and submitting contact form")
			                 .assignCategory("Contact");
			
			form.clickOnContact(driver);
			form.getDataForContactForm("ContactForm");
			form.fillContactForm(driver);
			form.clickOnSubmit(driver);
		
		}
		catch(Throwable t)
		{
		System.out.println(t.getLocalizedMessage());
		Error e1 = new Error(t.getMessage()); 
		e1.setStackTrace(t.getStackTrace()); 
		throw e1;
		}
		
	}	
	
}
