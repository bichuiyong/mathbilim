package kg.edu.mathbilim.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Category {
    ALGEBRA(1, "ALGEBRA"),
    GEOMETRY(2, "GEOMETRY"),
    PLANE_GEOMETRY(3, "PLANE_GEOMETRY"),
    DISCRETE_MATHEMATICS(4, "DISCRETE_MATHEMATICS"),
    CALCULUS(5, "CALCULUS"),
    COMBINATORICS(6, "COMBINATORICS"),
    TOPOLOGY(7, "TOPOLOGY"),
    NUMBER_THEORY(8, "NUMBER_THEORY"),
    LINEAR_ALGEBRA(9, "LINEAR_ALGEBRA"),
    PROBABILITY(10, "PROBABILITY"),
    STATISTICS(11, "STATISTICS"),
    LOGIC(12, "LOGIC"),
    SET_THEORY(13, "SET_THEORY"),
    GRAPH_THEORY(14, "GRAPH_THEORY"),
    DIFFERENTIAL_EQUATIONS(15, "DIFFERENTIAL_EQUATIONS"),
    GAME_THEORY(16, "GAME_THEORY"),
    MATHEMATICAL_ANALYSIS(17, "MATHEMATICAL_ANALYSIS"),
    FUNCTIONS(18, "FUNCTIONS"),
    TRIGONOMETRY(19, "TRIGONOMETRY"),
    VECTOR_CALCULUS(20, "VECTOR_CALCULUS");

    @JsonProperty("id")
    private final Integer id;

    @JsonProperty("name")
    private final String name;


    Category(Integer id, String name) {
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


    public static Category fromId(Integer id) {
        if (id == null) return ALGEBRA;
        return Arrays.stream(values())
                .filter(category -> category.id.equals(id))
                .findFirst()
                .orElse(ALGEBRA);
    }

    @JsonCreator
    public static Category fromJson(@JsonProperty("id") Integer id) {
        return fromId(id);
    }

    public static List<Category> getAllValues() {
        return Arrays.asList(values());
    }
}
