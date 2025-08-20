package kg.edu.mathbilim.dto.test;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicResultDto {
    private Integer topicId;
    private String topicName;
    private Double totalScoreCount;
    private Double maxScoreCount;
    private Double percentage;
}
