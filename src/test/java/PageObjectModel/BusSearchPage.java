package PageObjectModel;

import BaseClass.BaseClass;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BusSearchPage extends BaseClass {
    public BusSearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

     By from = By.id("fromCity");
     By fromLocation=By.xpath("//input[@placeholder='From']");
     By toLocation=By.xpath("//input[@placeholder='To']");
     By travelDateField = By.id("travelDate");
     By searchButton = By.xpath("//button[contains(text(),'Search')]");


        public void enterFromCity (String city){
            WebElement fromElement = driver.findElement(from);
            jsScrollToCentre(fromElement);
            fromElement.click();
            sleep(1000);
            WebElement fromCity= wait.until(ExpectedConditions.visibilityOfElementLocated(fromLocation));
            jsScrollToCentre(fromCity);
            fromCity.click();
            // Now enter the from city name
            fromCity.sendKeys(city);
            sleep(1000);
            fromCity.sendKeys(Keys.DOWN);  // optional
            fromCity.sendKeys(Keys.ENTER);// force selection
            System.out.println("From Location Entered");
        }


    public void enterToCity(String toCity)  {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement toInput = wait.until(ExpectedConditions.elementToBeClickable(toLocation));
            jsScrollToCentre(toInput);
            jsClick(toInput);

            // Now enter the To city name
            toInput.sendKeys(toCity);
            sleep(1500); // Allow time for suggestions

            // Navigate through suggestions and hit ENTER
            toInput.sendKeys(Keys.ARROW_DOWN);
            toInput.sendKeys(Keys.ENTER);
            System.out.println("✅ Entered To city: " + toCity);
        } catch (Exception e) {
            System.out.println("❌ Failed to enter To city: " + e.getMessage());
            throw e;
        }
    }



    public void closeCountryPopupIfVisible() {
        try {
            List<WebElement> countryPopup = driver.findElements(By.cssSelector("p.ctrySelectText"));
            if (!countryPopup.isEmpty()) {
                WebElement overlay = countryPopup.get(0);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", overlay);
                System.out.println("🌐 Country popup closed using JS");
            }
        } catch (Exception e) {
            System.out.println("ℹ️ No country popup found or already closed.");
        }
    }


    public void clickTravelDateField() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            //  Ensure popup is closed
            closeCountryPopupIfVisible();

           // WebElement travelDate = wait.until(ExpectedConditions.elementToBeClickable(By.id("travelDate")));
            WebElement travelDate = wait.until(ExpectedConditions.elementToBeClickable(travelDateField));
            jsScrollToCentre(travelDate);
            sleep(500); // optional pause
            jsClick(travelDate);
            System.out.println("✅ Travel Date field clicked");

        } catch (Exception e) {
            System.out.println("❌ Failed to click travel date field: " + e.getMessage());
            throw e;
        }
    }


        public void selectNextDay () {
            By nextDay = By.xpath("//div[contains(@class, 'DayPicker-Day--today')]/following-sibling::div[1]");
            click(waitForVisible(nextDay));
            System.out.println("✔ Selected next day date");
        }

     public void clickSearchButton () {
            click(waitForVisible(searchButton));
            System.out.println("✔ Search button clicked");
     }

}