package org.my.sandbox.fhirrestapi.tests.stepDefinitions;

import io.restassured.RestAssured;
import org.my.sandbox.fhirrestapi.tests.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.my.sandbox.fhirrestapi.utilities.FHIREndpoints;

public class Hooks {
    private final TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void beforeSteps() {
        RestAssured.baseURI = FHIREndpoints.BASE_URL;
        RestAssured.basePath = FHIREndpoints.BASE_PATH;

        testContext.setupFHIRContext();
    }

    @After
    public void afterSteps() {

    }
}
