package kg.edu.mathbilim.enums;

public enum Metadata {
    AUTHOR("Author"),
    PUBLISHING_YEAR("Published year"),
    DESCRIPTION("Description");

    private final String label;

    Metadata(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
