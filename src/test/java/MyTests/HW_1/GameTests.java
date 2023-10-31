package MyTests.HW_1;

import io.qameta.allure.*;
import org.example.home.HW_1.Door;
import org.example.home.HW_1.Game;
import org.example.home.HW_1.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Epic(value = "Тестирование игры с парадоксом Монти Холла")
@Feature(value = "First HomeWork")
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
    @Description("remove door")
    @Severity(SeverityLevel.NORMAL)
    void removeDoorTest(){
        Game game = new Game(new Player("Player", true),listDoors);

        Door doorNumber3beforeGame = listDoors.get(2);
        Door result = game.round(1);

        assertEquals(2, listDoors.size());
        assertEquals(listDoors.get(1), result);
        assertEquals(listDoors.get(1), doorNumber3beforeGame);
    }

    @Test
    @Description("win with risk Test")
    @Severity(SeverityLevel.NORMAL)
    void riskTrueWinTest(){
        Game game = new Game(new Player("Player", true),listDoors);

        Door door = game.round(1);

        assertTrue(door.isPrize());
    }

    @Test
    @Description("win without risk Test")
    @Severity(SeverityLevel.NORMAL)
    void riskFalseWinTest(){
        Game game = new Game(new Player("Player", false),listDoors);

        Door door = game.round(2);

        assertTrue(door.isPrize());
    }

    @Test
    @Description("lose without risk Test")
    @Severity(SeverityLevel.NORMAL)
    void riskFalseLoseTest(){
        Game game = new Game(new Player("Player", false),listDoors);

        Door door = game.round(1);

        assertFalse(door.isPrize());
    }

    @Test
    @Description("lose with risk Test")
    @Severity(SeverityLevel.NORMAL)
    void riskTrueLoseTest(){
        Game game = new Game(new Player("Player", true),listDoors);

        Door door = game.round(2);

        assertFalse(door.isPrize());
    }
}
