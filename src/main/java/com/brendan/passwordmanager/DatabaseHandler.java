package com.brendan.passwordmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdk.jfr.Description;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;


public class DatabaseHandler {
    private static final String DB_url = "jdbc:derby:database/passwordmanager;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;
    public static DatabaseHandler handler;

    // Define your secret key and salt (keep these secure and don't hardcode in production)
    public static String secretKey = "MySecretKey";
    public static String salt = "MySalt";


    public DatabaseHandler() {
        createConnection();
        createUserTable();
        createPasswordTable();
    }

    public static DatabaseHandler getHandler() {
        if(handler == null){
            handler = new DatabaseHandler();
            return handler;
        }else{
            return handler;
        }
    }


    /**
     * Create a table that stores user data.
     */
    private void createUserTable() {
        String TABLE_NAME = "USERTABLE";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dmn = conn.getMetaData();
            ResultSet tables = dmn.getTables(null, null, TABLE_NAME, null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " exists");
            } else {
                String statement = "CREATE TABLE " + TABLE_NAME + " ("
                        + "ID varchar(50) primary key, \n"
                        + "Password varchar(50))";
                System.out.println(statement);
                stmt.execute(statement);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * create table for passwords
     */
    private void createPasswordTable() {
        String TABLE_NAME = "PASSWORDTABLE";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dmn = conn.getMetaData();
            ResultSet tables = dmn.getTables(null, null, TABLE_NAME, null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " exists");
            } else {
                String statement = "CREATE TABLE " + TABLE_NAME + " ("
                        + "ID Integer primary key generated always as identity, \n"
                        + "OWNER varchar(50), \n"
                        + "NAME varchar(50), \n"
                        + "USERID varchar(30), \n"
                        + "PASSWORD varchar(60), \n"
                        + "DESCRIPTION varchar(50))";
                System.out.println(statement);
                stmt.execute(statement);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Connect to Database
     */
    private void createConnection() {
        try {
            //Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(DB_url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * insert a user to the database with given id and password
     * @param ID
     * @param Password
     * @return
     */
    public boolean addUser(String ID, String Password) {

        //String encryptPass = encryptPassword(Password);

        String insertSQL = "INSERT INTO USERTABLE VALUES (" +
                "'" + ID + "'," + "'" + Password + "'" + ")";

        try {
            stmt = conn.createStatement();
            stmt.execute(insertSQL);
            return true;

        } catch (SQLException throwables) {
            System.out.println(throwables);
            System.out.println("Did not enter data");
        }
        return false;
    }

    public boolean addPassword(String owner, String name, String userId, String password, String description) {

        String encryptPass = Encryption.encrypt(password, secretKey, salt);

        String insertSQL = "INSERT INTO PASSWORDTABLE VALUES (DEFAULT, " +
                "'" + owner + "'," + "'" + name + "'," + "'" + userId + "'," + "'" + encryptPass + "'," + "'" + description + "'" + ")";


        try {
            stmt = conn.createStatement();
            stmt.execute(insertSQL);
            return true;

        } catch (SQLException throwables) {
            System.out.println(throwables);
            System.out.println("Did not enter data");
        }
        return false;
    }

    public boolean savePassword(Integer id, String name, String userId, String password, String description) {

        String encryptPass = Encryption.encrypt(password, secretKey, salt);

        String updateSQL = "UPDATE PASSWORDTABLE SET " +
                "NAME = '" + name + "'," +
                "USERID = '" + userId + "'," +
                "PASSWORD = '" + encryptPass + "'," +
                "DESCRIPTION = '" + description + "'" +
                " WHERE ID = " + id;

        System.out.println(updateSQL);

        try {
            stmt = conn.createStatement();
            stmt.execute(updateSQL);
            return true;

        } catch (SQLException throwables) {
            System.out.println(throwables);
            System.out.println("Did not enter data");
        }
        return false;
    }


    public boolean deletePassword(Integer id) {

        String deleteSQL = "DELETE FROM PASSWORDTABLE WHERE ID = " + id;

        try {
            stmt = conn.createStatement();
            stmt.execute(deleteSQL);
            return true;

        } catch (SQLException throwables) {
            System.out.println(throwables);
        }
        return false;
    }


    /**
     * CHeck if user id and password exist
     * @param ID
     * @param Password
     * @return
     * @throws SQLException
     */
    public boolean checkLogin(String ID, String Password) throws SQLException {
        ResultSet resultSet;
        String query = "SELECT * FROM USERTABLE WHERE ID = '" + ID + "' AND PASSWORD = '" + Password + "'";
        try{
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        int size = 0;
        while (resultSet.next()) {
            size++;
        }
        if (size == 0) {
            return false;
        } else {
           return true;
        }
    }
    public ObservableList<Passwords> getPasswords(String currentUser) throws SQLException {
        //List<Passwords> myPasswords = new LinkedList<Passwords>();
        ObservableList<Passwords> myPasswords = FXCollections.observableArrayList();
        ResultSet resultSet;
        String query = "SELECT * FROM PASSWORDTABLE WHERE OWNER = '" + currentUser + "'";
        try{
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

        while (resultSet.next()) {
            String id = resultSet.getString("ID");
            String owner = resultSet.getString("OWNER");
            String name = resultSet.getString("NAME");
            String userid= resultSet.getString("USERID");
            String password = resultSet.getString("PASSWORD");
            String description = resultSet.getString("DESCRIPTION");
            String decryptPass = Encryption.decrypt(password, secretKey, salt);

            myPasswords.add(new Passwords(id, owner, name, userid, decryptPass, description));
        }

        return myPasswords;
    }
}