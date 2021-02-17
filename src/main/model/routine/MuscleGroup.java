package model.routine;

/**
 * MuscleGroup is an enumeration representing the main muscle group that a workout could train.
 */

public enum MuscleGroup {
    CHEST("Chest"),
    ABS("Abs"),
    SHOULDERS("Shoulders"),
    BICEPS("Biceps"),
    TRICEPS("Triceps"),
    LATS("Lats"),
    QUADS("Quads"),
    GLUTES("Glutes"),
    HAMSTRINGS("Hamstrings"),
    CALVES("Calves");

    private String desc;

    // EFFECTS: muscle group has a description
    MuscleGroup(String desc) {
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
