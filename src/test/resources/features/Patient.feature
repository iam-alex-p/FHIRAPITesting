Feature: End to End Tests for HAPI FHIR Patient Resource

  Scenario: Create a Basic Patient
    When I create Patients with following data
      | givenNames     | familyName |
      | Michael,Thomas | Smith      |
      | David          | Muller     |
      | [blank]        | [blank]    |
    Then Patients are created successfully
    When I retrieve created Patients
    Then Patients are retrieved successfully
    When I delete created Patients
    Then Patients are deleted successfully