package org.my.sandbox.fhirrestapi.tests;

import org.my.sandbox.fhirrestapi.utilities.TestDataEntity;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
    private Map<String, Object> scenarioContext;

    public ScenarioContext() {
        this.scenarioContext = new HashMap<>();
    }

    public void setContext(TestDataEntity testDataEntity, Object value) {
        this.scenarioContext.put(testDataEntity.toString(), value);
    }

    public Object getContext(TestDataEntity testDataEntity) {
        return this.scenarioContext.get(testDataEntity.toString());
    }

    public boolean isContains(TestDataEntity testDataEntity) {
        return this.scenarioContext.containsKey(testDataEntity.toString());
    }
}
