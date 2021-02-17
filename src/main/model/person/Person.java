package model.person;

import exceptions.InvalidInputException;
import exceptions.NotInitializedException;
import exceptions.NullInputException;
import model.food.FoodLog;
import model.food.FoodLogEntry;
import model.routine.Routine;
import model.routine.RoutineCollection;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.regex.Pattern;

/**
 * Person stores some personal information given by the user that will be used in this application. It stores the
 * user's name to distinguish between users, the user's food log and routines, and sex, age, height, and weight, and
 * activity level are used to calculate maintenance calories (total daily energy expenditure) of the user according to
 * the Mifflin St. Jeor equation, which is:
 *
 *  Men: ((10 x weight in kg) + (6.25 x height in cm) – (4.92 x age) + 5) x activity multiplier
 *  Women: ((10 x weight in kg) + (6.25 x height in cm) – (4.92 x age) – 161) x activity multiplier.
 *
 * The calorie requirement is also scaled based on the user's goal intensity. The class also stores the user's desired
 * weight, and holds the user's calorie goal and macronutrient goals.
 */

public class Person implements Writable {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final Pattern DATE_FORMAT_REGEX = Pattern.compile(
            "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");

    private String name;
    private boolean sex;
    private int age;
    private int height;
    private int weight;
    private ActivityLevel activityLevel;

    private int weightGoal;
    private GoalIntensity goalIntensity;

    private int calorieGoal;
    private int carbsGoal;
    private int fatGoal;
    private int proteinGoal;

    private RoutineCollection routines;
    private FoodLog foodLog;

    // EFFECTS: person has a name, sex (true for male, false for female), age, height in cm, weight in kgs, and
    //          their activity level; no goals set, empty routine collection and food log
    public Person(String name, boolean sex, int age, int height, int weight, ActivityLevel activity) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activity;
        routines = new RoutineCollection();
        foodLog = new FoodLog();

        carbsGoal = -1;
        fatGoal = -1;
        proteinGoal = -1;
    }

    // EFFECTS: check given string against DATE_FORMAT_REGEX pattern; return the string if pattern matches,
    //          throw InvalidInputException if pattern doesn't match
