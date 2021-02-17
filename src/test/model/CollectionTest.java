package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class CollectionTest<B extends Collection<T>, T extends Entry<E>, E extends SingleUnit> {
    public B c;
    public T e1;
    public T e2;
    public E su1;
    public E su2;
    public E su3;
    public E su4;

    @Test
    public abstract void testConstructor();

    @Test
    public abstract void testGetCollectionFormatted();

    @Test
    public void testAddEntry() {
        c.add(e1);
        assertEquals(1, c.getEntries().size());
        assertEquals(e1, c.getEntries().get(0));

        c.add(e2);
        assertEquals(2, c.getEntries().size());
        assertEquals(e2, c.getEntries().get(1));
    }

    @Test
    public void testDeleteEntryEmpty() {
        assertFalse(c.delete(e1));
        assertFalse(c.delete(e2));
    }

    @Test
    public void testDeleteEntryTypical() {
        c.add(e1);
        assertFalse(c.delete(e2));
        assertTrue(c.delete(e1));
        assertEquals(0, c.getEntries().size());
    }

    @Test
    public void testDeleteEntryMultiple() {
        c.add(e1);
        c.add(e2);

        assertTrue(c.delete(e1));
        assertEquals(1, c.getEntries().size());
        assertEquals(e2, c.getEntries().get(0));

        assertTrue(c.delete(e2));
        assertEquals(0, c.getEntries().size());
    }

    @Test
    public abstract void testSearchEntryDoesNotExist();

    @Test
    public abstract void testSearchEntryTypical();
}
