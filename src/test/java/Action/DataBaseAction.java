package Action;

import Utility.SQLUtility;

import java.sql.SQLException;
import java.util.*;

public class DataBaseAction {

    String tablename;
    SQLUtility sqlUtility;
    static List<String> printSet ;
    static HashMap<String,String> conditionSet;
    static Map<String,String> dataSet;
    HashMap<String,String> updateSet;

    public DataBaseAction(String tablename){
        printSet = new ArrayList<>();
        conditionSet = new HashMap<>();
        dataSet = new IdentityHashMap<>();
        updateSet = new HashMap<>();
        sqlUtility = new SQLUtility();
        this.tablename = tablename;
    }
    public void getOtp(String queryData) throws SQLException {
        printSet.add("otp");
        conditionSet.put("phone",queryData);
        sqlUtility.printData(tablename,printSet,conditionSet);
    }

    public void createConnection() {
    }

    public void fetchAllDetails() throws SQLException {
        sqlUtility.printAllData(tablename);
    }

    public void displayDetails() {
    }

    public void createUser(String id,String phone, String email, String otp) {
        dataSet.put("id",id);
        dataSet.put("email",email);
        dataSet.put("phone",phone);
        dataSet.put("otp",otp);
    }

    public void executeInsert() throws SQLException {
        sqlUtility.insertData(tablename,dataSet);
    }

    public void displayDetail() throws SQLException {
        printSet.add("id");
        printSet.add("email");
        printSet.add("phone");
        printSet.add("otp");
        conditionSet.put("id",dataSet.get("id"));
        sqlUtility.printData(tablename,printSet,conditionSet);
    }

    public void updateDetails(String to) {
        updateSet.put("phone",to);

    }

    public void updateQuery(String id) throws SQLException {
        dataSet.put("id",id);
        sqlUtility.updateData(tablename,dataSet,updateSet);
    }

    public void deleteQuery(String id) throws SQLException {
        dataSet.put("id",id);
        sqlUtility.deleteData(tablename,dataSet);
    }
}
