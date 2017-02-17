package com.kingschan.fastquery;

import com.kingschan.fastquery.sql.AbstractConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DefaultConnection implements AbstractConnection {

    public Connection getConnection() throws Exception {
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String userName = "root";
        String userPass = "kingschan";
        String driverName ="jdbc:mysql://localhost:3306/blog";
        Connection cn = null;
        try {
            cn = DriverManager.getConnection(driverName, userName, userPass );
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return cn;
    }

}
