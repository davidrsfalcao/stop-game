package com.game.stop.game;

import java.util.ArrayList;
import java.util.HashMap;

public class GameLogic {

    private Database db;

    public GameLogic(){

        db = new Database();

    }


    public boolean word_is_valid(String word, String category){

        HashMap<String, String> words = db.getWords();

        if(words.containsKey(word.toUpperCase())){

            String cat = words.get(word.toUpperCase());

            if(cat.equals(category)){
                return true;
            }

        }

        return false;
    }

    public int word_score(String word, String category, String[] other_word){

        if(!word_is_valid(word, category))
            return 0;


       ArrayList<String> valid_words = new ArrayList<String>();

        for( String wd : other_word){

            if (word_is_valid(wd, category)){
                valid_words.add(wd);
            }
        }

        if(valid_words.size() == 0)
            return 20;

        if(valid_words.contains(word))
            return 5;


        return 10;

    }
}
