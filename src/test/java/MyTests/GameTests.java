package MyTests;

import org.example.home.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {
    private List<Door> listDoors;


    @BeforeEach
    void setUp(){
        listDoors = new ArrayList<>();
        listDoors.add(new Door(false));
        listDoors.add(new Door(false));
        listDoors.add(new Door(true));
    }
    @Test
    void removeDoorTest(){
        Game game = new Game(new Player("Player", true),listDoors);

        Door doorNumber3beforeGame = listDoors.get(2);
        Door result = game.round(1);

        assertEquals(2, listDoors.size());
        assertEquals(listDoors.get(1), result);
        assertEquals(listDoors.get(1), doorNumber3beforeGame);
    }

    @Test
    void riskTrueWinTest(){
        Game game = new Game(new Player("Player", true),listDoors);

        Door door = game.round(1);

        assertTrue(door.isPrize());
    }

    @Test
    void riskFalseWinTest(){
        Game game = new Game(new Player("Player", false),listDoors);

        Door door = game.round(2);

        assertTrue(door.isPrize());
    }

    @Test
    void riskFalseLoseTest(){
        Game game = new Game(new Player("Player", false),listDoors);

        Door door = game.round(1);

        assertFalse(door.isPrize());
    }

    @Test
    void riskTrueLoseTest(){
        Game game = new Game(new Player("Player", true),listDoors);

        Door door = game.round(2);

        assertFalse(door.isPrize());
    }
}
