package com.app.chatapp.rest;

import java.sql.*;

public class Message {
    private String sender;
    private String recipient;
    private String message;

    public Message (ResultSet rset) throws SQLException {
        sender = rset.getString ("sender");
        recipient = rset.getString ("recipient");
        message = rset.getString ("message");
    }

    public String getMessage() {return message;}
    public String getSender() {return sender;}
    public String getRecipient() {return recipient;}
}
