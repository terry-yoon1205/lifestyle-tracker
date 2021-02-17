package model;

import model.food.Food;
import model.food.FoodLogEntry;
import model.food.Meal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static model.food.Meal.*;
import static org.junit.jupiter.api.Assertions.*;

public class FoodLogEntryTest extends EntryTest<FoodLogEntry, Food> {

    @BeforeEach
    public void setUp() {
        e = new FoodLogEntry("2020-10-13");
        su1 = new Food("Test1", BREAKFAST, 600, 70, 2, 5);
        su2 = new Food("Test2", SNACKS, 200);
    }

    @Test
    public void testConstructorAndSetter() {
        assertEquals("2020-10-13", e.getName());
        assertEquals(0, e.getUnits().size());

        e.setName("2020-10-14");
        assertEquals("2020-10-14", e.getName());
    }

    @Test
    public void testToString() {
        assertEquals("2020-10-13", e.toString());
    }

    @Test
    public void testSumCalories() {
        assertEquals(0, e.sumCalories());

        e.add(su1);
        assertEquals(600, e.sumCalories());

        e.add(su2);
        assertEquals(800, e.sumCalories());
    }

    @Test
    public void testSumCaloriesByMeal() {
        for (Meal m : Meal.values()) {
            assertEquals(0, e.sumCalories(m));
        }

        Food f3 = new Food("Test3", BREAKFAST, 400);
        e.add(su1);
        e.add(su2);
        e.add(f3);

        assertEquals(1000, e.sumCalories(BREAKFAST));
        assertEquals(200, e.sumCalories(SNACKS));
    }

    @Test
    public void testGetFoodsByMeal() {
        for (Meal m : Meal.values()) {
            assertEquals(0, e.getFoodsByMeal(m).size());
        }

        Food f3 = new Food("Test3", BREAKFAST, 400);
        e.add(su1);
        e.add(su2);
        e.add(f3);

        List<Food> breakfasts = e.getFoodsByMeal(BREAKFAST);
        assertEquals(2, breakfasts.size());
        assertEquals(su1, breakfasts.get(0));
        assertEquals(f3, breakfasts.get(1));

        List<Food> snacks = e.getFoodsByMeal(SNACKS);
        assertEquals(1, snacks.size());
        assertEquals(su2, snacks.get(0));

        assertEquals(0, e.getFoodsByMeal(LUNCH).size());
        assertEquals(0, e.getFoodsByMeal(DINNER).size());
    }

    @Test
    public void testGetUnitsFormatted() {
        assertEquals("Breakfast:\nLunch:\nDinner:\nSnacks:\n", e.listToString());

        Food su3 = new Food("Test3", LUNCH, 700);
        Food su4 = new Food("Test4", DINNER, 1200);
        Food su5 = new Food("Test5", LUNCH, 100);

        e.add(su1);
        e.add(su2);
        e.add(su3);
        e.add(su4);
        e.add(su5);

        assertEquals("Breakfast:\n\tTest1 (600 kcal, carbs: 70 g, fat: 2 g, protein: 5 g)\nLunch:\n\tTest3 " +
                "(700 kcal)\n\tTest5 (100 kcal)\nDinner:\n\tTest4 (1200 kcal)\nSnacks:\n\tTest2 (200 kcal)\n",
                e.listToString());
    }
}
