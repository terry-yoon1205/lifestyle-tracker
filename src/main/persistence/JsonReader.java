package persistence;

import exceptions.InvalidInputException;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * JsonReader represents a reader that reads person from JSON data stored in file
 *
 * Class adapted from:
 *  JsonSerializationDemo source code
 *      by Paul Carter
 *      https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads person from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public Person read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePerson(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Person parsePerson(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        boolean sex = jsonObject.getBoolean("sex");
        int age = jsonObject.getInt("age");
        int height = jsonObject.getInt("height");
        int weight = jsonObject.getInt("weight");
        ActivityLevel activity = ActivityLevel.valueOf(jsonObject.getString("activity"));

        Person p = new Person(name, sex, age, height, weight, activity);
        addWeightGoals(p, jsonObject);
        addFoodGoals(p, jsonObject);
        addRoutineCollection(p, jsonObject);
        addFoodLog(p, jsonObject);
        return p;
    }

    // MODIFIES: p
    // EFFECTS: sets the person's weight goals from goals parsed from JSON object
    private void addWeightGoals(Person p, JSONObject jsonObject) {
        try {
            int weightGoal = jsonObject.getInt("weight goal");
            p.setWeightGoal(weightGoal);
        } catch (InvalidInputException e) {
            // do nothing
        }

        try {
            GoalIntensity goalIntensity = GoalIntensity.valueOf(jsonObject.getString("goal intensity"));
            p.setGoalIntensity(goalIntensity);
        } catch (JSONException e) {
            // do nothing
        }
    }

    // MODIFIES: p
    // EFFECTS: sets the person's calorie and macro goals from goals parsed from JSON object
    private void addFoodGoals(Person p, JSONObject jsonObject) {
        try {
            int calorieGoal = jsonObject.getInt("calorie goal");
            p.setCalorieGoal(calorieGoal);
        } catch (InvalidInputException e) {
            // do nothing
        }

        int carbsGoal = jsonObject.getInt("carbs goal");
        int fatGoal = jsonObject.getInt("fat goal");
        int proteinGoal = jsonObject.getInt("protein goal");

        try {
            p.setCarbsGoal(carbsGoal);
        } catch (InvalidInputException e) {
            // do nothing
        }

        try {
            p.setFatGoal(fatGoal);
        } catch (InvalidInputException e) {
            // do nothing
        }

        try {
            p.setProteinGoal(proteinGoal);
        } catch (InvalidInputException e) {
            // do nothing
        }
    }

    // MODIFIES: p
    // EFFECTS: parses routines from JSON object and adds the routine collection to person
    private void addRoutineCollection(Person p, JSONObject jsonObject) {
        JSONArray routines = jsonObject.getJSONArray("routine collection");
        RoutineCollection rc = p.getRoutines();

        for (Object routine : routines) {
            JSONObject nextRoutine = (JSONObject) routine;
            rc.add(parseRoutine(nextRoutine));
        }
    }

    // EFFECTS: parses and returns a routine from JSON object
    private Routine parseRoutine(JSONObject r) {
        String name = r.getString("name");
        JSONArray workouts = r.getJSONArray("workouts");
        Routine routine = new Routine(name);

        for (Object workout : workouts) {
            JSONObject nextWorkout = (JSONObject) workout;
            routine.add(parseWorkout(nextWorkout));
        }

        return routine;
    }

    // EFFECTS: parses and returns a workout from JSON object
    private Workout parseWorkout(JSONObject w) {
        String name = w.getString("name");
        int reps = w.getInt("reps");
        int sets = w.getInt("sets");
        int weight = w.getInt("weight");
        MuscleGroup muscle = MuscleGroup.valueOf(w.getString("muscle"));

        return new Workout(name, reps, sets, weight, muscle);
    }

    // MODIFIES: p
    // EFFECTS: parses food log entries from JSON object and adds the food log to person
    private void addFoodLog(Person p, JSONObject jsonObject) {
        JSONArray foodLog = jsonObject.getJSONArray("food log");
        FoodLog fl = p.getFoodLog();

        for (Object entry : foodLog) {
            JSONObject nextEntry = (JSONObject) entry;
            fl.getEntries().add(parseEntry(nextEntry));
        }
    }

    // EFFECTS: parses and returns a food log entry from JSON object
    private FoodLogEntry parseEntry(JSONObject e) {
        String name = e.getString("name");
        JSONArray foods = e.getJSONArray("foods");
        FoodLogEntry fle = new FoodLogEntry(name);

        for (Object food : foods) {
            JSONObject nextFood = (JSONObject) food;
            fle.add(parseFood(nextFood));
        }

        return fle;
    }

    // EFFECTS: parses and returns a food from JSON object
    private Food parseFood(JSONObject f) {
        String name = f.getString("name");
        Meal meal = Meal.valueOf(f.getString("meal"));
        int calories = f.getInt("calories");
        int carbs = f.getInt("carbs");
        int fat = f.getInt("fat");
        int protein = f.getInt("protein");
        Food food = new Food(name, meal, calories);

        try {
            food.setCarbs(carbs);
        } catch (InvalidInputException e) {
            // do nothing
        }

        try {
            food.setFat(fat);
        } catch (InvalidInputException e) {
            // do nothing
        }

        try {
            food.setProtein(protein);
        } catch (InvalidInputException e) {
            // do nothing
        }

        return food;
    }
}
