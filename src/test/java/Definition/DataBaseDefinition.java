package Definition;

import Action.DataBaseAction;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.SQLException;

public class DataBaseDefinition {
    public DataBaseAction dataBaseAction;
    @Given("user connection to the database table {string}")
    public void user_connection_to_the_database_table(String tablename) {
        dataBaseAction = new DataBaseAction(tablename);
        dataBaseAction.createConnection();
    }
    @Given("getting a otp from database of mobile number {string}")
    public void getting_a_otp_from_database(String queryData) throws SQLException {
        dataBaseAction.getOtp(queryData);
    }
    @When("user send the query to fetch al details")
    public void user_send_the_query_to_fetch_al_details() throws SQLException {
        dataBaseAction.fetchAllDetails();
    }
    @Then("all user details should display")
    public void all_user_details_should_display() {
        dataBaseAction.displayDetails();
    }
    @Given("creates a new party id as {string},phone as {string},email as {string} and otp as {string}")
    public void creates_a_new_party_id_as_name_as_phone_as_email_as_and_otp_as(String id, String phone, String email, String otp) {
        dataBaseAction.createUser(id,phone,email,otp);
    }

    @When("user executes the Insert query")
    public void user_executes_the_insert_query() throws SQLException {
        dataBaseAction.executeInsert();
    }

    @Then("party details should visible")
    public void created_party_should_visible() throws SQLException {
        dataBaseAction.displayDetail();
    }
    @Given("updates details of existing party phone number {string} to {string}")
    public void updates_details_of_existing_party_phone_number_to(String from, String to) {
        dataBaseAction.updateDetails(to);
    }

    @When("user executes the Updates query Id as {string}")
    public void user_executes_the_updates_query_id_as(String id) throws SQLException {
        dataBaseAction.updateQuery(id);
    }
    @When("user executes the Delete query Id as {string}")
    public void user_executes_the_delete_query_id_as(String id) throws SQLException {
        dataBaseAction.deleteQuery(id);
    }

    @Then("party should not visible")
    public void party_should_not_visible() throws SQLException {
        dataBaseAction.displayDetail();
    }

}
