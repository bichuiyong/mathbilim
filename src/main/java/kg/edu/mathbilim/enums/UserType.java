package kg.edu.mathbilim.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserType {
    SCHOOLBOY(1, "SCHOOLBOY"),
    STUDENT(2, "STUDENT"),
    TEACHER(3, "TEACHER"),
    EXPLORER(4, "EXPLORER");

    @JsonProperty("id")
    private final Integer id;

    @JsonProperty("name")
    private final String name;

    UserType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty("id")
    public Integer getId() { return id; }

    @JsonProperty("name")
    public String getName() { return name; }

    public static UserType fromId(Integer id) {
        if (id == null) return SCHOOLBOY;
        return Arrays.stream(values())
                .filter(type -> type.id.equals(id))
                .findFirst()
                .orElse(SCHOOLBOY);
    }

    @JsonCreator
    public static UserType fromJson(@JsonProperty("id") Integer id) {
        return fromId(id);
    }

    public static List<UserType> getAllValues() {
        return Arrays.asList(values());
    }
}