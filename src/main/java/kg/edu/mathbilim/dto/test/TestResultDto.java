package kg.edu.mathbilim.dto.test;

import lombok.*;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestResultDto {
    private Long attemptId;
    private Long testId;
    private String wastedTime;
    private String testName;
    private String finished;
    private Double totalScoreCount;
    private Double maxScoreCount;
    private Double totalPercentage;
    private Integer questionCount;
    private Integer correctAnswersCount;
    private String timeToFinish;
    private List<TopicResultDto> topicResultDtoList;
}
