package org.my.sandbox.fhirrestapi.tests.stepDefinitions;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;
import org.my.sandbox.fhirrestapi.tests.TestContext;
import org.my.sandbox.fhirrestapi.utilities.TestDataEntity;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PatientStepDefinition extends BaseTest {
    private List<Patient> lstExpectedPatients = new ArrayList<>();

    @DataTableType(replaceWithEmptyString = "[blank]")
    public Patient patientTransformer(Map<String, String> row) {
        Patient objPat = new Patient();

        final String strGivenNames = row.getOrDefault("givenNames", testContext.getFaker().name().firstName());
        String[] arrGivenNames = strGivenNames.split(",");

        for (String givenName : arrGivenNames)
            objPat.addName().addGiven(givenName);

        objPat.addName().setFamily(row.getOrDefault("familyName", testContext.getFaker().name().lastName()));
        return objPat;
    }

    public PatientStepDefinition(TestContext testContext) {
        super(testContext);
    }

    @When("I create Patients with following data")
    public void i_create_patients_with_following_data(List<Patient> lstPatients) {
        this.testContext.getFHIRService().clearResponses();

        for (Patient objPatient: lstPatients) {
            this.lstExpectedPatients.add(objPatient);
            this.testContext.getFHIRService().storeResponse(this.testContext.getFHIRService().createPatient(objPatient));
        }
    }

    @Then("Patients are created successfully")
    public void patient_are_created() {
        List<Patient> lstActualPatients = new ArrayList<>();

        for (int i = 0; i < this.testContext.getFHIRService().getLstResponses().size(); i++) {
            Response response = this.testContext.getFHIRService().getLstResponses().get(i);

            Patient actPatient = this.testContext.getFHIRService().getFhirParser().parseResource(Patient.class, response.asString());
            lstActualPatients.add(actPatient);
            Patient expPatient = this.lstExpectedPatients.get(i);

            HumanName actPatientName = actPatient.getNameFirstRep();
            HumanName expPatientName = expPatient.getNameFirstRep();

            Assert.assertEquals(response.getStatusCode(), 201);
            Assert.assertEquals(actPatientName.getFamily(), expPatientName.getFamily());
            Assert.assertEquals(actPatientName.getGivenAsSingleString(), expPatientName.getGivenAsSingleString());
        }

        this.getScenarioContext().setContext(TestDataEntity.PATIENT, lstActualPatients);
    }

    @When("I retrieve created Patients")
    public void i_retrieve_prev_created_patients() {
        this.testContext.getFHIRService().clearResponses();

        for (Patient patient: (List<Patient>) this.getScenarioContext().getContext(TestDataEntity.PATIENT)) {
            Response response = this.testContext.getFHIRService().getPatient(patient.getIdElement().getIdPart());
            this.testContext.getFHIRService().storeResponse(response);
        }
    }

    @Then("Patients are retrieved successfully")
    public void patients_are_retrieved() {
        for (Patient patient : (List<Patient>) this.getScenarioContext().getContext(TestDataEntity.PATIENT)) {
            for (Response response : this.testContext.getFHIRService().getLstResponses()) {
                Assert.assertEquals(response.getStatusCode(), 200);

                Patient patientResponse = this.testContext.getFHIRService().getFhirParser().parseResource(Patient.class, response.asString());
                if (patient.getIdElement().getIdPart().equals(patientResponse.getIdElement().getIdPart())) {
                    Assert.assertEquals(patientResponse.getNameFirstRep().getFamily(), patient.getNameFirstRep().getFamily());
                    Assert.assertEquals(patientResponse.getNameFirstRep().getGivenAsSingleString(), patient.getNameFirstRep().getGivenAsSingleString());
                }
            }
        }
    }

    @When("I delete created Patients")
    public void i_delete_created_patients() {
        this.testContext.getFHIRService().clearResponses();

        for (Patient patient: (List<Patient>) this.getScenarioContext().getContext(TestDataEntity.PATIENT)) {
            Response response = this.testContext.getFHIRService().deletePatient(patient.getIdElement().getIdPart());
            this.testContext.getFHIRService().storeResponse(response);
        }
    }

    @Then("Patients are deleted successfully")
    public void patients_are_deleted() {
        for (Patient patient: (List<Patient>) this.getScenarioContext().getContext(TestDataEntity.PATIENT)) {
            Response response = this.testContext.getFHIRService().getPatient(patient.getIdElement().getIdPart());
            Assert.assertEquals(response.getStatusCode(), 410);
        }
    }
}
