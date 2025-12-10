package PageObjectModel;

import BaseClass.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;


public class HomePage extends BaseClass {
    public static WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    By closePopup = By.xpath("//span[@class='commonModal__close']");
    By busTab = By.xpath("//span[@class='headerIconTextAlignment chNavText darkGreyText'][normalize-space()='Buses']");
    By countryPopup = By.xpath("//p[contains(@class,'ctrySelectText')]");

    public void closePopupIfAvailable() {
        try {
            WebElement popup = waitForVisible(closePopup);
            click(popup);
            System.out.println("✔ Popup closed");
        } catch (Exception ignored) {}
    }


    // Clicks the bus ticket tab on homepage
     public void clickBusTab() {

        WebElement tab = waitForVisible(busTab);
        Assert.assertTrue(tab.isDisplayed(), "❌ Bus Tab Not Found!");

        click(tab);
        System.out.println("✔ Navigated to Buses tab");
        System.out.println("✔ Bus Tickets Tab Clicked Successfully");

    }

    // close the  country popup
    public void closeCountryPopupIfVisible() {
        try {
            WebElement popup = driver.findElement(countryPopup);
            if (popup.isDisplayed()) {
                jsClick(popup); // Clicking it closes the overlay
                System.out.println("✔ Country selection popup closed");
            }
        } catch (Exception ignored) {
        }
    }
}
