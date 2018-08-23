package launchSite;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import launchPage.LaunchSite;
import launchPage.goToAccessories;
import launchPage.addToCart;
import launchPage.shippingDetails;
import launchPage.confirmTransaction;
import org.apache.log4j.Logger;
public class FirstPage {
	
		WebDriver driver;
		Logger log = Logger.getLogger("devpinoyLogger");
	
	@Before
	
	public void launchSite() {
		try {
		LaunchSite launch_Object= new LaunchSite(driver);
		driver  = launch_Object.setup();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		log.debug("Main info is" +driver);
		}
		catch(Exception e)
		{
			log.error("Website not loaded properly");
			throw(e);
		}
	}
	
	@Test
	
	public void shopping() {
		
		try {
			goToAccessories accessory_Object = new goToAccessories(driver);
			log.debug("Clicking on Accessories");
			accessory_Object.accessory_Click();
			}
		catch(Exception e) {
			log.error("Could not select accessories");
			}
		
		try {
			log.debug("Adding to Cart");
			addToCart cart_Object=new addToCart(driver);
			cart_Object.magic_Mouse_Click();
			cart_Object.add_To_Cart();
			cart_Object.checkOut();
			String itemName =  cart_Object.check_Order();
			log.debug("Asserting mouse added");
			Assert.assertEquals(itemName,"Magic Mouse");
			cart_Object.click_Continue();
			}
		catch(Exception e) {
			log.error("Could not add to Cart");
			}
		
		shippingDetails ship_Details_Object = new shippingDetails(driver);
		try {
			log.debug("Shipping Details added");
			ship_Details_Object.add_Details();
			}
		catch(Exception e){
			log.error("Shipping Details not added completely");
		}
		try {
			log.debug("Purchasing the product");
			ship_Details_Object.purchase_Click();
			}
		catch(Exception e){
			log.error("Could not click on purchase");
			}
		
		confirmTransaction confirm_Object = new confirmTransaction(driver);
		int qty = confirm_Object.confirm_Quantity();
		
		try {
			log.debug("Confirming the quantity");
			Assert.assertTrue(qty==1);
			}
		catch(Exception e){
			log.error("Mouse quantity not added");
		}
		
		try {
			log.debug("Confirming the message");
			String msg = confirm_Object.confirm_Transaction_Message();
			Assert.assertTrue(msg.contains("Thank you, your purchase is pending. You will be sent an email once the order clears."));
			}
		catch(Exception e){
			log.error("Transaction not complete");
			}
		
	}
	
	@After
		
		public void quitSite() {
		try {
			log.debug("Closing Browser");
			driver.close();
			}
		catch(Exception e) {
			log.error("Driver could not be closed properly");
			}
		
}
}

		
		