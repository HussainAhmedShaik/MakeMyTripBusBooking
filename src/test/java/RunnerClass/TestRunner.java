package RunnerClass;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(

        features = "src/test/resources/Features",
        glue = {"StepDefinition", "Hooks"},
        plugin = {
                "pretty",
                "html:target/CucumberReports/HTMLReport.html",
        },
        monochrome = true,
        dryRun = false
)

public class TestRunner {
}
