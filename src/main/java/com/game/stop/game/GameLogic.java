package com.game.stop.game;

import com.game.stop.database.Database;

import java.util.ArrayList;
import java.util.HashMap;

public class GameLogic {

    private Database db;

    public GameLogic(){

        db = new Database();

    }


    public boolean word_is_valid(String word, String category, char letter){

        HashMap<String, String> words = db.getWords();

        if(words.containsKey(word.toLowerCase())){

            String cat = words.get(word.toLowerCase());
            char first_letter = word.substring(0, 1).charAt(0);;
            

            if(cat.equals(category) && (first_letter ==letter)){
                return true;
            }

        }

        return false;
    }

    public int word_score(String word, String category, String[] other_word, char letter){

        if(!word_is_valid(word, category, letter))
            return 0;


       ArrayList<String> valid_words = new ArrayList<String>();

        for( String wd : other_word){

            if (word_is_valid(wd, category, letter)){
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
