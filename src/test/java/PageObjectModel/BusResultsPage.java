package PageObjectModel;

import BaseClass.BaseClass;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;



public class BusResultsPage extends BaseClass {
    public BusResultsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    By availableBusList = By.xpath("//div[contains(@data-testid,'bus-card')]");
    By acFilter = By.xpath("//p[contains(@class,'FilterTabs_labelText')][normalize-space()='AC']");
    By nonAcFilter=By.xpath("//p[normalize-space()='Non-AC']");
    By sleeperOption = By.xpath("//p[normalize-space()='Sleeper']");
    By seaterOption=By.xpath("//p[normalize-space()='Seater']");
    By singleSeatOption = By.xpath("//input[@id='filter-CAhSAlgB']");
    By continueButton = By.xpath("//button[contains(text(),'Continue')]");

    By upperSleeperSingleBerthXpath=By.xpath("//div[contains(@class,'SeatMapContainer_berthHeader') and contains(.,'UPPER')]" +
            "  /following-sibling::div" +
            "  //div[contains(@style,'grid-area') and contains(@style,'/ 4')]" +
            "  //img[@alt='HORIZONTAL_SLEEPER' and contains(@src, 'Sleeper_Available')]");

    By lowerSleeperSingleBerthXpath=By.xpath("//div[contains(@class,'SeatMapContainer_berthHeader') and contains(.,'LOWER')]" +
            "  /following-sibling::div" +
            "  //div[contains(@style,'grid-area') and contains(@style,'/ 4')]" +
            "  //img[@alt='HORIZONTAL_SLEEPER' and contains(@src, 'Sleeper_Available')]");

    By upperSleeperCombinedBerthXpath=By.xpath("//div[contains(@class, 'SeatMapContainer_berthHeader') and contains(., 'UPPER')]" +
            "/following::div[contains(@style, 'grid-area') and (contains(@style, '/ 1') or contains(@style, '/ 2'))]" +
            "//img[@alt='HORIZONTAL_SLEEPER' and contains(@src, 'Sleeper_Available')]");

    By lowerSleeperCombinedBerthXpath=By.xpath("//div[contains(@class,'SeatMapContainer_berthHeader') and contains(.,'LOWER')]" +
            "  /following-sibling::div" +
            "  //div[contains(@style,'grid-area') and (contains(@style,'/ 1') or contains(@style,'/ 2'))]" +
            "  //img[@alt='HORIZONTAL_SLEEPER' and contains(@src,'Sleeper_Available')]");
    By seaterSeatXpath = By.xpath(
            "//img[@alt='SEATER' and contains(@src, 'Seater_Available')]");
    By selectedSeatInfoXpath = By.xpath(
            "//div[normalize-space()='Selected Seats']" +
                    "/following-sibling::div//div[contains(@class,'PickUpDropSelection_calloutHeight')]/span"
    );

    //--Apply Filters
    public void applyACFilter() {
        click(waitForVisible(acFilter));
        System.out.println("✔ AC filter applied");
    }

    public void applyNonACFilter() {
        click(waitForVisible(nonAcFilter)); // nonAcFilter is a locator you must define
        System.out.println("✔ Non AC filter applied");

    }

    public void selectSleeperOptionIfAvailable() {
        try {
            click(waitForVisible(sleeperOption));
            System.out.println("✔ Sleeper filter selected");
        } catch (Exception e) {
            System.out.println("⚠ Sleeper filter not available — continuing without selecting Sleeper Filter");
        }
    }
    public void selectSeaterOptionIfAvailable() {
        try {
            click(waitForVisible(seaterOption)); // seaterOption is a locator you must define
            System.out.println("✔ Seater filter selected");
        } catch (Exception e) {
            System.out.println("⚠ Seater filter not available — continuing without selecting Seater Filter");
        }
    }

    public void selectSingleSeatOption() {
        WebElement singleSeatBoxElement=waitForVisible(singleSeatOption);
        jsScrollToCentre(singleSeatBoxElement);
        click(singleSeatBoxElement);
        System.out.println("✔ Single seat option selected");
    }


