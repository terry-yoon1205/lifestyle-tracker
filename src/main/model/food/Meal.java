package model.food;

/**
 * Meal is an enumeration representing the four kinds of meals (breakfast, lunch, dinner, snacks) you could have in a
 * day.
 */

public enum Meal {
    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    DINNER("Dinner"),
    SNACKS("Snacks");

    private String desc;

    // EFFECTS: meal has a description
    Meal(String desc) {
        this.desc = desc;
    }

    // getters
    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return getDesc();
    }
}
