package testRunner;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
	//features ="classpath:features/footer.feature",
				
		//features = "classpath:features/addremovecart.feature",
		
				features = "src/test/resources/features", 
				//features = "classpath:features/login.feature",
				//features = "classpath:features/hamberger.feature",
						//features = "classpath:features/sort.feature",
								//features = "classpath:features/endtoendflow.feature",
											
		
		
		 glue = { "stepDefination" },
			plugin = {
					"pretty",
					"html:target/cucumber-reports/cucumber.html",
					"json:target/cucumber.json"
			},
			monochrome = true
	)
public class TestRunner {
}

 