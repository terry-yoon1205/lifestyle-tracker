package model.routine;

import model.SingleUnit;
import org.json.JSONObject;
import persistence.Writable;

/**
 * Workout represents a single workout that has a name, number of sets and reps per set that the user desires to
 * perform, amount of weight used during the workout (0 if bodyweight), and the main muscle group the workout trains.
 */

public class Workout extends SingleUnit implements Writable {
    private int reps;
    private int sets;
    private int weight;
    private MuscleGroup muscle;

    // EFFECTS: workout has a name, number of reps and sets, amount of weight in lbs, and the main
    //          muscle group the workout is training
    public Workout(String name, int reps, int sets, int weight, MuscleGroup muscle) {
        super(name);
        this.reps = reps;
        this.sets = sets;
        this.weight = weight;
        this.muscle = muscle;
    }

    // getters
    public int getReps() {
        return reps;
    }

    public int getSets() {
        return sets;
    }

    public int getWeight() {
        return weight;
    }

    public MuscleGroup getMuscle() {
        return muscle;
    }

    @Override
    public String toString() {
        return name + " (" + reps + " reps, " + sets + " sets, " + weight + " lbs; " + muscle.getDesc() + ")";
    }

    // setters
    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setMuscle(MuscleGroup muscle) {
        this.muscle = muscle;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("reps", reps);
        json.put("sets", sets);
        json.put("weight", weight);
        json.put("muscle", muscle.name());

        return json;
    }
}