//    public static String checkDateFormat(String s) throws InvalidInputException {
//        if (!DATE_FORMAT_REGEX.matcher(s).matches()) {
//            throw new InvalidInputException();
//        }
//        return s;
//    }

    // getters
    public String getName() {
        return name;
    }

    public boolean getSex() {
        return sex;
    }

    // EFFECTS: return the sex in string form; "Male" if true, "Female" if false
    public String getSexString() {
        if (sex) {
            return "Male";
        } else {
            return "Female";
        }
    }

    public int getAge() {
        return age;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    // EFFECTS: return weight goal; if <= 0, throw NotInitializedException
    public int getWeightGoal() throws NotInitializedException {
        if (weightGoal <= 0) {
            throw new NotInitializedException();
        }
        return weightGoal;
    }

    // EFFECTS: return goal intensity; if null, throw NotInitializedException
    public GoalIntensity getGoalIntensity() throws NotInitializedException {
        if (goalIntensity == null) {
            throw new NotInitializedException();
        }
        return goalIntensity;
    }

    // EFFECTS: return calorie goal; if <= 0, throw NotInitializedException
    public int getCalorieGoal() throws NotInitializedException {
        if (calorieGoal <= 0) {
            throw new NotInitializedException();
        }
        return calorieGoal;
    }

    // EFFECTS: return carbs goal; if < 0, throw NotInitializedException
    public int getCarbsGoal() throws NotInitializedException {
        if (carbsGoal < 0) {
            throw new NotInitializedException();
        }
        return carbsGoal;
    }

    // EFFECTS: return fat goal; if < 0, throw NotInitializedException
    public int getFatGoal() throws NotInitializedException {
        if (fatGoal < 0) {
            throw new NotInitializedException();
        }
        return fatGoal;
    }

    // EFFECTS: return protein goal; if < 0, throw NotInitializedException
    public int getProteinGoal() throws NotInitializedException {
        if (proteinGoal < 0) {
            throw new NotInitializedException();
        }
        return proteinGoal;
    }

    public RoutineCollection getRoutines() {
        return routines;
    }

    public FoodLog getFoodLog() {
        return foodLog;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    // MODIFIES: this
    // EFFECTS: set carbs goal; if < 0, throw InvalidInputException
    public void setCarbsGoal(int carbsGoal) throws InvalidInputException {
        if (carbsGoal < 0) {
            throw new InvalidInputException();
        }
        this.carbsGoal = carbsGoal;
    }

    // MODIFIES: this
    // EFFECTS: set fat goal; if < 0, throw InvalidInputException
    public void setFatGoal(int fatGoal) throws InvalidInputException {
        if (fatGoal < 0) {
            throw new InvalidInputException();
        }
        this.fatGoal = fatGoal;
    }

    // MODIFIES: this
    // EFFECTS: set protein goal; if < 0, throw InvalidInputException
    public void setProteinGoal(int proteinGoal) throws InvalidInputException {
        if (proteinGoal < 0) {
            throw new InvalidInputException();
        }
        this.proteinGoal = proteinGoal;
    }

    // MODIFIES: this
    // EFFECTS: set activity level; if activityLevel = null, throw NullInputException
    public void setActivityLevel(ActivityLevel activityLevel) {
        if (activityLevel == null) {
            throw new NullInputException();
        }
        this.activityLevel = activityLevel;
    }

    // MODIFIES: this
    // EFFECTS: set weight goal; if weightGoal <= 0, throw InvalidInputException
    public void setWeightGoal(int weightGoal) throws InvalidInputException {
        if (weightGoal <= 0) {
            throw new InvalidInputException();
        }
        this.weightGoal = weightGoal;
    }

    // MODIFIES: this
    // EFFECTS: set goal intensity; if goalIntensity = null, throw NullInputException
    public void setGoalIntensity(GoalIntensity goalIntensity) {
        if (goalIntensity == null) {
            throw new NullInputException();
        }
        this.goalIntensity = goalIntensity;
    }

    // MODIFIES: this
    // EFFECTS: set calorie goal; if calorieGoal <= 0, throw InvalidInputException
    public void setCalorieGoal(int calorieGoal) throws InvalidInputException {
        if (calorieGoal <= 0) {
            throw new InvalidInputException();
        }
        this.calorieGoal = calorieGoal;
    }

    // MODIFIES: this
    // EFFECTS: set carbs, fat, and protein goal in grams based on given respective proportion of the calorie goal;
    //          if calorie goal = 0, throw NotInitializedException, if given values don't add up to 1, throw
    //          InvalidInputException
    public void setMacroGoal(double carbs, double fat, double protein) throws NotInitializedException,
            InvalidInputException {
        if (carbs + fat + protein != 1) {
            throw new InvalidInputException();
        }
        carbsGoal = (int) ((getCalorieGoal() * carbs) / 4);
        fatGoal = (int) ((getCalorieGoal() * fat) / 9);
        proteinGoal = (int) ((getCalorieGoal() * protein) / 4);
    }

    // EFFECTS: returns the recommended calories based on weight goal; if goal intensity = null, throw
    //          NotInitializedException
    public int calculateCalories() throws NotInitializedException {
        double result;

        if (sex) {
            result = ((10 * weight) + (6.25 * height) - (4.92 * age) + 5) * activityLevel.getMultiplier();
        } else {
            result = ((10 * weight) + (6.25 * height) - (4.92 * age) - 161) * activityLevel.getMultiplier();
        }

        return (int) (result * getGoalIntensity().getMultiplier());
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("sex", sex);
        json.put("age", age);
        json.put("height", height);
        json.put("weight", weight);
        json.put("activity", activityLevel.name());
        json.put("weight goal", weightGoal);

        try {
            json.put("goal intensity", getGoalIntensity().name());
        } catch (NotInitializedException e) {
            // do nothing
        }

        json.put("calorie goal", calorieGoal);
        json.put("carbs goal", carbsGoal);
        json.put("fat goal", fatGoal);
        json.put("protein goal", proteinGoal);
        json.put("routine collection", routinesToJson());
        json.put("food log", foodLogToJson());

        return json;
    }

    // EFFECTS: returns routine collection as a JSON array
    private JSONArray routinesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Routine r : routines.getEntries()) {
            jsonArray.put(r.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns food log as a JSON array
    private JSONArray foodLogToJson() {
        JSONArray jsonArray = new JSONArray();

        for (FoodLogEntry f : foodLog.getEntries()) {
            jsonArray.put(f.toJson());
        }

        return jsonArray;
    }
}
