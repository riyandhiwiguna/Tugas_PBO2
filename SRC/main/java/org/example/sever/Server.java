package org.example.server;

import java.io.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.Controller.CardsController;
import org.example.Controller.CustomersController;
import org.example.Controller.SubscriptionsController;
import org.example.Controller.ItemsController;


import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.json.JSONObject;


public class Server{
    private static final int PORT = 9119;
    private CardsController cardsController;
    private CustomersController customersController;
    private ItemsController itemsController;
    private SubscriptionsController subscriptionsController;

    public Server(){
        DatabaseManager databaseManager = new DatabaseManager("database/DatabaseKeren.db");
        customersController = new CustomersController(databaseManager);
        itemsController = new ItemsController(databaseManager);
        subscriptionsController = new SubscriptionsController(databaseManager);
        cardsController = new CardsController(databaseManager);
    }
    public void httpConnection() throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress("localhost",PORT), 0);
        httpServer.createContext("/",new Handler());
        httpServer.setExecutor(Executors.newSingleThreadExecutor());
        httpServer.start();
    }
    private class Handler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
//            String path = exchange.getRequestURI().getPath();
            String response = "";
            int statusCode = 404;
            JSONObject requestBodyJson;

            String[] path = exchange.getRequestURI()
                    .getPath()
                    .split("/");
            String query = exchange.getRequestURI()
                    .getQuery();


            if (method.equals("GET")){
                GetHandler getHandler = new GetHandler(path,query);
                getHandler.main();
                System.out.println("get method");

                response = getHandler.getResponse();
                statusCode = getHandler.getStatusCode();
            } else if (method.equals("POST")) {
                System.out.println("post method");
                requestBodyJson = parseRequestBody(exchange.getRequestBody());
                PostHandler postHandler = new PostHandler(path,query,requestBodyJson);

                postHandler.main();
                System.out.println("post ended");

                response = postHandler.getResponse();
                statusCode = postHandler.getStatusCode();
            } else if (method.equals("PUT")) {
                System.out.println(" Put Method");
                requestBodyJson = parseRequestBody(exchange.getRequestBody());
                PutHandler putHandler = new PutHandler(path,query,requestBodyJson);
                putHandler.main();

                response = putHandler.getResponse();
                statusCode = putHandler.getStatusCode();
            } else if (method.equals("DELETE")) {
                DeleteHandler deleteHandler = new DeleteHandler(path,query);
                deleteHandler.main();

                response = deleteHandler.getResponse();
                statusCode = deleteHandler.getStatusCode();
            }else {
                response = "Request Method Invalid";
                statusCode = 400;
            }

            OutputStream outputStream = exchange.getResponseBody();
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(statusCode, response.length());
            outputStream.write(response.getBytes());
            outputStream.flush();
            outputStream.close();
        }
        private JSONObject parseRequestBody(InputStream requestBody) throws IOException {
            byte[] requestBodyBytes = requestBody.readAllBytes();
            String requestBodyString = new String(requestBodyBytes);
            return new JSONObject(requestBodyString);
        }

    }
    public class GetHandler {
        private String[] path;
        private String query;
        private String response;
        private int statusCode;

        private int queryValue;
        private String queryCondition;
        private String queryField;

        GetHandler(String[] path, String query) {
            this.path = path;
            this.query = query;
        }


        public void main() {
            if (this.path[1].equals("customers")) {
                customersHandle();
            } else if (this.path[1].equals("items")) {
                itemsHandle();
            } else if (this.path[1].equals("subscriptions")) {
                subscriptionHande();
            } else if (this.path[1].equals("cards")) {
                cardHande();
                this.response = "Invalid Path";
                this.statusCode = 400;
            }
        }

        private void cardsController() {
        }

        public String getResponse() {
            return this.response;
        }

        public int getStatusCode() {
            return this.statusCode;
        }

        private void queryCheck(String query) {
            String[] querys = query.split("&");
            for (String i : querys) {
                if (i.contains("field")) {
                    this.queryField = i.substring(i.lastIndexOf("=") + 1);
                } else if (i.contains("val")) {
                    this.queryValue = Integer.parseInt(i.substring(i.lastIndexOf("=") + 1));
                } else if (i.contains("cond")) {
                    String cond = i.substring(i.lastIndexOf("=") + 1);
                    if (cond.equals("larger")) {
                        this.queryCondition = ">";
                    } else if (cond.equals("smaller")) {
                        this.queryCondition = "<";
                    } else if (cond.equals("smallerEqual")) {
                        this.queryCondition = "<=";
                    } else if (cond.equals("largerEqual")) {
                        this.queryCondition = ">=";
                    }
                }
            }

        }

        private void customersHandle() {

            if (this.path.length == 4) {
                //path : /customers/id/something
                if (path[3].equals("customers")) {
                    this.response = customersController
                            .getCustomer(Integer.parseInt(path[2]))
                            .toString();
                } else if (path[3].equals("cards")) {
                    this.response = customersController
                            .getCustomerCard(Integer.parseInt(path[2]))
                            .toString();
                } else if (path[3].equals("subscriptions")) {
                    this.response = customersController
                            .getCustomerSubscription(Integer.parseInt(path[2]))
                            .toString();
                }
            } else if (this.path.length == 3) {
                // path : /users/id
                this.response = customersController
                        .getCustomer(Integer.parseInt(path[2]))
                        .toString();

            } else if (this.path.length == 2) {
                // path : /users
                if (query == null) {
                    this.response = customersController
                            .getCustomers()
                            .toString();
                } else if (query.contains("field")) {
                    queryCheck(query);
                    response = customersController.getUserFilter(this.queryField, this.queryCondition, this.queryValue)
                            .toString();

                }
            }

            if (this.response.equals("[]")) {
                this.response = "Data Null";
                this.statusCode = 400;
            } else {
                this.statusCode = 200;
            }
        }

        private void itemsHandle() {
            if (this.path.length == 3) {
                // path  : /products/id
                this.response = itemsController
                        .getItem(Integer.parseInt(path[2]))
                        .toString();
                this.statusCode = 200;

            } else if (this.path.length == 2) {
                // path : /items
                System.out.println("/items");
                if (query == null) {
                    this.response = itemsController
                            .getItems()
                            .toString();
                } else if (query.contains("field")) {
                    queryCheck(query);
                    response = itemsController.getProductFilter(this.queryField, this.queryCondition, this.queryValue)
                            .toString();
                }

                if (this.response.equals("[]")) {
                    this.response = "Data Null";
                    this.statusCode = 400;
                } else {
                    this.statusCode = 200;
                }
            }
        }
        private void subscriptionHande(){
            if (this.path.length == 3){
                // path  : /subscription/id
                this.response = subscriptionsController
                        .getSubscriptions(Integer.parseInt(path[2]))
                        .toString();

            }else if(this.path.length == 2){
                // path : /orders
                this.response = subscriptionsController
                        .getSubscriptions()
                        .toString();
            }


            if (this.response.equals("[]")){
                this.response = "Data Null";
                this.statusCode = 400;
            }else {
                this.statusCode = 200;
            }
        }
        private void cardHande(){
            if (this.path.length == 3){
                // path  : /card/id
                this.response = cardsController
                        .getCard(Integer.parseInt(path[2]))
                        .toString();

            }else if(this.path.length == 2){
                // path : /orders
                this.response = cardsController
                        .getCard()
                        .toString();
            }


            if (this.response.equals("[]")){
                this.response = "Data Null";
                this.statusCode = 400;
            }else {
                this.statusCode = 200;
            }
        }


//        public String getResponse(){
//            return this.response;
//        }
//        public int getStatusCode(){return this.statusCode;}

            //======== GET HANDLER END==============//





    }
    public class PostHandler {
        private String[] path;
        private String query;
        private String response;
        private int statusCode;

        private JSONObject requestBodyJson;

        PostHandler(String[] path, String query, JSONObject requestBodyJson) {
            this.path = path;
            this.query = query;
            this.requestBodyJson = requestBodyJson;
        }

        public void main() {
            System.out.println("post handler hit");
            if (this.path[1].equals("customers")) {
                System.out.println("customer hit");
                customerdHandle();
            } else if (this.path[1].equals("items")) {
                itemsController();
            } else if (this.path[1].equals("orders")) {
//                ordersHandle();
            } else {
                System.out.println(this.path[1]);
                this.response = "Path Invalid";
                this.statusCode = 400;
            }
        }

        private void customerdHandle() {
            // path : /Customer
            boolean done = customersController.addCustomer(requestBodyJson);
            if (done) {
                this.response = "Data Added";
                this.statusCode = 200;
            } else {
                this.response = "Data Invalid";
                this.statusCode = 400;
            }
        }

        private void itemsController(){
            // path : /items
            boolean done = itemsController.addItem(requestBodyJson);
            if (done){
                this.response = "Data Added";
                this.statusCode = 200;
            }else {
                this.response = "Data Invalid";
                this.statusCode = 400;
            }
        }
//        private void ordersHandle(){
//            // path : /orders
//            boolean done = ordersController.addOrder(requestBodyJson);
//            if (done){
//                this.response = "Data Added";
//                this.statusCode = 200;
//            }else {
//                this.response = "Data Invalid";
//                this.statusCode = 400;
//            }
//        }

        public String getResponse() {
            return this.response;
        }

        public int getStatusCode() {
            return this.statusCode;
        }
        // ========== POST HANDLER END =========== //
    }
    public class PutHandler {
        private String[] path;
        private String query;
        private String response;
        private int statusCode;

        private JSONObject requestBodyJson;

        PutHandler(String[] path, String query, JSONObject requestBodyJson) {
            this.path = path;
            this.query = query;
            this.requestBodyJson = requestBodyJson;
        }

        public void main() {
            if (this.path[1].equals("customers")) {
                System.out.println("customers api hit");
                customerHandle();
            } else if (this.path[1].equals("items")) {
                itemHandle();
            } else if (this.path[1].equals("orders")) {
                ordersHandle();
            } else {
                this.response = "Path Invalid";
                this.statusCode = 400;
            }
        }

        private void customerHandle() {
            // path :  PUT /customer/id
            boolean done = customersController.updateCustomer(Integer.parseInt(path[2]), requestBodyJson);
            System.out.println("customer api hit handle");
            if (done) {
                System.out.println("success");
                this.response = "Data Updated";
                this.statusCode = 200;
            } else {
                System.out.println("failed");
                this.response = "Data Invalid";
                this.statusCode = 400;
            }
            System.out.println("done");
        }

        private void itemHandle() {
            // path : /products/id
//            boolean done = true;
            boolean done = itemsController.updateItems(Integer.parseInt(path[2]),requestBodyJson);
            if (done) {
                this.response = "Data Updated";
                this.statusCode = 200;
            } else {
                this.response = "Data Invalid";
                this.statusCode = 400;
            }
        }

        private void ordersHandle() {
            // path : /orders/id
            boolean done = true;
//            boolean done = ordersController.updateOrder(Integer.parseInt(path[2]),requestBodyJson);
            if (done) {
                this.response = "Data Updated";
                this.statusCode = 200;
            } else {
                this.response = "Data Invalid";
                this.statusCode = 400;
            }
        }

        public String getResponse() {
            return this.response;
        }

        public int getStatusCode() {
            return this.statusCode;
        }
        // ========== PUT HANDLER END =========== //
    }
    public class DeleteHandler{
        private String[] path;
        private String query;
        private String response;
        private int statusCode;

        DeleteHandler(String[] path,String query){
            this.path = path;
            this.query = query;
        }
        public void main() {
            if (this.path[1].equals("customers")){
                System.out.println("delete customer");
                customersHandle();
            } else if (this.path[1].equals("items")) {
                itemsHandle();
            } else if (this.path[1].equals("orders")) {
                ordersHandle();
            }else {
                this.response = "Request Method Invalid";
                this.statusCode = 400;
            }
        }
        private void customersHandle(){
            // path : /customer/id

            if (this.path.length == 5){
                boolean done = customersController.deleteCustomerCards(Integer.parseInt(path[2]),Integer.parseInt(path[4]));
                if (done){
                    this.response = "Data Deleted";
                    this.statusCode = 200;
                }else {
                    this.response = "Data Not Found";
                    this.statusCode = 400;
                }
            } else {
                boolean done = customersController.deleteCustomer(Integer.parseInt(path[2]));
                if (done){
                    this.response = "Data Deleted";
                    this.statusCode = 200;
                }else {
                    this.response = "Data Not Found";
                    this.statusCode = 400;
                }
            }
        }

        private void itemsHandle(){
            // path : /items/id
//            boolean done = true;
            boolean done = itemsController.deleteItems(Integer.parseInt(path[2]));
            if (done){
                this.response = "Data Deleted";
                this.statusCode = 200;
            }else {
                this.response = "Data Not Found";
                this.statusCode = 400;
            }
        }
        private void ordersHandle(){
            // path : /products/id
            boolean done = true;
//            boolean done = ordersController.deleteOrder(Integer.parseInt(path[2]));
            if (done){
                this.response = "Data Deleted";
                this.statusCode = 200;
            }else {
                this.response = "Data Not Found";
                this.statusCode = 400;
            }
        }

        public String getResponse(){
            return this.response;
        }
        public int getStatusCode(){return this.statusCode;}
        // ========== DELETE HANDLER END =========== //
    }
}
