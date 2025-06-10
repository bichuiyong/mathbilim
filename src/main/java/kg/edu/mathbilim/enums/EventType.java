package kg.edu.mathbilim.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EventType {
    OLYMPIAD(1, "OLYMPIAD"),
    FORUM(2, "FORUM"),
    MEETUP(3, "MEETUP"),
    WEBINAR(4, "WEBINAR"),
    WORKSHOP(5, "WORKSHOP"),
    CONFERENCE(6, "CONFERENCE"),
    COMPETITION(7, "COMPETITION"),
    LECTURE(8, "LECTURE"),
    COURSE(9, "COURSE"),
    EXHIBITION(10, "EXHIBITION");

    @JsonProperty("id")
    private final Integer id;

    @JsonProperty("name")
    private final String name;


    EventType(Integer id, String name) {
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

    public static EventType fromId(Integer id) {
        if (id == null) return OLYMPIAD;
        return Arrays.stream(values())
                .filter(type -> type.id.equals(id))
                .findFirst()
                .orElse(OLYMPIAD);
    }

    @JsonCreator
    public static EventType fromJson(@JsonProperty("id") Integer id) {
        return fromId(id);
    }

    public static List<EventType> getAllValues() {
        return Arrays.asList(values());
    }
}
