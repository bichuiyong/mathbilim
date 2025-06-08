package kg.edu.mathbilim.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PostType {
    NEWS(1, "NEWS"),
    BLOG(2, "BLOG"),
    ANNOUNCEMENT(3, "ANNOUNCEMENT"),
    ARTICLE(4, "ARTICLE"),
    INTERVIEW(5, "INTERVIEW"),
    REVIEW(6, "REVIEW"),
    GUIDE(7, "GUIDE"),
    STORY(8, "STORY");

    @JsonProperty("id")
    private final Integer id;

    @JsonProperty("name")
    private final String name;


    PostType(Integer id, String name) {
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


    public static PostType fromId(Integer id) {
        if (id == null) return NEWS;
        return Arrays.stream(values())
                .filter(type -> type.id.equals(id))
                .findFirst()
                .orElse(NEWS);
    }

    @JsonCreator
    public static PostType fromJson(@JsonProperty("id") Integer id) {
        return fromId(id);
    }

    public static List<PostType> getAllValues() {
        return Arrays.asList(values());
    }
}
