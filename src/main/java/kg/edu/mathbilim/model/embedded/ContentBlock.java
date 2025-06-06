package kg.edu.mathbilim.model.embedded;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentBlock {
    private String type;
    private String value;
}
