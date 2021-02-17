package persistence;

import model.person.Person;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/nonexistent.json");
        try {
            Person p = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderInitialState() {
        JsonReader reader = new JsonReader("./data/testReaderInitial.json");
        try {
            Person p = reader.read();
            checkInitial(p);
        } catch (IOException e) {
            fail("Unexpected IOException thrown");
        }
    }

    @Test
    public void testReaderGeneral() {
        JsonReader reader = new JsonReader("./data/testReaderGeneral.json");
        try {
            Person p = reader.read();
            checkGeneral(p);
        } catch (IOException e) {
            fail("Unexpected IOException thrown");
        }
    }
}
