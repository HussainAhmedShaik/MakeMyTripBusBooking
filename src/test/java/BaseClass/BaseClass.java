package BaseClass;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class BaseClass {

    public static WebDriver driver;
    public static JavascriptExecutor js;
    public static WebDriverWait wait;
    public static Actions actions;
    public static Properties prop;

    // LOAD CONFIG FILE
    public static void loadConfig() {
        if (prop == null) {
            try {
                prop = new Properties();
                FileInputStream fis = new FileInputStream("src/test/resources/config/config.properties");
                prop.load(fis);
            } catch (Exception e) {
                throw new RuntimeException("❌ config.properties NOT FOUND: " + e.getMessage());
            }
        }
    }

    // LAUNCH BROWSER (Headless + Headed)
    public static void launchBrowser(String browserName, String mode) {

        String projectPath = System.getProperty("user.dir");
        String driverFolder = projectPath + "/drivers/";
        boolean headless = mode.equalsIgnoreCase("headless");

        switch (browserName.toLowerCase()) {

            case "chrome":
                System.setProperty("webdriver.chrome.driver", driverFolder + "chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) chromeOptions.addArguments("--headless=new", "--disable-gpu");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                System.setProperty("webdriver.gecko.driver", driverFolder + "geckodriver.exe");
                FirefoxOptions ffOptions = new FirefoxOptions();
                if (headless) ffOptions.addArguments("-headless");
                driver = new FirefoxDriver(ffOptions);
                break;

            case "edge":
                System.setProperty("webdriver.edge.driver", driverFolder + "msedgedriver.exe");
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) edgeOptions.addArguments("--headless=new");
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new RuntimeException("❌ Invalid browser name mentioned in config.properties");
        }

         // Browser Settings
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        actions = new Actions(driver);

        System.out.println("🚀 Browser Launched: " + browserName + " | Mode: " + mode);
    }

    // OPEN APPLICATION URL
    public static void openApplication(String url) {
        driver.get(url);
        System.out.println("🌍 Navigated To: " + url);
    }

    //send keys

    public static void sendKeysMethod(WebElement ela, String text) {
        ela.sendKeys(text);
    }


    public static void scrollMethod(WebElement element) {
        js = (JavascriptExecutor) driver;
        js.executeScript("argument[0].click()", element);
    }

    public static void windowHandleMethod(int ind) {
        Set<String> windowHandles = driver.getWindowHandles();
        List<String> tabs = new ArrayList<>(windowHandles);
        driver.switchTo().window(tabs.get(ind));
    }

    public static void alertAccept() {
        driver.switchTo().alert().accept();
    }

    public static void alertDismiss() {
        driver.switchTo().alert().dismiss();
    }

    public static void robotMethod() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));

    }

    // WAIT UTILITIES
    public static WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    // CLICK (Fallback JS Click if normal fails)
    public static void click(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            jsClick(element);
        }
    }
    // SEND INPUT
    public static void type(WebElement element, String value) {
        try {
            element.clear();
        } catch (Exception ignored) {}
        element.sendKeys(value);
    }

    // SELECT DROPDOWN OPTION
    public static void selectOption(WebElement element, String visibleText) {
        new Select(element).selectByVisibleText(visibleText);
    }

    public static void selectByText(WebElement element, String text) {
        new Select(element).selectByVisibleText(text);

    }

    // GET TEXT
    public static String getText(WebElement element) {
        return element.getText().trim();
    }

    // JAVASCRIPT ACTIONS
    public static JavascriptExecutor js() {

        return (JavascriptExecutor) driver;
    }

    public static void jsClick(WebElement element) {

        js().executeScript("arguments[0].click();", element);
    }

    public static void jsScroll(WebElement element) {
        js().executeScript("arguments[0].scrollIntoView(true);", element);
    }
    public void jsScrollToCentre(WebElement element) {
        js().executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        sleep(500); // Small pause to stabilize view
    }



    // ACTIONS SUPPORT
    public static void mouseHover(WebElement element) {
        actions.moveToElement(element).build().perform();
    }

    public static void doubleClick(WebElement element) {
        actions.doubleClick(element).perform();
    }

    public static void rightClick(WebElement element) {
        actions.contextClick(element).perform();
    }

    public static void pressEnter() {
        actions.sendKeys(Keys.ENTER).perform();
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void waitForPageToSettle() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".overlay")));
    }

    // ALERT HANDLING
    public static String getAlertText() {
        return driver.switchTo().alert().getText();
    }

    public static void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    public static void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }


    // SCREENSHOT

    public static String takeScreenshot(String fileName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);

            // Define screenshot directory
            String screenshotFolder = prop.getProperty("screenshotPath", "screenshots");
            Path screenshotDir = Paths.get(System.getProperty("user.dir"), screenshotFolder);
            Files.createDirectories(screenshotDir); // Ensures the folder exists

            // Final screenshot path
            Path screenshotPath = screenshotDir.resolve(fileName + ".png");
            Files.copy(src.toPath(), screenshotPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("📸 Screenshot captured at: " + screenshotPath); // Optional log
            return screenshotPath.toString();

        } catch (IOException e) {
            throw new RuntimeException("❌ Screenshot failed: " + e.getMessage());
        }
    }

    // CLOSE BROWSER
    public static void closeBrowser() {
        if (driver != null) {
            driver.quit();
            System.out.println("🛑 Browser Closed Successfully");
        }
    }
}
