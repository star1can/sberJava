package util.json.parser;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import util.json.parser.interfaces.JsonParser;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class JsonParserSimpleTest {
    @Test
    @Order(1)
    @Tag("SimpleTest")
    void testParseJsonStringEmpty() {
        String jsonString = "{     }";
        JsonParser parser = new JsonParserImpl();
        var parsed = parser.parse(jsonString);
        assertAll(
                () -> assertNotEquals(parsed, null),
                () -> assertTrue(parsed.isEmpty())
        );
        System.out.println("======TEST ONE SUCCESSFUL=======");
    }

    @Test
    @Order(2)
    @Tag("SimpleTest")
    void testParseJsonStringStringValue() {
        String jsonString = "{\n  \"a\": \"b\"\n}";
        JsonParser parser = new JsonParserImpl();
        var parsed = parser.parse(jsonString);
        assertAll(
                () -> assertNotEquals(parsed, null),
                () -> assertEquals(parsed.get("a"), "b")
        );
        System.out.println("======TEST TWO SUCCESSFUL=======");
    }

    @Test
    @Order(3)
    @Tag("SimpleTest")
    void testParseJsonIntegerValue() {
        String jsonString = "{\n  \"i\": 1 \n}";
        JsonParser parser = new JsonParserImpl();
        var parsed = parser.parse(jsonString);
        assertAll(
                () -> assertNotEquals(parsed, null),
                () -> assertEquals(parsed.get("i"), 1)
        );
        System.out.println("======TEST THREE SUCCESSFUL=======");
    }

    @Test
    @Order(4)
    @Tag("SimpleTest")
    void testParseJsonDoubleValue() {
        String jsonString = "{\n  \"j\": 1.123 \n}";
        JsonParser parser = new JsonParserImpl();
        var parsed = parser.parse(jsonString);
        assertAll(
                () -> assertNotEquals(parsed, null),
                () -> assertEquals(parsed.get("j"), 1.123)
        );
        System.out.println("======TEST FOUR SUCCESSFUL=======");
    }

    @Test
    @Order(5)
    @Tag("SimpleTest")
    void testParseJsonStringArrayValue() {
        String jsonString = "{\n  \"array\": [\"a\", \"b\", \"c\"] \n}";
        JsonParser parser = new JsonParserImpl();
        var parsed = parser.parse(jsonString);
        assertAll(
                () -> assertNotEquals(parsed, null),
                () -> {
                    Object[] objectArray = (Object[]) parsed.get("array");
                    String[] stringArray = Arrays.copyOf(objectArray, objectArray.length, String[].class);
                    String[] expected = {"a", "b", "c"};
                    assertTrue(Arrays.deepEquals(expected, stringArray));
                }
        );
        System.out.println("======TEST FIVE SUCCESSFUL=======");
    }
}
