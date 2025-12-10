package PageObjectModel;

import BaseClass.BaseClass;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PassengerDetailsPage extends BaseClass {
    public PassengerDetailsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

     By TravellerDetailsPage = By.xpath("//span[contains(text(),'Traveller Details')]");
     By passengerName = By.xpath("//input[@id='fname']");
     By passengerAge = By.xpath("//input[@id='age']");
     By mobileNumber = By.xpath("//input[@id='mobileNumber']");
     By passengerEmail = By.xpath("//input[@id='contactEmail']");

    // Gender radio buttons
     By maleGender = By.xpath("//div[contains(@class,'maleTab')][contains(normalize-space(),'Male')]");
     By femaleGender = By.xpath("//div[contains(@class,'femaleTab')][contains(normalize-space(),'Female')]");

     By billingCheckbox = By.xpath("//span[@class='checkboxWpr']//b");
     By insuranceNoOption = By.xpath("//span[normalize-space()=\"No,I don't need it\"]/preceding-sibling::span");

     By continueBtn = By.xpath("//span[normalize-space()='Continue']");


    // Page Validations
    public void verifyPassengerDetailsPageDisplayed() {
        WebElement title = waitForVisible(TravellerDetailsPage);
        Assert.assertTrue("❌ Passenger Details Page NOT loaded!", title.isDisplayed());
        System.out.println("✔ Passenger Details Page Displayed");
    }

    // Passenger Input Fields
    public void enterPassengerName(String name) {
        WebElement field = waitForVisible(passengerName);
        jsScroll(field);
        type(field, name);
        System.out.println("✔ Passenger Name Entered: " + name);
    }

    public void enterPassengerAge(String age) {
        WebElement field = waitForVisible(passengerAge);
        jsScroll(field);
        type(field, age);
        System.out.println("✔ Passenger Age Entered: " + age);
    }

    public void enterEmail(String email) {
        WebElement field = waitForVisible(passengerEmail);
        jsScroll(field);
        type(field, email);
        System.out.println("✔ Email Entered: " + email);
    }

    public void enterMobileNumber(String mobile) {
        WebElement field = waitForVisible(mobileNumber);
        jsScroll(field);
        type(field, mobile);
        System.out.println("✔ Email Entered: " + mobile);
    }

    // Gender Selection
    public void selectGender(String gender) {

        if (gender.equalsIgnoreCase("male")) {
            WebElement male = waitForVisible(maleGender);
            jsScroll(male);
            jsClick(male);
            System.out.println("✔ Gender Selected: Male");

        } else if (gender.equalsIgnoreCase("female")) {
            WebElement female = waitForVisible(femaleGender);
            jsScroll(female);
            jsClick(female);
            System.out.println("✔ Gender Selected: Female");

        } else {
            Assert.fail("❌ Invalid gender value: '" + gender + "' (Use Male / Female)");
        }
    }

    // Billing + Insurance + Coupon

    public void enableBillingCheckBox() {
        WebElement checkbox = waitForVisible(billingCheckbox);
        jsScroll(checkbox);
        jsClick(checkbox);
        System.out.println("✔ Billing Details Checkbox Selected");
    }


    public void selectNoInsuranceOption() {

        List<WebElement> options = driver.findElements(insuranceNoOption);

        if (options.size() > 0) {
            WebElement insurance = options.get(0);
            jsScroll(insurance);
            jsClick(insurance);
            System.out.println("✔ Selected: No, I don't need insurance");
        } else {
            System.out.println("⚠ No insurance selection available — Skipping step");
        }
    }


    //Automatically selects and applies the first available coupon
    public void applyCouponAutomatically() {

        // Locator to detect available coupons
        By couponList = By.xpath("//p[normalize-space()='Offers']/following::p[contains(text(),'MEGABUS') or contains(text(),'MMTEXTRA') or contains(text(),'BUSTRAINPASS')]");

        // Locator to verify success
        By appliedCouponMsg = By.xpath("//span[normalize-space()='APPLIED']");

        try {
            List<WebElement> coupons = driver.findElements(couponList);

            if (coupons.size() == 0) {
                System.out.println("⚠ No Coupons Available — Skipping Coupon Selection");
                return;
            }

            WebElement firstCoupon = coupons.get(0);
            jsScroll(firstCoupon);
            jsClick(firstCoupon);

            System.out.println("🔖 Attempting to apply: " + firstCoupon.getText());

            // Short wait before validation
            Thread.sleep(1500);

            // Verify if applied
            try {
                WebElement appliedMsg = waitForVisible(appliedCouponMsg);

                if (appliedMsg.isDisplayed()) {
                    System.out.println("🎉 Coupon Applied Successfully: " + firstCoupon.getText());
                } else {
                    System.out.println("⚠ Coupon clicked but no confirmation appeared.");
                }

            } catch (Exception e) {
                System.out.println("⚠ Coupon NOT confirmed — Proceeding without coupon.");
            }

        } catch (Exception e) {
            System.out.println("⚠ Unexpected issue applying coupon — Continuing without coupon.");
        }
    }

    // Continue to Payment Page
    public void clickContinueButton() {
        WebElement btn = waitForVisible(continueBtn);
        jsScroll(btn);
        jsClick(btn);
        System.out.println("➡ Proceeding to Payment Page...");
    }
}

