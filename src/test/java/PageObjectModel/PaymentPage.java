package PageObjectModel;

import BaseClass.BaseClass;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class PaymentPage extends BaseClass {
    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    // Payment page verification text
     By paymentOptionsTitle = By.xpath("//h2[normalize-space()='Payment Options']");


    // Verify that the payment page is displayed
    public void verifyPaymentPageLoaded() {
        WebElement title = waitForVisible(paymentOptionsTitle);
        Assert.assertTrue("❌ Payment Page NOT Loaded!", title.isDisplayed());

        System.out.println("✔ Payment Page Displayed Successfully");
    }
    // Capture full-page screenshot for proof
    public void capturePaymentScreenshot(String screenshotName) {
        String path = takeScreenshot(screenshotName);
        System.out.println("📸 Payment Page Screenshot Saved At: " + path);
    }
}
