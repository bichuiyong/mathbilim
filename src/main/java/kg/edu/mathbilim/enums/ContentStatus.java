package kg.edu.mathbilim.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ContentStatus {
    DRAFT(1, "DRAFT"),
    PENDING_REVIEW(2, "PENDING_REVIEW"),
    APPROVED(3, "APPROVED"),
    REJECTED(4, "REJECTED");

    @JsonProperty("id")
    private final Integer id;

    @JsonProperty("name")
    private final String name;

    ContentStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public static ContentStatus fromId(Integer id) {
        if (id == null) return DRAFT;
        return Arrays.stream(values())
                .filter(status -> status.id.equals(id))
                .findFirst()
                .orElse(DRAFT);
    }

    public static ContentStatus fromName(String name) {
        if (name == null) return DRAFT;
        return Arrays.stream(values())
                .filter(status -> status.name.equalsIgnoreCase(name))
                .findFirst()
                .orElse(DRAFT);
    }

    @JsonCreator
    public static ContentStatus fromJson(@JsonProperty("id") Integer id) {
        return fromId(id);
    }

    public static List<ContentStatus> getAllValues() {
        return Arrays.asList(values());
    }
}