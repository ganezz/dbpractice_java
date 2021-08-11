package Utility;

import java.sql.*;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

public class PostgresUtility {
    static Connection conn = null;
    static Statement stmt = null;
    static ResultSet resultSet = null;
    static ResultSet headerSet = null;
    static int noColumns = 0,affectedRows=0;
    static String postgresConnectionURL = "jdbc:postgresql://localhost:5432/party";
    static String user ="postgres";


    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        String tableName = "users";
        String database="postgres";
        Map<String,String> dataSet = new IdentityHashMap<>();
        dataSet.put("id","1");
        dataSet.put("email", "helloworld@cprog.com");
        dataSet.put("phone","404");

        HashMap<String,String> conditionSet = new HashMap<>();
        conditionSet.put("id","2");
        //conditionSet.put("phone","948789156");

        HashMap<String,String> updateSet = new HashMap<>();
        updateSet.put("phone","948789156");
        updateSet.put("email","moinca@friends.com");

        getConnection(database);

        printHeader(tableName);
     //   insertData(tableName,dataSet);
        //updateData(tableName,conditionSet,updateSet);
       // deleteData(tableName,conditionSet);
        printAllData(tableName);
      //  printData(tableName,conditionSet);
        closeConnection();
    }

    private static void updateData(String tableName, HashMap<String, String> cond, HashMap<String, String> updateSet) throws SQLException {
        String table = "update "+tableName+" set ";
        StringBuilder set= new StringBuilder();
        StringBuilder con = new StringBuilder();
        String condition=" where ";
        Set<String> conKey = cond.keySet();
        for (String col : conKey){
            con.append(col+" = '"+cond.get(col)+"' AND ");
        }
        Set<String> colName = updateSet.keySet();
        for (String col : colName){
            set.append(col+" = '"+updateSet.get(col)+"',");
        }
        String where = con.substring(0,con.length()-4);
        StringBuilder update = set.deleteCharAt(set.length()-1);
        System.out.println(table+update+condition+where);
        affectedRows = stmt.executeUpdate(table+update+condition+where+";");
    }


    private static void deleteData(String tableName, HashMap<String, String> cond) throws SQLException {
        String query = "delete from "+tableName+" where ";
        StringBuilder con = new StringBuilder();
        Set<String> conKey = cond.keySet();
        for (String col : conKey){
            con.append(col+" = '"+cond.get(col)+"' AND ");
        }
        String where = con.substring(0,con.length()-4);
        System.out.println(query+where+";");

       affectedRows=  stmt.executeUpdate(query+where+";") ;
    }

    private static void insertData(String tableName, Map<String, String> dataSet) throws SQLException {
        StringBuilder set = new StringBuilder();
        String query ="insert into "+tableName;
        StringBuilder column = new StringBuilder();

        Set<String> keys = dataSet.keySet();
        for (String key : keys){
            column.append(key).append(",");
            set.append("'").append(dataSet.get(key)).append("',");
        }
        StringBuilder columns = column.deleteCharAt(column.length()-1);
        StringBuilder values = set.deleteCharAt(set.length()-1);
        query = query+"("+columns+")  values ("+values+")"+";";
        System.out.println(query);
        affectedRows=stmt.executeUpdate(query);
    }
    public static void printHeader(String tablename) throws SQLException {
        headerSet = stmt.executeQuery("SELECT column_name FROM information_schema.columns WHERE table_name = '"+tablename+"' ;");
        System.out.println("SELECT column_name FROM information_schema.columns WHERE table_name = '"+tablename+"' ;");
        System.out.println();
        if (headerSet == null){
            System.out.println("Sorry nothing to show bye..!");
        }
        while (headerSet.next()){
            System.out.print(headerSet .getString(1)+ " | ");
        }
    }
    public static ResultSet printAllData(String tableName) throws SQLException {
        countColumns(tableName);
        resultSet = stmt.executeQuery("select * from "+tableName+";");

        if (resultSet == null){
            System.out.println("Sorry nothing to show bye..!");
        }
        while (resultSet.next()) {
            System.out.println();
            for (int i=1;i <= noColumns;i++)
                System.out.print(resultSet .getString(i)+" | ");
            affectedRows++;
        }
        return resultSet;
    }

    public static ResultSet printData(String tableName,Map<String, String> condition) throws SQLException {
        countColumns(tableName);
        StringBuilder cond = new StringBuilder();
        Set<String> conKey = condition.keySet();
        for (String col : conKey){
            cond.append(col+" = '"+condition.get(col)+"' AND ");
        }
        String where = cond.substring(0,cond.length()-4);
        resultSet = stmt.executeQuery("select * from "+tableName+" where "+where+";");
        if (resultSet == null){
            System.out.println("Sorry nothing to show bye..!");
        }
        while (resultSet.next()) {
            System.out.println();
            for (int i=1;i <= noColumns;i++)
                System.out.print(resultSet .getString(i)+" | ");
            affectedRows++;
        }
        return resultSet;
    }

    private static void countColumns(String tablename) throws SQLException {
        headerSet =  stmt.executeQuery("SELECT column_name FROM information_schema.columns WHERE table_name = '"+tablename+"' ;");
        noColumns =0;
        while (headerSet.next()){
            noColumns++;
        }
    }

    public static void getConnection(String database) throws ClassNotFoundException {
        try {
            if (database.equalsIgnoreCase("postgres")){
                Class.forName("org.postgresql.Driver");
                System.out.println("Postgres Database");
            }
            conn = DriverManager.getConnection(postgresConnectionURL, user, "root");
            stmt = conn.createStatement();
        }catch (SQLException e){
            System.out.println("\nConnection refused :"+e);
        }
    }
    public static void closeConnection(){
        if (affectedRows ==0){
            System.out.println("\n\nQuery Didn't make change");
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

