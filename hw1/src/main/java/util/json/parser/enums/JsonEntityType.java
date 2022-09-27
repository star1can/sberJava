package util.json.parser.enums;

import util.json.parser.JsonEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static util.json.parser.JsonEntity.createJsonEntity;

public enum JsonEntityType {
    INTEGER {
        @Override
        public Object convertToObject(String jsonEntityStrRepr) {
            return Integer.parseInt(jsonEntityStrRepr);
        }
    },
    DOUBLE {
        @Override
        public Object convertToObject(String jsonEntityStrRepr) {
            return Double.parseDouble(jsonEntityStrRepr);
        }
    },

    ARRAY {
        private JsonEntity extractString(String arrayMembers) {
            String valueQuotesRegex = "((?<=[^\\\\])\")|(^\")";
            Pattern pattern = Pattern.compile(valueQuotesRegex);
            Matcher matcher = pattern.matcher(arrayMembers.substring(1));

            if (!matcher.find()) return null;

            return createJsonEntity(arrayMembers.substring(0, matcher.start() + 2));
        }

        private JsonEntity extractNumber(String arrayMembers) {
            if (!arrayMembers.matches("^[+-]?\\d.*")) return null;

            String numberRegex = "^[+-]?([0-9]*[.])?[0-9]+";
            Matcher matcher = Pattern.compile(numberRegex).matcher(arrayMembers);

            if (!matcher.find()) return null;

            return createJsonEntity(arrayMembers.substring(matcher.start(), matcher.end()));
        }

        @Override
        public Object convertToObject(String jsonEntityStrRepr) {
            String arrayMembers = jsonEntityStrRepr.substring(1, jsonEntityStrRepr.length() - 1).trim();
            List<Object> arrayObjects = new ArrayList<>();

            if (arrayMembers.isEmpty()) return arrayObjects;

            for (int i = 0; i < arrayMembers.length(); ++i) {

                JsonEntity jsonEntity;
                if (arrayMembers.charAt(i) == '"') {
                    jsonEntity = extractString(arrayMembers.substring(i));

                    if(jsonEntity == null) return null;
                } else {
                    jsonEntity = extractNumber(arrayMembers.substring(i));
                }

                if (jsonEntity != null) {
                    i += jsonEntity.getJsonEntityRepr().length();
                    arrayObjects.add(jsonEntity.convertToObject());
                }
            }

            return arrayObjects.toArray();
        }
    },
    STRING {
        @Override
        public Object convertToObject(String jsonEntityStrRepr) {
            String jsonStringWithoutQuotes = jsonEntityStrRepr.substring(1, jsonEntityStrRepr.length() - 1);
            return jsonStringWithoutQuotes.replaceAll("\\\\\"", "\"");
        }
    };

    public abstract Object convertToObject(String jsonEntityStrRepr);
}
