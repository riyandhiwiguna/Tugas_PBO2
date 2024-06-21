package org.example.Controller;

import org.example.server.DatabaseManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class CustomersController {
    private DatabaseManager databaseManager;

    public CustomersController(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }
    public JSONArray getCustomers(){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM customers";

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);
            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("email",resultSet.getString("email"));
                jsonUser.put("firstName",resultSet.getString("first_name"));
                jsonUser.put("lastName",resultSet.getString("last_name"));
                jsonUser.put("phoneNumber",resultSet.getString("phone_number"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }

    public JSONArray getCustomer(int customerId){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM customers WHERE id=" + customerId;

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);
            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("email",resultSet.getString("email"));
                jsonUser.put("firstName",resultSet.getString("first_name"));
                jsonUser.put("lastName",resultSet.getString("last_name"));
                jsonUser.put("phoneNumber",resultSet.getString("phone_number"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }

    public JSONArray getCustomerSubscription(int customerId){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM customers WHERE id=" + customerId;
        String querySql2 = "SELECT * FROM subscriptions WHERE customer=" + customerId;

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);

            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("email",resultSet.getString("email"));
                jsonUser.put("firstName",resultSet.getString("first_name"));
                jsonUser.put("lastName",resultSet.getString("last_name"));
                jsonUser.put("phoneNumber",resultSet.getString("phone_number"));
                JSONArray jsonAddressesArray = new JSONArray();
                try{
                    Statement statement2 = connection.createStatement();
                    ResultSet resultSet2 = statement2.executeQuery(querySql2);

                    while (resultSet2.next()){
                        JSONObject jsonAddresses = new JSONObject();
                        jsonAddresses.put("id",resultSet2.getInt("id"));
                        jsonAddresses.put("customer",resultSet2.getString("customer"));
                        jsonAddresses.put("billing_period",resultSet2.getString("billing_period "));
                        jsonAddresses.put("billing_period_unit",resultSet2.getString("billing_period_unit"));
                        jsonAddresses.put("total_due",resultSet2.getString("total_due"));
                        jsonAddresses.put("activated_at",resultSet2.getString("activated_at"));
                        jsonAddresses.put("current_term_start",resultSet2.getString("current_term_start"));
                        jsonAddresses.put("current_term_end",resultSet2.getString("current_term_end"));
                        jsonAddresses.put("status",resultSet2.getString("status"));
                        jsonAddressesArray.put(jsonAddresses);
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
                jsonUser.put("subscription",jsonAddressesArray);
                jsonArray.put(jsonUser);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }


    public JSONArray getCustomerCard(int customerId){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM customers WHERE id=" + customerId;
        String querySql2 = "SELECT * FROM cards WHERE customer=" + customerId;

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);

            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("email",resultSet.getString("email"));
                jsonUser.put("firstName",resultSet.getString("first_name"));
                jsonUser.put("lastName",resultSet.getString("last_name"));
                jsonUser.put("phoneNumber",resultSet.getString("phone_number"));
                JSONArray jsonAddressesArray = new JSONArray();
                try{
                    Statement statement2 = connection.createStatement();
                    ResultSet resultSet2 = statement2.executeQuery(querySql2);

                    while (resultSet2.next()){
                        JSONObject jsonAddresses = new JSONObject();
                        jsonAddresses.put("id",resultSet2.getInt("id"));
                        jsonAddresses.put("customer",resultSet2.getString("customer"));
                        jsonAddresses.put("card_type",resultSet2.getString("card_type"));
                        jsonAddresses.put("masked_number",resultSet2.getString("masked_number"));
                        jsonAddresses.put("expiry_month",resultSet2.getString("expiry_month"));
                        jsonAddresses.put("expiry_year",resultSet2.getString("expiry_year"));
                        jsonAddresses.put("status",resultSet2.getString("status"));
                        jsonAddresses.put("is_primary",resultSet2.getString("is_primary"));
                        jsonAddressesArray.put(jsonAddresses);
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
                jsonUser.put("cards",jsonAddressesArray);
                jsonArray.put(jsonUser);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }

    public JSONArray getUserFilter(String field, String cond, int val){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM users WHERE " + field + cond + "'" + val + "'";

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);
            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("email",resultSet.getString("email"));
                jsonUser.put("firstName",resultSet.getString("first_name"));
                jsonUser.put("lastName",resultSet.getString("last_name"));
                jsonUser.put("phoneNumber",resultSet.getString("phone_number"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }

    public boolean deleteCustomer(int idUser){
        int rowDeleted = 0;
        PreparedStatement statement = null;
        String querySql = "DELETE FROM customers WHERE id="+idUser;
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
    public boolean deleteCustomerCards(int idUser,int idCard){
        int rowDeleted = 0;
        PreparedStatement statement = null;
        String querySql = "DELETE FROM customers WHERE customer="+idUser+"AND id"+idCard;
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
    public boolean addCustomer(JSONObject requestBodyJson){
        String email = requestBodyJson.optString("email");
        String firstName = requestBodyJson.optString("firstName");
        String lastName = requestBodyJson.optString("lastName");
        String phoneNumber = requestBodyJson.optString("phoneNumber");
        PreparedStatement statement = null;
        int insertedRow = 0;

        String querySql = "INSERT INTO customers(email,first_name,last_name,phone_number) VALUES(?,?,?,?)";
        try {
            statement = databaseManager
                    .getConnection()
                    .prepareStatement(querySql);
            statement.setString(3,email);
            statement.setString(1,firstName);
            statement.setString(2,lastName);
            statement.setString(4,phoneNumber);

            insertedRow = statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return insertedRow > 0;
    }
    public boolean updateCustomer(int idUser, JSONObject requestBodyJson){
        String email = requestBodyJson.optString("email");
        String firstName = requestBodyJson.optString("firstName");
        String lastName = requestBodyJson.optString("lastName");
        String phoneNumber = requestBodyJson.optString("phoneNumber");
        PreparedStatement statement = null;
        int insertedRow = 0;
        String querySql = "UPDATE customers SET email=?, first_name=?, last_name=?, phone_number=?  WHERE id="+idUser;
        try {

            statement = databaseManager
                    .getConnection()
                    .prepareStatement(querySql);
            statement.setString(1,email);
            statement.setString(2,firstName);
            statement.setString(3,lastName);
            statement.setString(4,phoneNumber);
            System.out.println("start to add");
            insertedRow = statement.executeUpdate();
            System.out.println("controller update success");

        }catch (SQLException e){
            e.printStackTrace();
        }


        return insertedRow > 0;
    }

}
