package testRunner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.ANSI_COLORS_DISABLED_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "stepDefination")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value =
        "pretty, html:target/cucumber-reports/cucumber.html, " +
        "json:target/cucumber-reports/cucumber.json, " +
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm, " +
        "rerun:target/rerun.txt")
@ConfigurationParameter(key = ANSI_COLORS_DISABLED_PROPERTY_NAME, value = "true")
public class TestRunner {
}