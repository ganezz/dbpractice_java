package Utility;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.*;

public class MongoUtility {
    public static MongoClient mongoClient;
    public static DB database;
    public static DBCollection collection;
    public static DBObject query;
    public static DBCursor data;

    public static void main(String[] args) throws UnknownHostException {

        String collection = "mycal";
        String db ="sri";
        HashMap<String,String> dataSet = new HashMap<>();
        dataSet.put("id","1");
        dataSet.put("name","sri");
        dataSet.put("email", "srily@cprog.com");
        dataSet.put("phone","98865684123");
        dataSet.put("otp","7894");

        List<String> printSet = new ArrayList<>();
        printSet.add("id");
        printSet.add("name");
        printSet.add("email");
        printSet.add("phone");
        printSet.add("otp");

        HashMap<String,String> conditionSet = new HashMap<>();
       // conditionSet.put("name","abs");
        conditionSet.put("id","1");

        HashMap<String,String> updateSet = new HashMap<>();
        updateSet.put("email","dnapal@were.com");

        getConnection(db,collection);

       // insertData(dataSet);
       // updateData(conditionSet,dataSet);
       // updateOrInsert(conditionSet,dataSet);
       // deleteData(conditionSet);
        printAllData(printSet);
        //printSelectedData(conditionSet,printSet);
       // printData(conditionSet);
        closeConnection();
    }

    private static void printSelectedData(HashMap<String, String> conditionSet, List<String> printSet) {
        query = new BasicDBObject(conditionSet);
        data = collection.find(query);
        printer(printSet);
    }

    private static void printAllData( List<String> printSet) {
        data = collection.find();
       printer();
    }


    private static void printData(HashMap<String, String> conditionSet) {
        query = new BasicDBObject(conditionSet);
        data = collection.find(query);
        printer();
    }

    private static void printer(List<String> printSet) {
        for ( DBObject datas: data) {
            for(String key : printSet)
                System.out.print(key +" | ");
            System.out.println();
            for(String key  : printSet)
                System.out.print(datas.get(key) + " | ");
            System.out.println();
            data.next();
        }
    }
    private static void printer() {
        for ( DBObject datas: data) {
            for(String key :datas.keySet())
                System.out.print(key +" | ");
            System.out.println();
            for(String key  : datas.keySet())
                System.out.print(datas.get(key) + " | ");
            System.out.println();
            data.next();
        }
    }

    private static void insertData(HashMap<String, String> dataSet) {
        collection.insert(new BasicDBObject(dataSet));
    }

    private static void updateData( HashMap<String, String> conditionSet, HashMap<String, String> updateSet) {
        collection.update(new BasicDBObject(conditionSet),new BasicDBObject("$set",new BasicDBObject(updateSet)));
    }

    private static void deleteData(HashMap<String, String> conditionSet) {
        collection.remove(new BasicDBObject(conditionSet));
    }

    private static void closeConnection() {
        mongoClient.close();
    }

    private static void updateOrInsert(HashMap<String, String> conditionSet, HashMap<String, String> updateSet) {
        collection.update(new BasicDBObject(conditionSet),new BasicDBObject("$set",new BasicDBObject(updateSet)),true,false);
    }

    private static void getConnection(String db,String collection) throws UnknownHostException {
        mongoClient = new MongoClient("localhost:27017");
        database = mongoClient.getDB(db);
        MongoUtility.collection = database.getCollection(collection);
    }
}
