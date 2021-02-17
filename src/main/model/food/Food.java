package model.food;

import exceptions.InvalidInputException;
import exceptions.NotInitializedException;
import exceptions.NullInputException;
import model.SingleUnit;
import org.json.JSONObject;
import persistence.Writable;

/**
 * Food represents a single item of food that the user decides to record on the food log. It has a name, amount of
 * calories, and optionally the amount of carbohydrates, fat, and protein if the user also wishes to track
 * macronutrients.
 */

public class Food extends SingleUnit implements Writable {
    private Meal meal;

    private int calories;
    private int carbs;
    private int fat;
    private int protein;

    // EFFECTS: food has a name and the meal it's eaten as, calories, but no specified macronutrients (carbs, fat,
    //          protein) in grams, represented by -1
    public Food(String name, Meal meal, int calories) {
        super(name);
        this.meal = meal;
        this.calories = calories;
        carbs = -1;
        fat = -1;
        protein = -1;
    }

    // EFFECTS: food has a name and the meal it's eaten as, calories, and macronutrients (carbs, fat, protein) in grams
    public Food(String name, Meal meal, int calories, int carbs, int fat, int protein) {
        super(name);
        this.meal = meal;
        this.calories = calories;
        this.carbs = carbs;
        this.fat = fat;
        this.protein = protein;
    }

    // getters
    public Meal getMeal() {
        return meal;
    }

    public int getCalories() {
        return calories;
    }

    // EFFECTS: return carbs; if < 0, throw NotInitializedException
    public int getCarbs() throws NotInitializedException {
        if (carbs < 0) {
            throw new NotInitializedException();
        }
        return carbs;
    }

    // EFFECTS: return fat; if < 0, throw NotInitializedException
    public int getFat() throws NotInitializedException {
        if (fat < 0) {
            throw new NotInitializedException();
        }
        return fat;
    }

    // EFFECTS: return protein; if < 0, throw NotInitializedException
    public int getProtein() throws NotInitializedException {
        if (protein < 0) {
            throw new NotInitializedException();
        }
        return protein;
    }

    @Override
    public String toString() {
        String line = name + " (" + calories + " kcal";

        try {
            line = line + ", carbs: " + getCarbs() + " g";
        } catch (NotInitializedException e) {
            // do nothing
        }

        try {
            line = line + ", fat: " + getFat() + " g";
        } catch (NotInitializedException e) {
            // do nothing
        }

        try {
            line = line + ", protein: " + getProtein() + " g";
        } catch (NotInitializedException e) {
            // do nothing
        }

        return line + ")";
    }

    // setters

    // EFFECTS: set meal; if null, throw NullInputException
    public void setMeal(Meal meal) {
        if (meal == null) {
            throw new NullInputException();
        }
        this.meal = meal;
    }

    // EFFECTS: set calories; if < 0, throw InvalidInputException
    public void setCalories(int calories) throws InvalidInputException {
        if (calories < 0) {
            throw new InvalidInputException();
        }
        this.calories = calories;
    }

    // EFFECTS: set carbs; if < 0, throw InvalidInputException
    public void setCarbs(int carbs) throws InvalidInputException {
        if (carbs < 0) {
            throw new InvalidInputException();
        }
        this.carbs = carbs;
    }

    // EFFECTS: set fat; if < 0, throw InvalidInputException
    public void setFat(int fat) throws InvalidInputException {
        if (fat < 0) {
            throw new InvalidInputException();
        }
        this.fat = fat;
    }

    // EFFECTS: set protein; if < 0, throw InvalidInputException
    public void setProtein(int protein) throws InvalidInputException {
        if (protein < 0) {
            throw new InvalidInputException();
        }
        this.protein = protein;
    }

    // MODIFIES: this
    // EFFECTS: revert the value of carbs back to the not-initialized state
    public void removeCarbs() {
        carbs = -1;
    }

    // MODIFIES: this
    // EFFECTS: revert the value of fat back to the not-initialized state
    public void removeFat() {
        fat = -1;
    }

    // MODIFIES: this
    // EFFECTS: revert the value of protein back to the not-initialized state
    public void removeProtein() {
        protein = -1;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("meal", meal.name());
        json.put("calories", calories);
        json.put("carbs", carbs);
        json.put("fat", fat);
        json.put("protein", protein);

        return json;
    }
}
