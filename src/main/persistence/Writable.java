package persistence;

import org.json.JSONObject;

/**
 * Writable is an interface for classes with data to write and save as a JSON object.
 *
 * Interface taken from:
 *  JsonSerializationDemo source code
 *      by Paul Carter
 *      https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
