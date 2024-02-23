package org.my.sandbox.fhirrestapi.tests.stepDefinitions;

import org.my.sandbox.fhirrestapi.tests.ScenarioContext;
import org.my.sandbox.fhirrestapi.tests.TestContext;

public class BaseTest {
    protected final TestContext testContext;
    private ScenarioContext scenarioContext;

    public BaseTest(TestContext testContext) {
        this.testContext = testContext;
        this.scenarioContext = this.testContext.getScenarioContext();
    }

    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }
}
