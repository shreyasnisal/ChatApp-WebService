package com.app.chatapp.rest;

import java.sql.*;

public class User {
    private String name;
    private String password;

    public User (ResultSet rset) throws SQLException {
        name = rset.getString  ("user");
        password = rset.getString ("password");
    }

    public String getName () {return name;}
    public String getPassword () {return password;}
}
