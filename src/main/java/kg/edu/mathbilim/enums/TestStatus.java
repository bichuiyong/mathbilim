package kg.edu.mathbilim.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TestStatus {
    NOT_STARTED(1, "NOT_STARTED"),
    IN_PROGRESS(2, "IN_PROGRESS"),
    SUBMITTED(3, "SUBMITTED"),
    CANCELLED(4, "CANCELLED"),
    TIMEOUT(5, "TIMEOUT");

    @JsonProperty("id")
    private final Integer id;

    @JsonProperty("name")
    private final String name;

    TestStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty("id")
    public Integer getId() { return id; }

    @JsonProperty("name")
    public String getName() { return name; }

    public static TestStatus fromId(Integer id) {
        if (id == null) return NOT_STARTED;
        return Arrays.stream(values())
                .filter(status -> status.id.equals(id))
                .findFirst()
                .orElse(NOT_STARTED);
    }

    @JsonCreator
    public static TestStatus fromJson(@JsonProperty("id") Integer id) {
        return fromId(id);
    }

    public static List<TestStatus> getAllValues() {
        return Arrays.asList(values());
    }
}
