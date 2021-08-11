Feature: Validating Party table in Onboarding Database

  @run
  Scenario: Validating a OTP on Data Base
    Given user connection to the database table "party"
    And getting a otp from database of mobile number "54589645654"


  Scenario: List all the user details with OTP
    Given user connection to the database table "party"
    When user send the query to fetch al details
    Then all user details should display


  Scenario: Create a new party
    Given user connection to the database table "party"
    And creates a new party id as "6",phone as "8945454544",email as "honeywell@gov.in" and otp as "1234"
    When user executes the Insert query
    Then party details should visible


  Scenario: Update a existing party
    Given user connection to the database table "party"
    And updates details of existing party phone number "8945454544" to "98432984910"
    When user executes the Updates query Id as "6"
    Then party details should visible


  Scenario: Delete a existing party
    Given user connection to the database table "party"
    When user executes the Delete query Id as "6"
    Then party should not visible
