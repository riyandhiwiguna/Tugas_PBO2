package org.example.Controller;

import org.example.server.DatabaseManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class SubscriptionsController {

    private DatabaseManager databaseManager;

    public SubscriptionsController(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }


    public JSONArray getSubscriptions(){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM subscriptions";

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);

            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("customer",resultSet.getInt("customer"));
                jsonUser.put("billing_period",resultSet.getInt("billing_period"));
                jsonUser.put("billing_period_unit",resultSet.getString("billing_period_unit"));
                jsonUser.put("total_due",resultSet.getInt("total_due"));
                jsonUser.put("activated_at",resultSet.getString("activated_at"));
                jsonUser.put("current_term_start",resultSet.getString("current_term_start"));
                jsonUser.put("current_term_end",resultSet.getString("current_term_end"));
                jsonUser.put("status",resultSet.getString("status"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }
    public JSONArray getSubscriptions(int idSubscriptions){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM subscriptions WHERE id=" + idSubscriptions;

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);

            while (resultSet.next()){
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("id",resultSet.getInt("id"));
                jsonUser.put("customer",resultSet.getInt("customer"));
                jsonUser.put("billing_period",resultSet.getInt("billing_period"));
                jsonUser.put("billing_period_unit",resultSet.getInt("billing_period_unit"));
                jsonUser.put("total_due",resultSet.getInt("total_due"));
                jsonUser.put("activated_at",resultSet.getInt("activated_at"));
                jsonUser.put("current_term_start",resultSet.getInt("current_term_start"));
                jsonUser.put("current_term_end",resultSet.getInt("current_term_end"));
                jsonUser.put("status",resultSet.getInt("status"));
                jsonArray.put(jsonUser);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }
    public JSONArray getOrders(int idSubscriptions){
        JSONArray jsonArray = new JSONArray();
        String querySql = "SELECT * FROM subscriptions " +
                "INNER JOIN customers " +
                "ON orders.buyer=customers.id WHERE orders.id=" + idSubscriptions;
        String querySqlDetail = "SELECT * FROM orders_details " +
                "INNER JOIN items " +
                "ON items.id=subscription_details.'subscription' " +
                "WHERE subscriptions_details.'subscription'=" + idSubscriptions;
        String querySqlReview = "SELECT * FROM reviews WHERE reviews.'subscription'="+idSubscriptions;

        try{
            Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);

            while (resultSet.next()){
                JSONObject jsonOrder = new JSONObject();
                jsonOrder.put("id",resultSet.getInt("id"));
                jsonOrder.put("customer",resultSet.getString("customer") + " " + resultSet.getString("last_name"));
                jsonOrder.put("billing_period",resultSet.getString("billing_period"));
                jsonOrder.put("billing_period_unit",resultSet.getString("billing_period_unit"));
                jsonOrder.put("total_due",resultSet.getInt("total_due"));
                jsonOrder.put("activated_at",resultSet.getInt("activated_at"));
                jsonOrder.put("current_term_start",resultSet.getInt("current_term_start"));
                jsonOrder.put("current_term_end",resultSet.getInt("current_term_end"));
                jsonOrder.put("status",resultSet.getInt("status"));

                JSONArray jsonDetailArray = new JSONArray();
                try{
                    Statement statementDetail = connection.createStatement();
                    ResultSet resultSetDetail = statementDetail.executeQuery(querySqlDetail);

                    while (resultSetDetail.next()){
                        JSONObject jsonOrderDetail = new JSONObject();
                        jsonOrderDetail.put("item",resultSetDetail.getString("name"));
                        jsonOrderDetail.put("type",resultSetDetail.getInt("type"));
                        jsonOrderDetail.put("price",resultSetDetail.getInt("price"));
                        jsonDetailArray.put(jsonOrderDetail);
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
                jsonOrder.put("Subscriptions_detail", jsonDetailArray);

                JSONArray jsonReviews = new JSONArray();
                try{
                    Statement statementReview = connection.createStatement();
                    ResultSet resultSetReview = statementReview.executeQuery(querySqlReview);

                    while (resultSetReview.next()){
                        JSONObject jsonReview = new JSONObject();
                        jsonReview.put("current_term_start",resultSetReview.getInt("current_term_start"));
                        jsonReview.put("description",resultSetReview.getString("description"));
                        jsonReviews.put(jsonReview);
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
                jsonOrder.put("status", jsonReviews);


                //================END OF JSONARRAY==============//
                jsonArray.put(jsonOrder);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jsonArray;
    }


    public boolean deletesubscriptions(int idSubscriptions){

        int rowDeleted = 0;
        PreparedStatement statement = null;
        String querySql = "DELETE FROM orders WHERE id="+idSubscriptions;
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
    public boolean addSubscription(JSONObject requestBodyJson){
        String customer = requestBodyJson.optString("customer");
        String billing_period = requestBodyJson.optString("billing_period");
        String billing_period_unit = requestBodyJson.optString("billing_period_unit");
        int total_due = requestBodyJson.optInt("total_due");
        int activated_at = requestBodyJson.optInt("activated_at");
        int current_term_start = requestBodyJson.optInt("current_term_start");
        int current_term_end = requestBodyJson.optInt("current_term_end");
        int status = requestBodyJson.optInt("status");
        PreparedStatement statement = null;
        int insertedRow = 0;

        String querySql = "INSERT INTO subscription(customer,billing_period,billing_period_unit,total_due,activated_at,current_term_start,current_term_end,status) VALUES(?,?,?,?,?,?,?,?)";
        try {
            statement = databaseManager
                    .getConnection()
                    .prepareStatement(querySql);
            statement.setString(1,customer);
            statement.setString(2,billing_period);
            statement.setString(3,billing_period_unit);
            statement.setInt(4,total_due);
            statement.setInt(5,activated_at);
            statement.setInt(6,current_term_start);
            statement.setInt(7,current_term_end);
            statement.setInt(8,status);

            insertedRow = statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return insertedRow > 0;
    }
}
