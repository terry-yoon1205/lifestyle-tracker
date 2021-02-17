package model;

import exceptions.DoesNotExistException;
import model.food.Food;
import model.food.FoodLog;
import model.food.FoodLogEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.food.Meal.*;
import static org.junit.jupiter.api.Assertions.*;

public class FoodLogTest extends CollectionTest<FoodLog, FoodLogEntry, Food> {

    @BeforeEach
    public void setUp() {
        c = new FoodLog();
        e1 = new FoodLogEntry("2020-01-01");
        e2 = new FoodLogEntry("2020-01-02");
        su1 = new Food("Test1", LUNCH, 600);
        su2 = new Food("Test2", BREAKFAST, 300);
        su3 = new Food("Test3", DINNER, 400);
        su4 = new Food("Test4", SNACKS, 800);

        e1.add(su1);
        e1.add(su2);
        e2.add(su3);
        e2.add(su4);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, c.getEntries().size());
    }

    @Test
    @Override
    public void testAddEntry() {
        c.add(e1);
        assertEquals(1, c.getEntries().size());
        assertEquals(e1, c.getEntries().get(0));

        c.add(e2);
        assertEquals(2, c.getEntries().size());
        assertEquals(e2, c.getEntries().get(0));
    }

    @Test
    public void testGetCollectionFormatted() {
        assertEquals("", c.toString());

        c.add(e1);
        c.add(e2);

        assertEquals("2020-01-02\n2020-01-01\n", c.toString());
    }

    @Test
    public void testSearchEntryDoesNotExist() {
        c.add(e1);

        try {
            c.search("2020-01-02");
            fail();
        } catch (DoesNotExistException e) {
            // pass
        }
    }

    @Test
    public void testSearchEntryTypical() {
        c.add(e1);
        c.add(e2);

        try {
            assertEquals(e1, c.search("2020-01-01"));
            assertEquals(2, c.getEntries().size());
        } catch (DoesNotExistException e) {
            fail();
        }
    }

    @Test
    public void testLatestLog() {
        FoodLogEntry fe3 = new FoodLogEntry("2020-01-03");

        c.add(e1);
        c.add(e2);
        c.add(fe3);

        assertEquals(fe3, c.latestLog());
    }
}