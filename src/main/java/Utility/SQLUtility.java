package Utility;


import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.sql.*;
import java.util.*;

public class SQLUtility {
    static Connection conn = null;
    static Statement stmt = null;
    static ResultSet resultSet = null;
    static ResultSet headerSet = null;
    static int noColumns = 0,affectedRows =0;
    static String connectionURLSQL = "jdbc:mysql://localhost:3306/onboarding?autoReconnect=true&useSSL=false";
    static String user ="root";
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        SQLUtility sqlUtility = new SQLUtility();
        String tableName = "party";
        HashMap<String,String> dataSet = new HashMap<>();
        dataSet.put("id","3");
        dataSet.put("email","srigah@sri.com");
        dataSet.put("phone","54589645654");
        dataSet.put("otp","655689");

        List<String> printSet = new ArrayList<>();
        printSet.add("email");
        printSet.add("phone");

        HashMap<String,String> conditionSet = new HashMap<>();
        conditionSet.put("id","");
      //  conditionSet.put("phone","564654");

        HashMap<String,String> updateSet = new HashMap<>();
        updateSet.put("phone","948789156");
        updateSet.put("email","drcaryas@got.com");

        sqlUtility.getConnection();


       // insertData(tableName,dataSet);
        //updateData(tableName,conditionSet,updateSet);
        //deleteData(tableName,conditionSet);
       // sqlUtility.printAllData(tableName);
       sqlUtility.printData(tableName,printSet,conditionSet);
        sqlUtility.closeConnection();
    }

    public void updateData(String tableName, Map<String, String> cond, HashMap<String, String> updateSet) throws SQLException {
        String table = "update "+tableName+" set ";
        StringBuilder set= new StringBuilder();
        StringBuilder con = new StringBuilder();
        String condition=" where ";

        Set<String> conKey = cond.keySet();
        for (String col : conKey){
            con.append(col+" = \""+cond.get(col)+"\" AND ");
        }

        Set<String> colName = updateSet.keySet();
        for (String col : colName){
            set.append(col+" = \""+updateSet.get(col)+"\",");
        }

        String where = con.substring(0,con.length()-4);
        StringBuilder update = set.deleteCharAt(set.length()-1);
        System.out.println(table+update+condition+where);
        affectedRows = stmt.executeUpdate(table+update+condition+where);

    }


    public  void deleteData(String tableName, Map<String, String> cond) throws SQLException {
        String query = "delete from "+tableName+" where ";
        StringBuilder con = new StringBuilder();
        Set<String> conKey = cond.keySet();
        for (String col : conKey){
            con.append(col+" = \""+cond.get(col)+"\" AND ");
        }
        String where = con.substring(0,con.length()-4);
        System.out.println("Data Deleted  : "+where);
        affectedRows = stmt.executeUpdate(query+where);
    }

    public  void insertData(String tableName, Map<String, String> dataSet) throws SQLException {
        StringBuilder set = new StringBuilder();
        String query ="insert into "+tableName;
        StringBuilder column = new StringBuilder();

        Set<String> keys = dataSet.keySet();
        for (String key : keys){
            column.append(key).append(",");
            set.append("\"").append(dataSet.get(key)).append("\",");
        }
        StringBuilder columns = column.deleteCharAt(column.length()-1);
        StringBuilder values = set.deleteCharAt(set.length()-1);
        query = query+"("+columns+")  values ("+values+")";
        System.out.println(query);
        affectedRows = stmt.executeUpdate(query);

    }
    public  void printHeader(String tablename) throws SQLException {
        headerSet = stmt.executeQuery("SHOW COLUMNS FROM "+tablename);
        while (headerSet.next()){
            System.out.print(headerSet .getString(1)+ " | ");
        }
    }
    public ResultSet printAllData(String tableName) throws SQLException {
        countColumns(tableName);
        resultSet = stmt.executeQuery("select * from "+tableName);
        if (resultSet == null){
            System.out.println("Sorry nothing to show bye..!");
        }
        while (resultSet.next()) {
            System.out.println();
            affectedRows++;
            for (int i=1;i <= noColumns;i++)
                System.out.print(resultSet .getString(i)+" | ");
        }
        return resultSet;
    }

    public ResultSet printData(String tableName, List<String> printSet, Map<String, String> condition) throws SQLException {
        countColumns(tableName);
        StringBuilder cond = new StringBuilder();
        StringBuilder column = new StringBuilder();
        Set<String> conKey = condition.keySet();
        String where="";
        for (String col : conKey){
            cond.append(col+" = \""+condition.get(col)+"\" AND ");
        }
         where = cond.substring(0,cond.length()-4);
        for (String key : printSet){
            column.append(key).append(",");
            System.out.print(key+" | ");
        }
        StringBuilder columns = column.deleteCharAt(column.length()-1);

        resultSet = stmt.executeQuery("select "+columns+" from "+tableName+" where "+where);
        System.out.println();

        if (resultSet == null){
            System.out.println("Sorry nothing to show bye..!");
        }
        while (resultSet.next()) {
            affectedRows++;
            for (int i=1;i <= printSet.size();i++)
                System.out.print(resultSet .getString(i)+" | ");
        }
        return resultSet;
    }

    private void countColumns(String tablename) throws SQLException {
        headerSet = stmt.executeQuery("SHOW COLUMNS FROM "+tablename);
        noColumns =0;
        while (headerSet.next()){
            noColumns++;
        }
    }

    public void getConnection() throws ClassNotFoundException {
        try {
            conn = DriverManager.getConnection(connectionURLSQL, user, "root");
            stmt = conn.createStatement();
            System.out.println("\nConnection Initialized ");
        }catch (SQLException e){
            System.out.println("\nConnection refused :"+e);
        }
    }
    public void closeConnection(){
        if (affectedRows ==0){
            System.out.println("\n\nQuery Didn't make change or Data not found");
        }else {
            System.out.println("\n\n"+affectedRows +" rows Affected");
        }
        if ((resultSet != null) && (stmt != null) &&(conn != null)) {
            try {
                resultSet.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
            }
        }
        System.out.println("\n\nConnection closed");
    }
}
