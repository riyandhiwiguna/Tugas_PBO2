package org.example.Controller;

import org.example.server.DatabaseManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class ShippingAddressesController {

    private DatabaseManager databaseManager;

    public ShippingAddressesController(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }


    public JSONArray getShippingAddresses(){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM shippingaddresses";

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);

            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("customer",resultSet.getInt("customer"));
                jsonUser.put("title",resultSet.getInt("title"));
                jsonUser.put("line1",resultSet.getInt("line1"));
                jsonUser.put("line2",resultSet.getInt("line2"));
                jsonUser.put("city",resultSet.getInt("city"));
                jsonUser.put("province",resultSet.getInt("province"));
                jsonUser.put("postocde",resultSet.getInt("postocde"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }
    public JSONArray getShippingAddresses(int idShippingaddresse){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM shippingaddresse WHERE id=" + idShippingaddresse;

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);

            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("customer",resultSet.getString("customer"));
                jsonUser.put("title",resultSet.getString("title"));
                jsonUser.put("line1",resultSet.getString("line1"));
                jsonUser.put("line2",resultSet.getString("line2"));
                jsonUser.put("city",resultSet.getString("city"));
                jsonUser.put("province",resultSet.getString("province"));
                jsonUser.put("postocde",resultSet.getString("postocde"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }
    public JSONArray getShippingAddresses(String field, String cond, int val){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM shippingaddresses WHERE " + field + cond + "'" + val + "'";

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);

            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("customer",resultSet.getString("customer"));
                jsonUser.put("title",resultSet.getString("title"));
                jsonUser.put("line1",resultSet.getString("line1"));
                jsonUser.put("line2",resultSet.getString("line2"));
                jsonUser.put("city",resultSet.getString("city"));
                jsonUser.put("province",resultSet.getString("province"));
                jsonUser.put("postocde",resultSet.getString("postocde"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }}
