package org.my.sandbox.fhirrestapi.tests;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.github.javafaker.Faker;
import org.my.sandbox.fhirrestapi.utilities.FHIRService;

public class TestContext {
    private IParser jsonParser;
    private Faker faker;
    private FHIRService fhirService;
    private ScenarioContext scenarioContext;

    public void setupFHIRContext() {
        FhirContext ctx = FhirContext.forR4();
        this.jsonParser = ctx.newJsonParser().setPrettyPrint(true);

        this.faker = Faker.instance();
        this.fhirService = new FHIRService(this.jsonParser);
        this.scenarioContext = new ScenarioContext();
    }

    public IParser getJsonParser() {
        return jsonParser;
    }

    public Faker getFaker() {
        return faker;
    }

    public FHIRService getFHIRService() {
        return fhirService;
    }

    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }
}
