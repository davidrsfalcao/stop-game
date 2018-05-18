package game;

import database.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameLogic {

    private Database db;

    private ArrayList<Character> letters = new ArrayList<Character>();

    public GameLogic(){

        db = new Database();
        init_letters();

    }

    public Database getDb(){
        return db;
    }

    private void init_letters(){
        String tmp = "abcdefghijklmnopqrstuvwxyz";

        for(int i = 0; i<tmp.length(); i++){
            letters.add(tmp.charAt(i));
        }

    }

    public boolean word_is_valid(String word, String category, char letter){

        HashMap<String, String> words = db.getWords();

        if(words.containsKey(word.toLowerCase())){

            String cat = words.get(word.toLowerCase());
            char first_letter = word.toLowerCase().substring(0, 1).charAt(0);;

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

    public ArrayList<Character> getLetters(){
        return letters;
    }

    public char choose_letter(){

        Random gerador = new Random();
        int letters_left = letters.size();
        int index = gerador.nextInt(letters_left);
        char letter = letters.get(index);
        letters.remove(index);

        return letter;
    }
}
