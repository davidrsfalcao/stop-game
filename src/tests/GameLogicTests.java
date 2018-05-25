package tests;

import database.Dictionary;
import game.GameLogic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameLogicTests {

    @Test
    public void test_word_score(){

        GameLogic logic = new GameLogic();
        String animal = "Lemur";
        char letter = 'l';
        String[] other_animals1 = {"Koala","Panda","Bear"};
        String[] other_animals2 = {"Koala","Panda","Lark"};
        String[] other_animals3 = {"Koala","Lemur","Lark"};


        assertEquals(true, logic.word_is_valid(animal,Dictionary.Category.animal, letter));
        assertEquals(20, logic.word_score(animal,Dictionary.Category.animal, other_animals1,letter));
        assertEquals(10, logic.word_score(animal,Dictionary.Category.animal, other_animals2,letter));
        assertEquals(5, logic.word_score(animal,Dictionary.Category.animal, other_animals3,letter));
        assertEquals(0, logic.word_score("Koala",Dictionary.Category.animal, other_animals3,letter));
        
    }

    //@Test
    //public void

}
