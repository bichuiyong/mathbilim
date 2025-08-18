package kg.edu.mathbilim.service.interfaces.test;

import kg.edu.mathbilim.dto.test.*;
import kg.edu.mathbilim.model.test.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TestService {

    Page<TestsListDto> getTests(String keyword, Pageable pageable);

    TestsListDto getTestById(long id);

    List<Topic> getTopics();

    void createTest(TestCreateDto dto);

    TestDto getTestDtoForPassById(Long id);

    Long passTest(TestPassDto testPassDto, Long id);

    TestResultDto getResultByAttemptId(Long attemptId, String username);

    List<Map<String, String>> getLastResultsForUser(String username, Long testId);
}
