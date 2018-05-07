package com.game.stop;

import com.game.stop.game.Category;
import com.game.stop.game.GameLogic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {

    @Test
    public void test_valid_word(){
        GameLogic logic = new GameLogic();

        logic.word_is_valid("david", Category.name);
        assertEquals(true, logic.word_is_valid("david", Category.name));

    }

    @Test
    public void test_score(){
        GameLogic logic = new GameLogic();

        String cat = Category.name;
        String word = "david";
        String[] other_words1 = {"pim", "pam", "pum"};
        String[] other_words2 = {"diogo", "Filipe", "TIAGO"};
        String[] other_words3 = {"david", "Filipe", "TIAGO"};


        assertEquals(20, logic.word_score(word, cat, other_words1));
        assertEquals(10, logic.word_score(word, cat, other_words2));
        assertEquals(5, logic.word_score(word, cat, other_words3));
        assertEquals(0, logic.word_score("random", cat, other_words3));
        
    }
}
