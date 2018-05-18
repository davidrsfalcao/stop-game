package tests;

import database.Database;
import game.GameLogic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameLogicTests {

    @Test
    public void test_database_creation(){

        GameLogic logic = new GameLogic();

        assertEquals(new Database().getWords(), logic.getDb().getWords());

    }

    //@Test
    //public void

}
