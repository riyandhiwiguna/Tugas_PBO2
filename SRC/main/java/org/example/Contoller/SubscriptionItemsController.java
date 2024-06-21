package org.example.Controller;

import org.example.server.DatabaseManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class SubscriptionItemsController {

    private DatabaseManager databaseManager;

    public SubscriptionItemsController(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }


    public JSONArray getSubscriptions(){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM SubscriptionItems";

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);

            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("subscription",resultSet.getInt("subscription"));
                jsonUser.put("item",resultSet.getInt("item"));
                jsonUser.put("quantity",resultSet.getInt("quantity"));
                jsonUser.put("price",resultSet.getInt("price"));
                jsonUser.put("amount",resultSet.getInt("amount"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }
    public JSONArray getCard(int idCard){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM subscriptionitems WHERE subscription=" + getSubscriptions();

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);

            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("subscription",resultSet.getInt("subscription"));
                jsonUser.put("item",resultSet.getString("item"));
                jsonUser.put("quantity",resultSet.getString("quantity"));
                jsonUser.put("price",resultSet.getString("price"));
                jsonUser.put("amount",resultSet.getString("amount"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }
    public JSONArray getProductFilter(String field, String cond, int val){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM subscriptionitems WHERE " + field + cond + "'" + val + "'";

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);

            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("subscription",resultSet.getInt("subscription"));
                jsonUser.put("item",resultSet.getString("item"));
                jsonUser.put("quantity",resultSet.getString("quantity"));
                jsonUser.put("price",resultSet.getString("price"));
                jsonUser.put("amount",resultSet.getString("amount"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }}