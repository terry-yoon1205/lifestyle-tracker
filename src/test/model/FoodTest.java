package model;

import exceptions.InvalidInputException;
import exceptions.NotInitializedException;
import exceptions.NullInputException;
import model.food.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.food.Meal.*;
import static org.junit.jupiter.api.Assertions.*;

public class FoodTest {
    public Food f1;
    public Food f2;

    @BeforeEach
    public void setUp() {
        f1 = new Food("Test1", BREAKFAST, 600);
        f2 = new Food("Test2", LUNCH, 300, 70, 1, 2);
    }

    @Test
    public void testConstructor() {
        assertEquals("Test1", f1.getName());
        assertEquals(BREAKFAST, f1.getMeal());
        assertEquals(600, f1.getCalories());

        try {
            f1.getCarbs();
            fail("NotInitializedException expected");
        } catch (NotInitializedException e) {
            // pass
        }

        try {
            f1.getFat();
            fail("NotInitializedException expected");
        } catch (NotInitializedException e) {
            // pass
        }

        try {
            f1.getProtein();
            fail("NotInitializedException expected");
        } catch (NotInitializedException e) {
            // pass
        }

        assertEquals("Test2", f2.getName());
        assertEquals(LUNCH, f2.getMeal());
        assertEquals(300, f2.getCalories());

        try {
            assertEquals(70, f2.getCarbs());
            assertEquals(1, f2.getFat());
            assertEquals(2, f2.getProtein());
        } catch (NotInitializedException e) {
            fail("NotInitializedException thrown");
        }

    }

    @Test
    public void testSettersFail() {
        try {
            f1.setMeal(null);
            fail("NullInputException expected");
        } catch (NullInputException e) {
            // pass
        }

        try {
            f1.setCalories(-1);
            fail("InvalidInputException expected");
        } catch (InvalidInputException e) {
            // pass
        }

        try {
            f1.setCarbs(-1);
            fail("InvalidInputException expected");
        } catch (InvalidInputException e) {
            // pass
        }

        try {
            f1.setFat(-1);
            fail("InvalidInputException expected");
        } catch (InvalidInputException e) {
            // pass
        }

        try {
            f1.setProtein(-1);
            fail("InvalidInputException expected");
        } catch (InvalidInputException e) {
            // pass
        }
    }

    @Test
    public void testSettersSuccess() {
        f1.setName("Sandwich");
        assertEquals("Sandwich", f1.getName());
        f1.setMeal(DINNER);
        assertEquals(DINNER, f1.getMeal());
        try {
            f1.setCalories(400);
            assertEquals(400, f1.getCalories());
            f1.setCarbs(50);
            assertEquals(50, f1.getCarbs());
            f1.setFat(10);
            assertEquals(10, f1.getFat());
            f1.setProtein(20);
            assertEquals(20, f1.getProtein());
        } catch (NotInitializedException | InvalidInputException e) {
            fail("Exceptions thrown unexpectedly");
        }
    }

    @Test
    public void testRemoveMacros() {
        f2.removeCarbs();

        try {
            f2.getCarbs();
            fail("NotInitializedException expected");
        } catch (NotInitializedException e) {
            // pass
        }

        f2.removeFat();

        try {
            f2.getFat();
            fail("NotInitializedException expected");
        } catch (NotInitializedException e) {
            // pass
        }

        f2.removeProtein();

        try {
            f2.getProtein();
            fail("NotInitializedException expected");
        } catch (NotInitializedException e) {
            // pass
        }
    }
}
