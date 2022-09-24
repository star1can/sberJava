package util.json.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import util.json.parser.interfaces.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class JsonParserComplexTest {
    private String jsonTestString;
    private final String[] inputs = {"complexTest/json-with-json-string.json"};
    private final Iterator<String> nextInput = Arrays.asList(inputs).iterator();

    @BeforeEach
    void setNextJsonTestString() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource(nextInput.next())).getPath();
        jsonTestString = Files.readString(Paths.get(path));
    }

    @Test
    @Order(1)
    @Tag("ComplexTest")
    void testParseJsonWithJsonStringValue() {
        JsonParser parser = new JsonParserImpl();
        var parsed = parser.parse(jsonTestString);
        String string = "string";
        int integer = 3222;
        double d = 1234.56;
        Double[] arrayDoubleExpected = {42.42, 666.66, 777.777, 888.888};
        Integer[] arrayIntegerExpected = {1, 2, 3, 4, 5, 6, 7};
        String jsonString = "{\"string\"        : \"string\", \"integer\"       : 3222, \"double\"        : 1234.56, \"arrayDouble\"   : [42.42, 666.66, 777.777, 888.888],  \"arrayInteger\"  : [1, 2, 3, 4, 5, 6, 7],  \"arrayString\"   : [\"a\", \"b\", \"{  \"string\"        : \"Dmitrii\",  \"integer\"       : 3222,  \"double\"        : 1234.56,  \"arrayDouble\"   : [42.42, 666.66, 777.777, 888.888],  \"arrayInteger\"  : [1, 2, 3, 4, 5, 6, 7]}\"]}";
        assertAll(
                () -> assertNotEquals(parsed, null),
                () -> assertEquals(string, parsed.get("string")),
                () -> assertEquals(integer, parsed.get("integer")),
                () -> assertEquals(d, parsed.get("double")),
                () -> assertEquals(jsonString, parsed.get("jsonString")),
                () -> {
                    Object[] objectArray = (Object[]) parsed.get("arrayDouble");
                    Double[] arrayDouble = Arrays.copyOf(objectArray, objectArray.length, Double[].class);
                    assertTrue(Arrays.deepEquals(arrayDouble, arrayDoubleExpected));
                },
                () -> {
                    Object[] objectArray = (Object[]) parsed.get("arrayInteger");
                    Integer[] arrayInteger = Arrays.copyOf(objectArray, objectArray.length, Integer[].class);
                    assertTrue(Arrays.deepEquals(arrayInteger, arrayIntegerExpected));
                }
        );
        System.out.println("======TEST ONE SUCCESSFUL=======");
    }
}
