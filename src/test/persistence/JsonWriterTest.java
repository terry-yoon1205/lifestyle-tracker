package persistence;

import exceptions.InvalidInputException;
import exceptions.NotInitializedException;
import model.person.Person;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static model.person.ActivityLevel.*;
import static model.person.GoalIntensity.*;
import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterInitialState() {
        try {
            Person p = new Person("Initial State", true, 20, 170, 70, LIGHTLY_ACTIVE);
            JsonWriter writer = new JsonWriter("./data/testWriterInitial.json");
            writer.open();
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterInitial.json");
            p = reader.read();
            checkInitial(p);
        } catch (IOException e) {
            fail("Unexpected IOException thrown");
        }
    }

    @Test
    public void testWriterGeneral() {
        try {
            Person p = new Person("General", false, 19, 165, 55, MODERATELY_ACTIVE);
            p.setWeightGoal(60);
            p.setGoalIntensity(LIGHT_GAIN);
            p.setCalorieGoal(2288);
            p.setMacroGoal(0.5, 0.25, 0.25);
            setUpPerson(p);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneral.json");
            writer.open();
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneral.json");
            p = reader.read();
            checkGeneral(p);
        } catch (IOException | InvalidInputException | NotInitializedException e) {
            fail("Exception thrown unexpectedly");
        }
    }
}
