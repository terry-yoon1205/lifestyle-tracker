package model.routine;

import model.Collection;

/**
 * RoutineCollection represents a collection of multiple workout routines by the user. Each routine collection is
 * identifiable by the user that owns the collection, and has a list of routines.
 */

public class RoutineCollection extends Collection<Routine> {

    // EFFECTS: routine collection has no routines
    public RoutineCollection() {
        super();
    }
}
