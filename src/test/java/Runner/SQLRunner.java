package Runner;


import Utility.SQLUtility;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/Feature/Database.feature",
        tags = "@run",
        glue = {"Definition"}
)

public class SQLRunner {
    public static SQLUtility sqlUtility;

    @BeforeClass
    public static void createConnection() throws ClassNotFoundException {
        sqlUtility = new SQLUtility();
        sqlUtility.getConnection();
        System.out.println("Started");
    }

    @AfterClass
    public static void closeConnection(){
        sqlUtility.closeConnection();
        System.out.println("Completed");
    }
}
