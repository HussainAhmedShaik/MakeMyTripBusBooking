package Hooks;

import BaseClass.BaseClass;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    @Before
    public void setUp() {

        // Load config.properties only once
        BaseClass.loadConfig();

        String browser = BaseClass.prop.getProperty("browser");
        String mode = BaseClass.prop.getProperty("mode");
        String url = BaseClass.prop.getProperty("url");

        System.out.println("\n==================== STARTING TEST ====================");

        BaseClass.launchBrowser(browser, mode);
        BaseClass.openApplication(url);

        System.out.println("🧪 Test Running On → Browser: " + browser + " | Mode: " + mode);
        System.out.println("🌍 URL Opened → " + url);
        System.out.println("=======================================================\n");
    }

    @After
    public void tearDown(Scenario scenario) {

        System.out.println("\n==================== TEST FINISHED ====================");

        if (scenario.isFailed()) {
            System.out.println("❌ Test Failed: " + scenario.getName());
            System.out.println("📸 Capturing Failure Screenshot...");

            try {
                String path = BaseClass.takeScreenshot("Failed_" + System.currentTimeMillis());
                System.out.println("📁 Screenshot saved: " + path);
            } catch (Exception e) {
                System.out.println("⚠ Screenshot capture failed: " + e.getMessage());
            }

        } else {
            System.out.println("✔ Test Passed: " + scenario.getName());
        }

        BaseClass.closeBrowser();
        System.out.println("🛑 Browser Closed");
        System.out.println("=======================================================\n");
    }
}
