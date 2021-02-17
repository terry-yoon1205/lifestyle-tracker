package model;

import exceptions.DoesNotExistException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class EntryTest<B extends Entry<T>, T extends SingleUnit> {
    public B e;
    public T su1;
    public T su2;

    @Test
    public abstract void testConstructorAndSetter();

    @Test
    public abstract void testGetUnitsFormatted();

    @Test
    public abstract void testToString();

    @Test
    public void testAddUnit() {
        e.add(su1);
        assertEquals(1, e.getUnits().size());
        assertEquals(su1, e.getUnits().get(0));

        e.add(su2);
        assertEquals(2, e.getUnits().size());
        assertEquals(su2, e.getUnits().get(1));
    }

    @Test
    public void testDeleteUnitEmpty() {
        assertFalse(e.delete(su1));
        assertFalse(e.delete(su2));
    }

    @Test
    public void testDeleteUnitTypical() {
        e.add(su1);
        assertFalse(e.delete(su2));
        assertTrue(e.delete(su1));
        assertEquals(0, e.getUnits().size());
    }

    @Test
    public void testDeleteUnitMultiple() {
        e.add(su1);
        e.add(su2);

        assertTrue(e.delete(su1));
        assertEquals(1, e.getUnits().size());
        assertEquals(su2, e.getUnits().get(0));

        assertTrue(e.delete(su2));
        assertEquals(0, e.getUnits().size());
    }

    @Test
    public void testSearchUnitDoesNotExist() {
        e.add(su1);

        try {
            e.search("Test2");
            fail();
        } catch (DoesNotExistException e) {
            // pass
        }
    }

    @Test
    public void testSearchUnitTypical() {
        e.add(su1);
        e.add(su2);

        try {
            assertEquals(su1, e.search("Test1"));
            assertEquals(2, e.getUnits().size());
        } catch (DoesNotExistException e) {
            fail();
        }
    }
}
