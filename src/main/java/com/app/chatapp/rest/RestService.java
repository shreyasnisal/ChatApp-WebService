package com.app.chatapp.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.google.gson.*;
import org.apache.commons.dbutils.DbUtils;

@Path("/")
public class RestService {
    @GET
    @Path("/isReady")
    public Response isReady() {
        return Response.status(200).entity("ready").build();
    }

    @POST
    @Path("/addUser")
    public String addUser(@QueryParam("data") String userData) {
        Connection conn = null;
        Statement stmt = null;
        User user;
        ResultSet rset = null;

        try {
            Gson gson = new Gson();
            Class.forName ("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/messagesdb", <MySQL_USERNAME>, <PASSWORD>);
            stmt = conn.createStatement();
            user = gson.fromJson(userData, User.class);
            rset = stmt.executeQuery ("select * from users_table where user = '" + user.getName () + "';");
            if (rset.next ()) {
                return "FAILURE";
            }
            else {
                stmt.executeUpdate("insert into users_table(user, password) values('" + user.getName () + "', '" + user.getPassword () + "');");
                return "SUCCESS";
            }
        }
        catch (Exception e) {
            System.out.println ("Exception: " + e);
        }
        finally {
            DbUtils.closeQuietly(rset);
            DbUtils.closeQuietly(stmt);
            DbUtils.closeQuietly(conn);
        }
        return "FAILURE";
    }

    @GET
    @Path("/validate")
    public String validate (@QueryParam ("data") String userData) {
        Connection conn = null;
        Statement stmt = null;
        User user;
        ResultSet rset = null;

        try {
            Gson gson = new Gson();
            Class.forName ("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/messagesdb", "shreyas", "shreyas");
            stmt = conn.createStatement();
            user = gson.fromJson(userData, User.class);
            rset = stmt.executeQuery ("select * from users_table where user = '" + user.getName () + "';");
            if (rset.next ()) {
                if (user.getPassword().equals(rset.getString("password")))
                    return "SUCCESS";
                else
                    return "FAILURE";
            }
            else {
                return "FAILURE";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            DbUtils.closeQuietly(rset);
            DbUtils.closeQuietly(stmt);
            DbUtils.closeQuietly(conn);
        }
        return "FAILURE";
    }

    @POST
    @Path("/addMessage")
    public String addMessage(@QueryParam("message") String message) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rset;
        ArrayList<String> users = new ArrayList<String>();
        Message msg;


        try {
            Gson gson = new Gson();
            Class.forName ("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/messagesdb", "shreyas", "shreyas");
            stmt = conn.createStatement();
            msg = gson.fromJson(message, Message.class);
            rset = stmt.executeQuery("select user from users_table;");
            while (rset.next ()) {
                users.add(rset.getString("user"));
            }
            if (!isInArray (users, msg.getRecipient())) {
                return "FAILURE";
            }
            stmt.executeUpdate ("insert into messages_table(message, sender, recipient) values('" + msg.getMessage () + "', '" + msg.getSender() + "', '" + msg.getRecipient() + "');");
            return "SUCCESS";
        }
        catch (Exception e) {
            System.out.println ("Exception: " + e);
        }
        finally {
            DbUtils.closeQuietly(stmt);
            DbUtils.closeQuietly(conn);
        }
        return "FAILURE";
    }

    @GET
    @Path("/getConversations")
    public String getConversations(@QueryParam("user") String user) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;
        String jsonString;
        ArrayList<String> conversationUsers = new ArrayList<String>();

        try {
            Gson gson = new Gson();
            Class.forName ("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/messagesdb", "shreyas", "shreyas");
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select * from messages_table where (sender = '" + user + "' OR recipient = '" + user + "');");
            while (rset.next()) {
                if (user.equals(rset.getString ("sender")) && !isInArray(conversationUsers, rset.getString("recipient"))) {
                    conversationUsers.add (rset.getString ("recipient"));
                }
                else if (user.equals(rset.getString ("recipient")) && !isInArray(conversationUsers, rset.getString("sender"))) {
                    conversationUsers.add (rset.getString ("sender"));
                }
            }
            jsonString = gson.toJson (conversationUsers);
            return jsonString;
        }
        catch (Exception e) {
            System.out.println ("Exception: " + e);
        }
        finally {
            DbUtils.closeQuietly(rset);
            DbUtils.closeQuietly(stmt);
            DbUtils.closeQuietly(conn);
        }
        return null;
    }

    @GET
    @Path("/getChat")
    public String getChat(@QueryParam("user") String user, @QueryParam("target") String target) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;
        Message msg;
        String jsonString;
        ArrayList<Message> messages = new ArrayList<Message>();

        try {
            Gson gson = new Gson();
            Class.forName ("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/messagesdb", "shreyas", "shreyas");
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select * from messages_table where ((sender = '" + user + "' AND recipient = '" + target + "') OR (recipient = '" + user + "' AND sender = '" + target + "'));");
            while (rset.next()) {
                msg = new Message (rset);
                messages.add (msg);
            }
            jsonString = gson.toJson (messages);
            return jsonString;
        }
        catch (Exception e) {
            System.out.println ("Exception: " + e);
        }
        finally {
            DbUtils.closeQuietly(rset);
            DbUtils.closeQuietly(stmt);
            DbUtils.closeQuietly(conn);
        }
        return null;
    }

    public boolean isInArray (ArrayList<String> arr, String element) {
        for (int i = 0; i < arr.size(); i++) {
            if (element.equals(arr.get(i)))
                return true;
        }
        return false;
    }
}
