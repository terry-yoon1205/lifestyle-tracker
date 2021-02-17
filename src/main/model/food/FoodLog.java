package model.food;

import model.Collection;

/**
 * FoodLog represents a log of food log entries of the user. Each food log is identifiable by the user that owns the
 * log. It has a list of food log entries.
 */

public class FoodLog extends Collection<FoodLogEntry> {

    // EFFECTS: food log has no food log entries
    public FoodLog() {
        super();
    }

    // MODIFIES: this
    // EFFECTS: adds given food log entry to the front of the food log list
    @Override
    public void add(FoodLogEntry e) {
        entries.add(0, e);
    }

    // REQUIRES: foodLog is not empty
    // EFFECTS: return the last food log entry in the log
    public FoodLogEntry latestLog() {
        return entries.get(0);
    }
}
