package model;

/**
 * SingleUnit is an abstract class that represents the lowest level of organization in this application, a single unit
 * of entry that has no lists attached to it.
 */

public abstract class SingleUnit {
    protected String name;

    public SingleUnit(String name) {
        this.name = name;
    }

    // getters
    public String getName() {
        return name;
    }

    // EFFECTS: return a formatted string that shows an overview of the unit
    @Override
    public abstract String toString();

    // setters
    public void setName(String name) {
        this.name = name;
    }
}
