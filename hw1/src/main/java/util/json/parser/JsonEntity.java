package util.json.parser;

import util.json.parser.enums.JsonEntityType;

import java.util.Objects;

public final class JsonEntity {
    private final String jsonEntityRepr;
    private final JsonEntityType type;

    public static JsonEntity createJsonEntity(String jsonEntityRepr) {
        String trimmed = jsonEntityRepr.trim();

        if (trimmed.startsWith("[")) {
            return new JsonEntity(trimmed, JsonEntityType.ARRAY);
        }

        if (trimmed.startsWith("\"")) {
            return new JsonEntity(trimmed, JsonEntityType.STRING);
        }

        if (trimmed.matches("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)$")) {
            return new JsonEntity(trimmed, JsonEntityType.DOUBLE);
        }

        if (trimmed.matches("^(-?)(0|([1-9][0-9]*))$")) {
            return new JsonEntity(trimmed, JsonEntityType.INTEGER);
        }

        return null;
    }

    public Object convertToObject() {
        return type.convertToObject(jsonEntityRepr);
    }

    public String getJsonEntityRepr() {
        return jsonEntityRepr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonEntity that = (JsonEntity) o;
        return jsonEntityRepr.equals(that.jsonEntityRepr) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jsonEntityRepr, type);
    }

    private JsonEntity(
            String jsonEntityRepr,
            JsonEntityType type
    ) {
        this.jsonEntityRepr = jsonEntityRepr;
        this.type = type;
    }
}
