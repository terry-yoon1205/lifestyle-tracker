package model.person;

/**
 * GoalIntensity is an enumeration representing the user's desired speed at which they would like to lose or gain weight
 * (or maintain their weight). They apply a multiplier to the maintenance calories to calculate the user's daily goal
 * calories.
 */

public enum GoalIntensity {
    LIGHT_LOSS("Lose around 0.25kg/week", 0.85),
    MODERATE_LOSS("Lose around 0.5kg/week", 0.75),
    EXTREME_LOSS("Lose around 1kg/week; warning: not recommended.", 0.55),
    MAINTAIN("Maintain your weight", 1),
    LIGHT_GAIN("Gain around 0.25kg/week", 1.15),
    MODERATE_GAIN("Gain around 0.5kg/week", 1.25),
    EXTREME_GAIN("Gain around 1kg/week", 1.45);

    private final String desc;
    private final double calorieMultiplier;

    // EFFECTS: goal intensity has a description and a multiplier for calculating daily calories
    GoalIntensity(String desc, double multiplier) {
        this.desc = desc;
        calorieMultiplier = multiplier;
    }

    // getters
    public String getDesc() {
        return desc;
    }

    public double getMultiplier() {
        return calorieMultiplier;
    }
}
