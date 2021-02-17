package model.routine;

import model.Entry;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

/**
 * Routine represents a series of workouts to be performed in a workout session. It has a name to serve as a description
 * of the purpose of the routine and a list of workouts.
 */

public class Routine extends Entry<Workout> implements Writable {

    // EFFECTS: routine has a name and an empty list of workouts
    public Routine(String name) {
        super(name);
    }

    public String listToString() {
        StringBuilder result = new StringBuilder();

        for (Workout w : units) {
            String line = w.toString() + "\n";

            result.append(line);
        }

        return result.toString();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("workouts", workoutsToJson());

        return json;
    }

    // EFFECTS: return units in this entry as a JSON array
    private JSONArray workoutsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Workout w : units) {
            jsonArray.put(w.toJson());
        }

        return jsonArray;
    }
}
