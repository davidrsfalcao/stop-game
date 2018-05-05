package com.game.stop.game;

import java.util.HashMap;

/* Test Class */
public class Database {

    /* HasMap<word, category> */
    private HashMap<String, String> words;

    public Database() {
        words = new HashMap<String, String>();
        set_up_words();
    }

    private void set_up_words(){
        words.put("DAVID", Category.names);
        words.put("DIOGO", Category.names);
        words.put("FILIPE", Category.names);
        words.put("TIAGO", Category.names);

    }

    public HashMap<String, String> getWords() {
        return words;
    }


}
