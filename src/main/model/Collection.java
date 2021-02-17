package model;

import exceptions.DoesNotExistException;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection is an abstract class the represents the highest level of organization in this application. It contains a
 * list of Entries.
 */

public abstract class Collection<T extends Entry<? extends SingleUnit>> {
    protected List<T> entries;

    public Collection() {
        entries = new ArrayList<>();
    }

    // getters
    public List<T> getEntries() {
        return entries;
    }

    // EFFECTS: return the collection in a formatted string representation
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (T e : entries) {
            String line = e.getName() + "\n";
            result.append(line);
        }

        return result.toString();
    }

    // MODIFIES: this
    // EFFECTS: add an entry to the collection
    public void add(T e) {
        entries.add(e);
    }

    // MODIFIES: this
    // EFFECTS: return true if given entry can be removed from the collection; otherwise return false
    public boolean delete(T e) {
        if (entries.contains(e)) {
            entries.remove(e);
            return true;
        }
        return false;
    }

    // EFFECTS: return the entry in the collection with given name; if entry with given name does not exist, throw
    //          DoesNotExistException
    public T search(String name) throws DoesNotExistException {
        String searchName = name.toLowerCase();

        for (T e : entries) {
            String entryName = e.getName().toLowerCase();

            if (entryName.equals(searchName)) {
                return e;
            }
        }
        throw new DoesNotExistException();
    }
}
