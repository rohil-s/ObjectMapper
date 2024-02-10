package jackson;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Serializer {
    JACKSON("Jackson"),
    GSON("Gson");
    // JACKSON,
    // GSON;

    private final String serializer;

    Serializer(String name) {
        serializer = name;
    }

    @JsonValue
    public String getValue() {
        return serializer;
    }
}
