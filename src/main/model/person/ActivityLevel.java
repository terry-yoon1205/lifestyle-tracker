package model.person;

/**
 * ActivityLevel is an enumeration of the five activity levels that a user can have. The activity level is used to
 * calculate the maintenance calories according to the Mifflin St. Jeor equation.
 */

public enum ActivityLevel {
    SEDENTARY("Little to no activity/desk job", 1.1),
    LIGHTLY_ACTIVE("Light exercise 1-3 day a week", 1.3),
    MODERATELY_ACTIVE("Exercise 3-5 days a week", 1.5),
    VERY_ACTIVE("Heavy exercise 6-7 days a week", 1.7),
    ATHLETE("Very heavy exercise daily/active job", 1.9);

    private final String desc;
    private final double multiplier;

    // EFFECTS: activity level has a description and a multiplier for calculating daily calories
    ActivityLevel(String desc, double multiplier) {
        this.desc = desc;
        this.multiplier = multiplier;
    }

    // getters

    // EFFECTS: returns name as a capitalized string
    public String getName() {
        return name().charAt(0) + name().substring(1).toLowerCase().replaceAll("_", " ");
    }

    public String getDesc() {
        return desc;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
