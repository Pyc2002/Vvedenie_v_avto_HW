package HW_2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompositeTest {
    ProductGroup engineParts;
    ProductGroup carBody;
    Product piston;
    Product valve;
    Product cylinder;
    Product windscreen;
    Product bumper;
    Product bonnet;

    public CompositeTest() {
        piston = new Product("Поршень", 2500);
        valve = new Product("Клапан", 1500);
        cylinder = new Product("Цилиндр", 6000);

        windscreen = new Product("Лобовое стекло", 2000);
        bumper = new Product("Бампер", 9500);
        bonnet = new Product("Капот", 13500);

        engineParts = new ProductGroup();
        carBody = new ProductGroup();
    }

    @BeforeEach
    void setUp(){
        engineParts.addItem(piston);
        engineParts.addItem(valve);
        engineParts.addItem(cylinder);

        carBody.addItem(bumper);
        carBody.addItem(bonnet);
        carBody.addItem(windscreen);
    }

    @Test
    void enginePartsTotalPriceTest(){
        double totalPrice = engineParts.getPrice();

        assertEquals(10000, totalPrice);
    }

    @Test
    void allTotalPriceTest(){
        double totalPrice = engineParts.getPrice() + carBody.getPrice();

        assertEquals(35000, totalPrice);
    }
    @Test
    void mixedTotalPrice(){
        double totalPrice = engineParts.getPrice() + bumper.getPrice();

        assertEquals(19500, totalPrice);

    }

    @Test
    void removeTest(){
        engineParts.removeItem(valve);

        assertEquals(2, engineParts.getSize());
    }

}
