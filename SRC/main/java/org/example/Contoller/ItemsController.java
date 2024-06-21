package org.example.Controller;

import org.example.server.DatabaseManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class ItemsController {

    private DatabaseManager databaseManager;

    public ItemsController(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }


    public JSONArray getItems(){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM items";

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);

            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("name",resultSet.getString("name"));
                jsonUser.put("price",resultSet.getString("price"));
                jsonUser.put("type",resultSet.getString("type"));
                jsonUser.put("is_active",resultSet.getString("is_active"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }

    public JSONArray getItem(int idItem){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM items WHERE id=" + idItem;

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);

            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("name",resultSet.getString("name"));
                jsonUser.put("price",resultSet.getString("price"));
                jsonUser.put("type",resultSet.getString("type"));
                jsonUser.put("is_active",resultSet.getString("is_active"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }
    public JSONArray getProductFilter(String field, String cond, int val){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM items WHERE " + field + cond + "'" + val + "'";

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);

            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("name",resultSet.getString("name"));
                jsonUser.put("price",resultSet.getString("price"));
                jsonUser.put("type",resultSet.getString("type"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }

    public boolean updateItems(int idItem, JSONObject requestBodyJson){
        String name = requestBodyJson.optString("name");
        String price = requestBodyJson.optString("price");
        String type = requestBodyJson.optString("type");
        String is_active = requestBodyJson.optString("is_active");
        PreparedStatement statement = null;
        int insertedRow = 0;
        System.out.println("start update");
        String querySql = "UPDATE items SET name=?, price=?, type=?, is_active=?  WHERE id=" +idItem;
        try {
            statement = databaseManager
                    .getConnection()
                    .prepareStatement(querySql);
            statement.setString(1,name);
            statement.setString(2,price);
            statement.setString(3,type);
            statement.setString(4,is_active);

            insertedRow = statement.executeUpdate();
            System.out.println(" end update");
        }catch (SQLException e){
            e.printStackTrace();
        }


        return insertedRow > 0;
    }

    public boolean addItem(JSONObject requestBodyJson){
        String name = requestBodyJson.optString("name");
        String price = requestBodyJson.optString("price");
        String type = requestBodyJson.optString("type");
        String is_active = requestBodyJson.optString("is_active");
        PreparedStatement statement = null;
        int insertedRow = 0;

        String querySql = "INSERT INTO items(name,price,type,is_active) VALUES(?,?,?,?)";
        try {
            System.out.println("add item");
            statement = databaseManager
                    .getConnection()
                    .prepareStatement(querySql);
            statement.setString(1,name);
            statement.setString(2,price);
            statement.setString(3,type);
            statement.setString(4,is_active);

            insertedRow = statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return insertedRow > 0;
    }

    public boolean deleteItems(int idItem){

        int rowDeleted = 0;
        PreparedStatement statement = null;
        String querySql = "DELETE FROM items WHERE id="+idItem;
        try {
            statement = this.databaseManager
                    .getConnection()
                    .prepareStatement(querySql);
            rowDeleted = statement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }

        return rowDeleted > 0;
    }
}
