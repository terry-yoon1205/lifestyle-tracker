package model;

import exceptions.InvalidInputException;
import exceptions.NotInitializedException;
import exceptions.NullInputException;
import model.person.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.person.ActivityLevel.*;
import static model.person.GoalIntensity.*;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {
    public Person pi;

    @BeforeEach
    public void setUp() {
        pi = new Person("Test", true, 20, 170, 60, LIGHTLY_ACTIVE);
    }

    @Test
    public void testConstructor() {
        assertEquals("Test", pi.getName());
        assertTrue(pi.getSex());
        assertEquals(20, pi.getAge());
        assertEquals(170, pi.getHeight());
        assertEquals(60, pi.getWeight());
        assertEquals(LIGHTLY_ACTIVE, pi.getActivityLevel());
        assertEquals(0, pi.getFoodLog().getEntries().size());
        assertEquals(0, pi.getRoutines().getEntries().size());
    }

    @Test
    public void testGetSexString() {
        assertEquals("Male", pi.getSexString());

        Person pi2 = new Person("Test2", false, 35, 155, 48, SEDENTARY);
        assertEquals("Female", pi2.getSexString());
    }

    @Test
    public void testGetGoalsFail() {
        try {
            pi.getWeightGoal();
            fail();
        } catch (NotInitializedException e) {
            // pass
        }

        try {
            pi.getCarbsGoal();
            fail();
        } catch (NotInitializedException e) {
            // pass
        }

        try {
            pi.getFatGoal();
            fail();
        } catch (NotInitializedException e) {
            // pass
        }

        try {
            pi.getProteinGoal();
            fail();
        } catch (NotInitializedException e) {
            // pass
        }
    }

    @Test
    public void testSettersFail() {
        try {
            pi.setWeightGoal(0);
            fail();
        } catch (InvalidInputException e) {
            // pass
        }

        try {
            pi.setWeightGoal(-1);
            fail();
        } catch (InvalidInputException e) {
            // pass
        }

        try {
            pi.setActivityLevel(null);
            fail();
        } catch (NullInputException e) {
            // pass
        }

        try {
            pi.setGoalIntensity(null);
            fail();
        } catch (NullInputException e) {
            // pass
        }

        try {
            pi.setCalorieGoal(0);
        } catch (InvalidInputException e) {
            // pass
        }

        try {
            pi.setWeightGoal(-1);
            fail();
        } catch (InvalidInputException e) {
            // pass
        }
    }

    @Test
    public void testSettersSuccess() {
        try {
            pi.setName("Test2");
            assertEquals("Test2", pi.getName());
            pi.setSex(false);
            assertFalse(pi.getSex());
            pi.setAge(30);
            assertEquals(30, pi.getAge());
            pi.setHeight(175);
            assertEquals(175, pi.getHeight());
            pi.setWeight(55);
            assertEquals(55, pi.getWeight());
            pi.setActivityLevel(MODERATELY_ACTIVE);
            assertEquals(MODERATELY_ACTIVE, pi.getActivityLevel());
            pi.setCalorieGoal(2000);
            pi.setGoalIntensity(MODERATE_LOSS);
            pi.setWeightGoal(60);
        } catch (InvalidInputException e) {
            fail();
        }
    }

    @Test
    public void testCalculateCaloriesFail() {
        try {
            pi.calculateCalories();
            fail();
        } catch (NotInitializedException e) {
            // pass
        }
    }

    @Test
    public void testCalculateCaloriesSuccess() {
        try {
            pi.setWeightGoal(50);
            pi.setGoalIntensity(LIGHT_LOSS);

            assertEquals(50, pi.getWeightGoal());
            assertEquals(LIGHT_LOSS, pi.getGoalIntensity());

            assertEquals(1733, pi.calculateCalories());

            Person pi2 = new Person("Test2", false, 35, 155, 48, SEDENTARY);
            pi2.setGoalIntensity(MODERATE_GAIN);

            assertEquals(1533, pi2.calculateCalories());
        } catch (NotInitializedException | InvalidInputException e) {
            fail();
        }
    }

    @Test
    public void testSetMacroGoalFail() {
        try {
            pi.setMacroGoal(0.4, 0.3, 0.3);
            fail();
        } catch (NotInitializedException e) {
            // pass
        } catch (InvalidInputException e) {
            fail();
        }

        try {
            pi.setCalorieGoal(2000);
            pi.setMacroGoal(0.4, 0.2, 0.3);
            fail();
        } catch (InvalidInputException e) {
            // pass
        } catch (NotInitializedException e) {
            fail();
        }

        try {
            pi.setMacroGoal(0.4, 0.4, 0.3);
            fail();
        } catch (InvalidInputException e) {
            // pass
        } catch (NotInitializedException e) {
            fail();
        }
    }

    @Test
    public void testSetMacroGoalSuccess() {
        try {
            pi.setGoalIntensity(LIGHT_LOSS);
            pi.setCalorieGoal(pi.calculateCalories());

            pi.setMacroGoal(0.5, 0.2, 0.3);
            assertEquals(216, pi.getCarbsGoal());
            assertEquals(38, pi.getFatGoal());
            assertEquals(129, pi.getProteinGoal());

            pi.setCalorieGoal(2000);
            pi.setMacroGoal(0.4, 0.35, 0.25);
            assertEquals(200, pi.getCarbsGoal());
            assertEquals(77, pi.getFatGoal());
            assertEquals(125, pi.getProteinGoal());
        } catch (NotInitializedException | InvalidInputException e) {
            fail();
        }
    }
}
