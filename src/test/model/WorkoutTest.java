package model;

import model.routine.Workout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.routine.MuscleGroup.*;
import static org.junit.jupiter.api.Assertions.*;

public class WorkoutTest {
    public Workout w;

    @BeforeEach
    public void setUp() {
        w = new Workout("Test", 10, 3, 40, CALVES);
    }

    @Test
    public void testConstructor() {
        assertEquals("Test", w.getName());
        assertEquals(10, w.getReps());
        assertEquals(3, w.getSets());
        assertEquals(40, w.getWeight());
        assertEquals(CALVES, w.getMuscle());
    }

    @Test
    public void testSetters() {
        w.setName("Diff");
        assertEquals("Diff", w.getName());
        w.setReps(8);
        assertEquals(8, w.getReps());
        w.setSets(4);
        assertEquals(4, w.getSets());
        w.setWeight(100);
        assertEquals(100, w.getWeight());
        w.setMuscle(TRICEPS);
        assertEquals(TRICEPS, w.getMuscle());
    }
}
