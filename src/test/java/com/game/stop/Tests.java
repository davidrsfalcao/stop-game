package com.game.stop;

import com.game.stop.game.Category;
import com.game.stop.game.GameLogic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {

    @Test
    public void test_valid_word(){
        GameLogic logic = new GameLogic();

        boolean res = logic.word_is_valid("david", Category.name, 'd');
        assertEquals(true, res);

    }

    @Test
    public void test_score(){
        GameLogic logic = new GameLogic();

        String cat = Category.name;
        String word = "david";
        String[] other_words1 = {"dim", "dam", "dum"};
        String[] other_words2 = {"diogo", "Filipe", "TIAGO"};
        String[] other_words3 = {"david", "Filipe", "TIAGO"};


        assertEquals(20, logic.word_score(word, cat, other_words1, 'd'));
        assertEquals(10, logic.word_score(word, cat, other_words2, 'd'));
        assertEquals(5, logic.word_score(word, cat, other_words3, 'd'));
        assertEquals(0, logic.word_score(word, cat, other_words3, 'a'));


    }
}
