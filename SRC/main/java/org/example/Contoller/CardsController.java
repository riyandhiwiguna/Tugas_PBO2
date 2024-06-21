package org.example.Controller;

import org.example.server.DatabaseManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class CardsController {

    private DatabaseManager databaseManager;

    public CardsController(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }


    public JSONArray getCard(){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM cards";

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);

            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("customer",resultSet.getInt("customer"));
                jsonUser.put("card_type",resultSet.getInt("card_type"));
                jsonUser.put("masked_number",resultSet.getInt("masked_number"));
                jsonUser.put("expiry_month",resultSet.getInt("expiry_month"));
                jsonUser.put("expiry_year",resultSet.getInt("expiry_year"));
                jsonUser.put("status",resultSet.getInt("status"));
                jsonUser.put("is_primary",resultSet.getInt("is_primary"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }
    public JSONArray getCard(int idCard){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM card WHERE id=" + idCard;

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);

            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("customer",resultSet.getString("customer"));
                jsonUser.put("card_type",resultSet.getString("card_type"));
                jsonUser.put("masked_number",resultSet.getString("masked_number"));
                jsonUser.put("expiry_month",resultSet.getString("expiry_month"));
                jsonUser.put("expiry_year",resultSet.getString("expiry_year"));
                jsonUser.put("status",resultSet.getString("status"));
                jsonUser.put("is_primary",resultSet.getString("is_primary"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }
    public JSONArray getCard(String field, String cond, int val){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM cards WHERE " + field + cond + "'" + val + "'";

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);

            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("customer",resultSet.getString("customer"));
                jsonUser.put("card_type",resultSet.getString("card_type"));
                jsonUser.put("masked_number",resultSet.getString("masked_number"));
                jsonUser.put("expiry_month",resultSet.getString("expiry_month"));
                jsonUser.put("expiry_year",resultSet.getString("expiry_year"));
                jsonUser.put("status",resultSet.getString("status"));
                jsonUser.put("is_primary",resultSet.getString("is_primary"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }}