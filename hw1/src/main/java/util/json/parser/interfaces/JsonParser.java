package util.json.parser.interfaces;

import java.util.HashMap;

public interface JsonParser {
    HashMap<String, Object> parse(String jsonString);
}
