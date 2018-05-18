package database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Users {
    Connection conn = null;

    public Users() {

    }

    private void set_up_connection_db() throws Exception{
        Class.forName("org.sqlite.JDBC");
        String db_url = "jdbc:sqlite:" + getClass().getResource("res/users.db").getFile();

        conn = DriverManager.getConnection(db_url);

    }

    public boolean user_exists(String username){
        try {
            set_up_connection_db();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Statement stat = null;
        try {
            stat = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String query = "Select * from Users Where username = " + '"' + username + '"' + ";";

        ResultSet result = null;
        try {
            result = stat.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(result.next()){
                return true;
            }
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void register_username(String username, String password){
        try {
            set_up_connection_db();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Statement stat = null;
        try {
            stat = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        password = hash_password(password);

        String query = "Insert Into Users (username, password) VALUES ("+ '"'+ username + '"' + ", " + '"' + password + '"' +  ");";

        try {
            stat.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean verify_password(String username, String password){

        try {
            set_up_connection_db();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Statement stat = null;
        try {
            stat = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String query = "Select * from Users Where username = " + '"' + username + '"' + ";";

        ResultSet result = null;
        try {
            result = stat.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String pass = "";
        try {
            pass = result.getString("password");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        password = hash_password(password);

        if(password.equals(pass)){
            return true;
        }
        else return false;
    }


    private String hash_password(String password){

        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;

    }



}
