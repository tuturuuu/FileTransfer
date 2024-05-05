//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tuturuu.socket.Server;

import java.io.PrintStream;
import java.sql.*;

public class DatabaseConnection {
    private Connection conn;

    public DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean authenticateByUsername(String username) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/client", "client", "client");

        PreparedStatement preparedStatement = conn.prepareStatement("select username from Authentications where username=?");
        preparedStatement.setString(1,username);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()){
            return true;
        }

        preparedStatement.close();
        rs.close();
        conn.close();

        return false;
    }

    public boolean authenticate(String password, String username) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/client", "client", "client");

        PreparedStatement preparedStatement = conn.prepareStatement("select username from Authentications where password=? and username=?");
        preparedStatement.setString(1,password);
        preparedStatement.setString(2,username);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()){
            return true;
        }

        preparedStatement.close();
        rs.close();
        conn.close();

        return false;
    }
}
