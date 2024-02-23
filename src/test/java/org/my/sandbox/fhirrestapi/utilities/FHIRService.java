package org.my.sandbox.fhirrestapi.utilities;

import ca.uhn.fhir.parser.IParser;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hl7.fhir.r4.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class FHIRService {
    private IParser fhirParser;
    private List<Response> lstResponses;
    private RequestSpecification request;

    public FHIRService(IParser fhirParser) {
        this.fhirParser = fhirParser;
        this.lstResponses = new ArrayList<>();

        this.request = RestAssured.given();
        this.request.header("Content-Type", "application/fhir+json");
    }

    public Response createPatient(Patient objPatient) {
        return this.request.body(this.fhirParser.encodeResourceToString(objPatient)).post(FHIREndpoints.getPatientEndpoint());
    }

    public Response getPatient(String patientId) {
        return this.request.get(FHIREndpoints.getPatientIdEndpoint(patientId));
    }

    public Response deletePatient(String patientId) {
        return this.request.delete(FHIREndpoints.getPatientIdEndpoint(patientId));
    }

    public IParser getFhirParser() {
        return fhirParser;
    }

    public List<Response> getLstResponses() {
        return lstResponses;
    }

    public void clearResponses() {
        this.lstResponses.clear();
    }

    public void storeResponse(Response response) {
        this.lstResponses.add(response);
    }
}
