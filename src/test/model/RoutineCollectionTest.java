package model;

import exceptions.DoesNotExistException;
import model.routine.Routine;
import model.routine.RoutineCollection;
import model.routine.Workout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.routine.MuscleGroup.*;
import static org.junit.jupiter.api.Assertions.*;

public class RoutineCollectionTest extends CollectionTest<RoutineCollection, Routine, Workout> {

    @BeforeEach
    public void setUp() {
        c = new RoutineCollection();
        e1 = new Routine("TR1");
        e2 = new Routine("TR2");
        su1 = new Workout("W1", 10, 4, 0, BICEPS);
        su2 = new Workout("W2", 6, 3, 45, CHEST);
        su3 = new Workout("W3", 8, 5, 105, SHOULDERS);
        su4 = new Workout("W4", 12, 2, 20, TRICEPS);

        e1.add(su1);
        e1.add(su2);
        e2.add(su3);
        e2.add(su4);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, c.getEntries().size());
    }

    @Test
    public void testGetCollectionFormatted() {
        assertEquals("", c.toString());

        c.add(e1);
        c.add(e2);

        assertEquals("TR1\nTR2\n", c.toString());
    }

    @Test
    public void testSearchEntryDoesNotExist() {
        c.add(e1);

        try {
            c.search("TR2");
            fail();
        } catch (DoesNotExistException e) {
            // pass
        }
    }

    @Test
    public void testSearchEntryTypical() {
        c.add(e1);
        c.add(e2);

        try {
            assertEquals(e1, c.search("TR1"));
            assertEquals(2, c.getEntries().size());
        } catch (DoesNotExistException e) {
            fail();
        }
    }

}
