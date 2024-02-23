package org.my.sandbox.fhirrestapi.utilities;

public class FHIREndpoints {
    public static final String BASE_URL = "https://hapi.fhir.org";
    public static final String BASE_PATH = "/baseR4";

    public static String getPatientEndpoint() {
        return "/Patient";
    }

    public static String getPatientIdEndpoint(String patientId) {
        return String.format("/Patient/%s", patientId);
    }
}
