package kg.edu.mathbilim.service.interfaces.test;

import kg.edu.mathbilim.dto.test.*;
import kg.edu.mathbilim.model.test.Topic;

import java.util.List;

public interface TestService {
    List<Topic> getTopics();

    void createTest(TestCreateDto dto);

    TestDto getTestDtoForPassById(Long id);

    Long passTest(TestPassDto testPassDto, Long id);

    TestResultDto getResultByAttemptId(Long attemptId);
}
