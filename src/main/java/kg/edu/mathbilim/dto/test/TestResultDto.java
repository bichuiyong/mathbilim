package kg.edu.mathbilim.dto.test;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestResultDto {
    private Double totalScoreCount;
    private Double maxScoreCount;
    private Double totalPercentage;
    private List<TopicResultDto> topicResultDtoList;

}
