package game;

import database.Dictionary;
import database.Dictionary.Category;

import java.util.ArrayList;
import java.util.Random;

public class GameLogic {

    private ArrayList<Character> letters = new ArrayList<Character>();

    public GameLogic(){

        /* force dictionary instance */
        Dictionary.getInstance();
        init_letters();

    }

    private void init_letters(){
        String tmp = "abcdefghijklmnopqrstuvwxyz";

        for(int i = 0; i<tmp.length(); i++){
            letters.add(tmp.charAt(i));
        }

    }

    public boolean word_is_valid(String word, Category category, char letter){
        if(letter != word.toLowerCase().substring(0,1).charAt(0))
            return false;

        return Dictionary.getInstance().checkCategory(word.toLowerCase(), category, letter);
    }

    public int word_score(String word, Category category, String[] other_words, char letter){

        if(!word_is_valid(word, category, letter))
            return 0;


       ArrayList<String> valid_words = new ArrayList<String>();

        for( String wd : other_words){

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
