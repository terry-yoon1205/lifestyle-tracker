package model;

import model.routine.Routine;
import model.routine.Workout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.routine.MuscleGroup.*;
import static org.junit.jupiter.api.Assertions.*;

public class RoutineTest extends EntryTest<Routine, Workout> {

    @BeforeEach
    public void setUp() {
        e = new Routine("TR");
        su1 = new Workout("Test1", 10, 3, 40, LATS);
        su2 = new Workout("Test2", 8, 4, 155, QUADS);
    }

    @Test
    public void testToString() {
        assertEquals("TR", e.toString());
    }

    @Test
    public void testConstructorAndSetter() {
        assertEquals("TR", e.getName());
        assertEquals(0, e.getUnits().size());

        e.setName("TR2");
        assertEquals("TR2", e.getName());
    }

    @Test
    public void testGetUnitsFormatted() {
        assertEquals("", e.listToString());

        e.add(su1);
        e.add(su2);

        assertEquals("Test1 (10 reps, 3 sets, 40 lbs; Lats)\nTest2 (8 reps, 4 sets, 155 lbs; Quads)\n",
                e.listToString());
    }
}
