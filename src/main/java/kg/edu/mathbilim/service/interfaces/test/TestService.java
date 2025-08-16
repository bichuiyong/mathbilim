package kg.edu.mathbilim.service.interfaces.test;

import kg.edu.mathbilim.dto.test.AttemptAnswerDto;
import kg.edu.mathbilim.dto.test.TestCreateDto;
import kg.edu.mathbilim.dto.test.TestDto;
import kg.edu.mathbilim.dto.test.TestPassDto;
import kg.edu.mathbilim.model.test.Topic;

import java.util.List;

public interface TestService {
    List<Topic> getTopics();

    void createTest(TestCreateDto dto);

    TestDto getTestDtoForPassById(Long id);

    void passTest(TestPassDto testPassDto, Long id);
}
