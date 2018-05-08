package com.game.stop.database;

import com.game.stop.game.Category;

import java.util.HashMap;
import java.sql.*;

/* Test Class */
public class Database {

    /* HasMap<word, category> */
    private HashMap<String, String> words;

    Connection conn = null;


    public Database() {
        words = new HashMap<String, String>();
        try {
            set_up_connection_db();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            load_words();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void set_up_connection_db() throws Exception{
        Class.forName("org.sqlite.JDBC");
        String db_url = "jdbc:sqlite:" + getClass().getClassLoader().getResource("database/database.db").getFile();

        conn = DriverManager.getConnection(db_url);

    }

    private void load_words() throws Exception{
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("Select * From Words;");

        while (rs.next()) {
            String word = rs.getString("word").toLowerCase();
            String category = rs.getString("category").toLowerCase();
            words.put(word, category);

        }
        rs.close();
        conn.close();

    }

    public HashMap<String, String> getWords() {
        return words;
    }


}