    public void selectPickupPoint(String pickupPoint) {
        System.out.println("🔍 Looking for pickup point: " + pickupPoint);
        try {
            // Wait for the pickup point to appear
            WebElement pickUpBox=(waitForVisible(By.xpath("//span[contains(normalize-space(),'Pick up point')] /following::input[contains(@class,'Checkbox_searchInputArea')][preceding::span[contains(normalize-space(),'Pick up point')] and following::span[contains(normalize-space(),'Pick up time')]]")));
            jsScrollToCentre(pickUpBox);
            System.out.println("Pickup Box Located");
            jsClick(pickUpBox);
            pickUpBox.sendKeys(pickupPoint);
            sleep(300);
            System.out.println("pickup entered in box");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement pickup=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(text(),'" + pickupPoint + "')]")));

            // Scroll it into view (important for visibility)
            jsScrollToCentre(pickup);
            sleep(500); // slight pause after scroll

            // Click the pickup point
            jsClick(pickup);
            System.out.println("✔ Pickup point selected: " + pickupPoint);
        } catch (Exception e) {
            System.out.println("❌ Could not find pickup point: " + pickupPoint);
            throw e;
        }
    }

    public void selectDropPoint(String dropPoint) {
        System.out.println("✔ Drop point selected: " + dropPoint);
        try {
            // Wait for the Drop point to appear
            WebElement dropPointBox=(waitForVisible(By.xpath("//span[contains(normalize-space(),'Drop point')] /following::input[contains(@class,'Checkbox_searchInputArea')][preceding::span[contains(normalize-space(),'Drop point')] and following::span[contains(normalize-space(),'Drop time')]]")));
            jsScrollToCentre(dropPointBox);
            //jsScroll(dropPointBox);
            System.out.println("dropPoint Box Located");
            jsClick(dropPointBox);
            dropPointBox.sendKeys(dropPoint);
            sleep(500);
            System.out.println("Drop Point entered in box");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement drop=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(text(),'" + dropPoint + "')]")));
            jsScrollToCentre(drop);
            sleep(500); // slight pause after scroll

            // Click the pickup point
            jsClick(drop);
            System.out.println("✔ Drop point selected: " + dropPoint);
        } catch (Exception e) {
            System.out.println("❌ Could not find drop point: " + dropPoint);
            throw e;
        }
    }

    /** Select Pickup Time */
    public void selectPickupTime(String pickupTime) {
        WebElement selectPickupTimeElement= waitForVisible(By.xpath("//span[contains(text(), 'Pick up time')]//ancestor::div[contains(@class,'HideShowSection_borderBottom__O_4sX')]//p[contains(text(),'" + pickupTime + "')]"));
                                                     //span[contains(text(), 'Pick up time')]//ancestor::div[contains(@class,'HideShowSection_borderBottom__O_4sX')]//p[contains(text(),'12 PM - 6 PM')]
        jsScrollToCentre(selectPickupTimeElement);
        sleep(300);
        jsClick(selectPickupTimeElement);
        sleep(300);
        System.out.println("✔ Pickup Time Selected: " + pickupTime);
    }

    // Select Drop Time
    public void selectDropTime(String dropTime) {
        ////span[contains(text(),'Drop time')]/following::p[contains(text(),'12 AM - 6 AM')][1]
        WebElement selectDropTimeElement=waitForVisible(By.xpath("//span[contains(text(),'Drop time')]/following::p[contains(text(),'"+ dropTime +"')][1]"));
        jsScrollToCentre(selectDropTimeElement);
        sleep(300);
        jsClick(selectDropTimeElement);
        System.out.println("✔ Drop Time Selected: " + dropTime);
    }


    // check bus availability
    public void verifyBusResultsAvailable() {
        List<WebElement> buses = driver.findElements(availableBusList);

        Assert.assertTrue("❌ No buses found! Cannot continue test.",
                buses.size() > 0);

        System.out.println("✔ Buses found: " + buses.size());
    }

    List<Integer> allPrices = new ArrayList<>();
    List<String> allOperators = new ArrayList<>();
    int lowestPriceIndex = -1;

    By priceLocator = By.xpath("//p[@data-testid='fare' and contains(@class,'BusCard_priceText')]");
    By operatorLocator = By.xpath("//p[@data-testid='operator-name']");




    public void printAllBusPricesWithOperators() {
        List<WebElement> priceElements = driver.findElements(priceLocator);
        List<WebElement> operatorElements = driver.findElements(operatorLocator);

        allPrices.clear();
        allOperators.clear();

        int validCount = 0;

        System.out.println("🚌 Total Bus Cards Found: " + Math.min(priceElements.size(), operatorElements.size()));
        System.out.println("\n===== 💰 Bus Prices with Operators =====");

        for (int i = 0; i < Math.min(priceElements.size(), operatorElements.size()); i++) {
            try {
                String fullPriceText = priceElements.get(i).getText().trim();
                String operator = operatorElements.get(i).getText().trim();

                // Extract just the first price match that looks like ₹1234
                if (fullPriceText.contains("₹")) {
                    String[] split = fullPriceText.split("₹");
                    if (split.length > 1) {
                        String priceNum = split[1].replaceAll("[^0-9]", "");
                        int price = Integer.parseInt(priceNum);

                        allPrices.add(price);
                        allOperators.add(operator);
                        validCount++;

                        System.out.println(validCount + ". " + operator + " → ₹" + price);
                    } else {
                        System.out.println("⚠ No valid price in: " + fullPriceText);
                    }
                } else {
                    System.out.println("⚠ Skipped due to missing ₹ in price: " + fullPriceText);
                }
            } catch (Exception e) {
                System.out.println("⚠ Error reading price/operator at index " + i);
            }
        }

        System.out.println("\n✔ Buses with valid price/operator info: " + validCount);
    }

    public void clickSelectSeatWithLowestPrice() {
        // Find all seat selection buttons
        By selectSeatButtonLocator = By.xpath("//button[contains(text(),'Select Seat')]");
        List<WebElement> seatButtons = driver.findElements(selectSeatButtonLocator);

        // Validate if prices are available
        if (allPrices.isEmpty()) {
            System.out.println("❌ No bus prices available. Please run printAllBusPricesWithOperators() first.");
            return;
        }

        // Find the lowest price index
        int lowest = Integer.MAX_VALUE;
        lowestPriceIndex = -1;

        for (int i = 0; i < allPrices.size(); i++) {
            if (allPrices.get(i) < lowest) {
                lowest = allPrices.get(i);
                lowestPriceIndex = i;
            }
        }

        // Select corresponding seat button
        if (lowestPriceIndex != -1 && lowestPriceIndex < seatButtons.size()) {
            WebElement seatButton = seatButtons.get(lowestPriceIndex);
            jsScroll(seatButton);
            seatButton.click();

            String selectedOperator = allOperators.get(lowestPriceIndex);
            int selectedPrice = allPrices.get(lowestPriceIndex);

            System.out.println(
                    " Clicked 'Select Seat' for lowest price: 🚌 " +
                            selectedOperator + " → ₹" + selectedPrice
            );

        } else {
            System.out.println("❌ Could not find a seat button for index: " + lowestPriceIndex);
        }
    }

    //In this method,it will select the seat depend on availability in order
    //if upper single available the selected and return method,close method
    //upperSingleBerth==>lowerSingle==>UpperCombinedBerth==>LowerCombinedBerth==>Seater

    public void chooseAvailableSeat() {
        System.out.println("\n=== 🚍 Seat Selection Started ===");

        boolean seatSelected = false;


        // 1. Upper Sleeper Single Berth
        System.out.println("🔍 Checking Upper Sleeper Single Berth...");
        List<WebElement> upperSingle = driver.findElements(upperSleeperSingleBerthXpath);
        if (!upperSingle.isEmpty()) {
            for (WebElement seat : upperSingle) {
                if (seat.isDisplayed() && seat.isEnabled()) {
                    jsScroll(seat);
                    click(seat);
                    System.out.println("✔ Selected Upper Sleeper Single Berth.");
                    seatSelected = true;
                    break;
                }
            }
        }
        if (!seatSelected) System.out.println("❌ Upper Sleeper Single Berth is not available.");

        // 2. Lower Sleeper Single Berth
        if (!seatSelected) {
            System.out.println("🔍 Checking Lower Sleeper Single Berth...");
            List<WebElement> lowerSingle = driver.findElements(lowerSleeperSingleBerthXpath);
            if (!lowerSingle.isEmpty()) {
                for (WebElement seat : lowerSingle) {
                    if (seat.isDisplayed() && seat.isEnabled()) {
                        jsScroll(seat);
                        click(seat);
                        System.out.println("✔ Selected Lower Sleeper Single Berth.");
                        seatSelected = true;
                        break;
                    }
                }
            }
            if (!seatSelected) System.out.println("❌ Lower Sleeper Single Berth is not available.");
        }

        // 3. Upper Sleeper Combined Berth
        if (!seatSelected) {
            System.out.println("🔍 Checking Upper Sleeper Combined Berth...");
            List<WebElement> upperCombined = driver.findElements(upperSleeperCombinedBerthXpath);
            if (!upperCombined.isEmpty()) {
                for (WebElement seat : upperCombined) {
                    if (seat.isDisplayed() && seat.isEnabled()) {
                        jsScroll(seat);
                        click(seat);
                        System.out.println("✔ Selected Upper Sleeper Combined Berth.");
                        seatSelected = true;
                        break;
                    }
                }
            }
            if (!seatSelected) System.out.println("❌ Upper Sleeper Combined Berth is not available.");
        }

        // 4. Lower Sleeper Combined Berth
        if (!seatSelected) {
            System.out.println("🔍 Checking Lower Sleeper Combined Berth...");
            List<WebElement> lowerCombined = driver.findElements(lowerSleeperCombinedBerthXpath);
            if (!lowerCombined.isEmpty()) {
                for (WebElement seat : lowerCombined) {
                    if (seat.isDisplayed() && seat.isEnabled()) {
                        jsScroll(seat);
                        click(seat);
                        System.out.println("✔ Selected Lower Sleeper Combined Berth.");
                        seatSelected = true;
                        break;
                    }
                }
            }
            if (!seatSelected) System.out.println("❌ Lower Sleeper Combined Berth is not available.");
        }

        // 5. Seater
        if (!seatSelected) {
            System.out.println("🔍 Checking Seater...");
            List<WebElement> seaters = driver.findElements(seaterSeatXpath);
            if (!seaters.isEmpty()) {
                for (WebElement seat : seaters) {
                    if (seat.isDisplayed() && seat.isEnabled()) {
                        jsScroll(seat);
                        click(seat);
                        System.out.println("✔ Selected Seater.");
                        seatSelected = true;
                        break;
                    }
                }
            }
            if (!seatSelected) System.out.println("❌ No seats or berths available.");
        }

        // ✅ After seat is selected, print seat number

        if (seatSelected) {
            try {
                sleep(500); // Use base class sleep method
                List<WebElement> seatInfo = driver.findElements(selectedSeatInfoXpath);
                if (!seatInfo.isEmpty()) {
                    for (WebElement info : seatInfo) {
                        if (info.isDisplayed()) {
                            System.out.println("🪑 Selected Seat/Berth Number: " + info.getText());
                        }
                    }
                } else {
                    System.out.println("⚠ Could not fetch selected seat/berth number.");
                }
            } catch (Exception e) {
                System.out.println("⚠ Error while retrieving seat/berth number: " + e.getMessage());
            }
        }


        // 🔒 Final assertion
        assertTrue("❌ Test failed: No seat or berth was available for selection.", seatSelected);
    }

    public void selectPickupPointAfterSeatSelect(String pickupPoint) {
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

       WebElement boardingPoint = wait.until(ExpectedConditions.elementToBeClickable(
               By.xpath("//div[normalize-space()='Boarding Points']/following::div[normalize-space()='" + pickupPoint + "']")));

       jsScrollToCentre(boardingPoint);
       sleep(500);
       jsClick(boardingPoint);

       System.out.println("✔ After Seat Select Pickup point selected: " + pickupPoint);
   }

    public void selectDropPointAfterSeatSelect(String dropPoint) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));


        WebElement droppingPoint = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[normalize-space()='Drop Points']/following::div[normalize-space()='"+ dropPoint +"']")));
        //div[normalize-space()='Drop Points']/following::div[normalize-space()='Electronic City'][1]

        jsScrollToCentre(droppingPoint);
        sleep(500);
        jsClick(droppingPoint);
        sleep(1000);

        System.out.println("✔ After Seat Select Drop point selected: " + dropPoint);
    }

    public void proceedToPassengerDetails() {
        click(waitForVisible(continueButton));
        sleep(500);
        System.out.println("➡ Navigating to passenger details page");
    }
}
