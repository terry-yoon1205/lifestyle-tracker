package model.food;

import model.Entry;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

import static model.food.Meal.*;

/**
 * FoodLogEntry represents a single entry in the user's food log for a specific date. It is identifiable by the date
 * when the entry was created, and lists of foods separated by if it was consumed as breakfast, lunch, dinner or snacks.
 * It also holds a constant which is the format of the date (yyyy-MM-dd) that the user will see.
 */

public class FoodLogEntry extends Entry<Food> implements Writable {

    // EFFECTS: food log entry has the current date when the entry was created as the name, and an empty
    //          list of foods
    public FoodLogEntry(String name) {
        super(name);
    }

    // getters

    // EFFECTS: return a list of foods eaten as given meal
    public List<Food> getFoodsByMeal(Meal m) {
        List<Food> foodsByMeal = new ArrayList<>();

        for (Food f : units) {
            if (f.getMeal() == m) {
                foodsByMeal.add(f);
            }
        }

        return foodsByMeal;
    }

    // EFFECTS: return the food log entry as a formatted string
    public String listToString() {
        StringBuilder breakfast = headerSetup(BREAKFAST);
        StringBuilder lunch = headerSetup(LUNCH);
        StringBuilder dinner = headerSetup(DINNER);
        StringBuilder snacks = headerSetup(SNACKS);

        for (Food f : units) {
            String line = "\t" + f.toString() + "\n";

            switch (f.getMeal()) {
                case BREAKFAST: breakfast.append(line);
                    break;
                case LUNCH: lunch.append(line);
                    break;
                case DINNER: dinner.append(line);
                    break;
                default: snacks.append(line);
                    break;
            }
        }
        return breakfast.toString() + lunch.toString() + dinner.toString() + snacks.toString();
    }

    // EFFECTS: return a header StringBuilder object for given meal
    private StringBuilder headerSetup(Meal meal) {
        StringBuilder mealHeader = new StringBuilder();

        mealHeader.append(meal.getDesc());
        mealHeader.append(":\n");

        return mealHeader;
    }

    // EFFECTS: return the sum of all calories in the food log entry
    public int sumCalories() {
        int calories = 0;

        for (Food f : units) {
            calories += f.getCalories();
        }

        return calories;
    }

    // EFFECTS: return the sum of all calories of foods eaten as given meal in the food log entry
    public int sumCalories(Meal m) {
        List<Food> foodsByMeal = getFoodsByMeal(m);
        int calories = 0;

        for (Food f : foodsByMeal) {
            calories += f.getCalories();
        }

        return calories;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("foods", foodsToJson());

        return json;
    }

    // EFFECTS: return units in this entry as a JSON array
    private JSONArray foodsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Food f : units) {
            jsonArray.put(f.toJson());
        }

        return jsonArray;
    }
}
