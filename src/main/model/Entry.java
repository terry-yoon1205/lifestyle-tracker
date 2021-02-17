package model;

import exceptions.DoesNotExistException;

import java.util.ArrayList;
import java.util.List;

/**
 * Entry is an abstract class representing the second highest level of organization in this application, an object with
 * a list of SingleUnits.
 */

public abstract class Entry<T extends SingleUnit> {
    protected String name;
    protected List<T> units;

    public Entry(String name) {
        this.name = name;
        units = new ArrayList<>();
    }

    // getters
    public String getName() {
        return name;
    }

    public List<T> getUnits() {
        return units;
    }

    // EFFECTS: return the units in the entry in a formatted string representation
    public abstract String listToString();

    // setters
    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: adds given unit to the list of units
    public void add(T u) {
        units.add(u);
    }

    // MODIFIES: this
    // EFFECTS: return true if given unit can be deleted from the entry; otherwise return false
    public boolean delete(T u) {
        if (units.contains(u)) {
            units.remove(u);
            return true;
        }
        return false;
    }

    // EFFECTS: return unit with given name in the entry; if unit with given name doesn't exist, throw
    //          DoesNotExistException
    public T search(String name) throws DoesNotExistException {
        String searchName = name.toLowerCase();

        for (T u : units) {
            String unitName = u.getName().toLowerCase();

            if (unitName.equals(searchName)) {
                return u;
            }
        }
        throw new DoesNotExistException();
    }

    @Override
    public String toString() {
        return name;
    }
}
