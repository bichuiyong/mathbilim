package kg.edu.mathbilim.dto.test;

import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestResultDto {
    private String testName;
    private String finished;
    private Double totalScoreCount;
    private Double maxScoreCount;
    private Double totalPercentage;
    private Integer questionCount;
    private Integer correctAnswersCount;
    private List<TopicResultDto> topicResultDtoList;
}
