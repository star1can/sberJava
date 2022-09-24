package util.json.parser;

import util.json.parser.interfaces.JsonParser;

import java.util.*;

public final class JsonParserImpl implements JsonParser {
    private static final String JSON_COLON_SPLIT_REGEX = "(?<=[^\\\\]\")(\\s*:\\s*)";
    private static final String ARRAY_VALUE_KEY_COMMA_SPLIT_REGEX = "(?<=])(\\s*,\\s*(?=\"))";
    private static final String NOT_ARRAY_VALUE_KEY_COMMA_SPLIT_REGEX = "(?<=[\\]\\d\"])(\\s*,\\s*)(?=[\"])";

    @Override
    public HashMap<String, Object> parse(String jsonString) {
        String keyValueJsonString = removeBracketsFromJsonString(jsonString);
        String[] valueKeyJsonStrings = splitKeyValueJsonStringByColon(keyValueJsonString);
        var keyJsonEntityMap = getKeyJsonEntityMap(valueKeyJsonStrings);

        try {
            return convertJsonEntitiesToObjects(keyJsonEntityMap);
        } catch (Exception e) {
            return null;
        }
    }

    private String removeQuotesFromJsonKey(String key) {
        return key.replaceAll("(^\"|\"$)", "");
    }

    private String removeBracketsFromJsonString(String jsonString) {
        return jsonString.replaceAll("(^\\{\\s*)|(\\s*}$)", "");
    }

    private String[] splitKeyValueJsonStringByColon(String keyValueJsonString) {
        return keyValueJsonString.split(JSON_COLON_SPLIT_REGEX);
    }

    private String[] splitValueKeyJsonStringByComma(String valueKeyJsonString) {
        String valueKeyJsonStringTrimmed = valueKeyJsonString.trim();

        if (valueKeyJsonStringTrimmed.startsWith("[")) {
            return valueKeyJsonStringTrimmed.split(ARRAY_VALUE_KEY_COMMA_SPLIT_REGEX);
        }

        return valueKeyJsonStringTrimmed.split(NOT_ARRAY_VALUE_KEY_COMMA_SPLIT_REGEX);
    }

    private HashMap<String, Object> convertJsonEntitiesToObjects(HashMap<String, JsonEntity> keyJsonEntityMap) {
        HashMap<String, Object> result = new HashMap<>();

        for (Map.Entry<String, JsonEntity> entry : keyJsonEntityMap.entrySet()) {
            result.put(entry.getKey(), entry.getValue().convertToObject());
        }

        return result;
    }

    private HashMap<String, JsonEntity> getKeyJsonEntityMap(String[] valueKeyJsonStrings) {
        HashMap<String, JsonEntity> result = new HashMap<>();
        String key = null;

        for (var valueKeyJsonString : valueKeyJsonStrings) {

            String[] valueKeyPair = splitValueKeyJsonStringByComma(valueKeyJsonString);

            if (key == null) {
                key = removeQuotesFromJsonKey(valueKeyPair[valueKeyPair.length - 1]);
                continue;
            }

            result.put(key, JsonEntity.createJsonEntity(valueKeyPair[0]));

            key = removeQuotesFromJsonKey(valueKeyPair[valueKeyPair.length - 1]);
        }

        return result;
    }
}
