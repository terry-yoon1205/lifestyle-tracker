package persistence;

import exceptions.*;
import model.food.Food;
import model.food.FoodLog;
import model.food.FoodLogEntry;
import model.food.Meal;
import model.person.ActivityLevel;
import model.person.GoalIntensity;
import model.person.Person;
import model.routine.MuscleGroup;
import model.routine.Routine;
import model.routine.RoutineCollection;
import model.routine.Workout;

import static model.person.ActivityLevel.LIGHTLY_ACTIVE;
import static model.person.ActivityLevel.MODERATELY_ACTIVE;
import static model.person.GoalIntensity.LIGHT_GAIN;
import static model.food.Meal.*;
import static model.food.Meal.DINNER;
import static model.routine.MuscleGroup.*;
import static model.routine.MuscleGroup.CHEST;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkInitial(Person p) {
        checkPerson(p, "Initial State", true, 20, 170, 70, LIGHTLY_ACTIVE);
        RoutineCollection rc = p.getRoutines();
        assertEquals(0, rc.getEntries().size());
        FoodLog fl = p.getFoodLog();
        assertEquals(0, fl.getEntries().size());
    }

    protected void checkGeneral(Person p) {
        checkPerson(p, "General", false, 19, 165, 55, MODERATELY_ACTIVE);
        checkWeightGoals(p, 60, LIGHT_GAIN);
        checkFoodGoals(p, 2288, 286, 63, 143);

        checkRoutineCollection(p);
        checkFoodLog(p);
    }

    protected void setUpPerson(Person p) {
        RoutineCollection rc = p.getRoutines();
        FoodLog fl = p.getFoodLog();

        Routine r1 = new Routine("Monday");
        Routine r2 = new Routine("Wednesday");
        rc.add(r1);
        rc.add(r2);

        Workout w1 = new Workout("Barbell squats", 8, 4, 145, QUADS);
        Workout w2 = new Workout("Deadlifts", 9, 3, 135, HAMSTRINGS);
        Workout w3 = new Workout("Hip thrusts", 9, 4, 205, GLUTES);
        Workout w4 = new Workout("Bench press", 10, 3, 45, CHEST);

        r1.add(w1);
        r1.add(w2);
        r2.add(w3);
        r2.add(w4);

        FoodLogEntry fle1 = new FoodLogEntry("2020-10-20");
        FoodLogEntry fle2 = new FoodLogEntry("2020-10-21");
        fl.add(fle1);
        fl.add(fle2);

        Food f1 = new Food("Grilled chicken", LUNCH, 600, 0, 5, 60);
        Food f2 = new Food("Brownies", SNACKS, 200);
        Food f3 = new Food("Sandwich", BREAKFAST, 300, 70, 2, 1);
        Food f4 = new Food("Spicy pork", DINNER, 700);

        fle1.add(f1);
        fle1.add(f2);
        fle2.add(f3);
        fle2.add(f4);
    }

    private void checkRoutineCollection(Person p) {
        RoutineCollection rc = p.getRoutines();
        Routine r1 = rc.getEntries().get(0);
        Routine r2 = rc.getEntries().get(1);
        assertEquals("Monday", r1.getName());
        assertEquals("Wednesday", r2.getName());

        Workout w1 = r1.getUnits().get(0);
        Workout w2 = r1.getUnits().get(1);
        Workout w3 = r2.getUnits().get(0);
        Workout w4 = r2.getUnits().get(1);

        checkWorkout(w1, "Barbell squats", 8, 4, 145, QUADS);
        checkWorkout(w2, "Deadlifts", 9, 3, 135, HAMSTRINGS);
        checkWorkout(w3, "Hip thrusts", 9, 4, 205, GLUTES);
        checkWorkout(w4, "Bench press", 10, 3, 45, CHEST);
    }

    private void checkFoodLog(Person p) {
        FoodLog fl = p.getFoodLog();
        FoodLogEntry fe1 = fl.getEntries().get(0);
        FoodLogEntry fe2 = fl.getEntries().get(1);
        assertEquals("2020-10-21", fe1.getName());
        assertEquals("2020-10-20", fe2.getName());

        Food f1 = fe1.getUnits().get(0);
        Food f2 = fe1.getUnits().get(1);
        Food f3 = fe2.getUnits().get(0);
        Food f4 = fe2.getUnits().get(1);

        checkFood(f1, "Sandwich", BREAKFAST, 300, 70, 2, 1);
        checkFood(f2, "Spicy pork", DINNER, 700, -1, -1, -1);
        checkFood(f3, "Grilled chicken", LUNCH, 600, 0, 5, 60);
        checkFood(f4, "Brownies", SNACKS, 200, -1, -1, -1);
    }

    protected void checkPerson(Person p, String name, boolean sex, int age, int height, int weight,
                               ActivityLevel activity) {
        assertEquals(name, p.getName());
        assertEquals(sex, p.getSex());
        assertEquals(age, p.getAge());
        assertEquals(height, p.getHeight());
        assertEquals(weight, p.getWeight());
        assertEquals(activity, p.getActivityLevel());
    }

    protected void checkWeightGoals(Person p, int weightGoal, GoalIntensity intensity) {
        try {
            assertEquals(weightGoal, p.getWeightGoal());
            assertEquals(intensity, p.getGoalIntensity());
        } catch (NotInitializedException e) {
            fail();
        }
    }

    protected void checkFoodGoals(Person p, int calorieGoal, int carbsGoal, int fatGoal, int proteinGoal) {
        try {
            assertEquals(calorieGoal, p.getCalorieGoal());
            assertEquals(carbsGoal, p.getCarbsGoal());
            assertEquals(fatGoal, p.getFatGoal());
            assertEquals(proteinGoal, p.getProteinGoal());
        } catch (NotInitializedException e) {
            fail();
        }
    }

    protected void checkWorkout(Workout w, String name, int reps, int sets, int weight, MuscleGroup muscle) {
        assertEquals(name, w.getName());
        assertEquals(reps, w.getReps());
        assertEquals(sets, w.getSets());
        assertEquals(weight, w.getWeight());
        assertEquals(muscle, w.getMuscle());
    }

    protected void checkFood(Food f, String name, Meal meal, int calories, int carbs, int fat, int protein) {
        try {
            assertEquals(name, f.getName());
            assertEquals(meal, f.getMeal());
            assertEquals(calories, f.getCalories());
            assertEquals(carbs, f.getCarbs());
            assertEquals(fat, f.getFat());
            assertEquals(protein, f.getProtein());
        } catch (NotInitializedException e) {
            // pass
        }
    }
}
