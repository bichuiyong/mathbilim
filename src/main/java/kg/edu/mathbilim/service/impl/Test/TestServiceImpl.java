package kg.edu.mathbilim.service.impl.Test;

import kg.edu.mathbilim.dto.test.QuestionDto;
import kg.edu.mathbilim.dto.test.TestCreateDto;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.test.Question;
import kg.edu.mathbilim.model.test.Test;
import kg.edu.mathbilim.model.test.Topic;
import kg.edu.mathbilim.repository.test.QuestionRepository;
import kg.edu.mathbilim.repository.test.TestRepository;
import kg.edu.mathbilim.repository.test.TopicRepository;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.test.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;
    private final TopicRepository topicRepository;
    private final FileService fileService;

    @Override
    public List<Topic> getTopics() {
        return topicRepository.findAll();
    }

    @Override
    public void createTest(TestCreateDto dto) {
        File file = fileService.uploadFileReturnEntity(dto.getFile(),"test");

        Test test = Test.builder()
                .name(dto.getName())
                .file(file)
                .hasLimit(dto.getHasLimit())
                .timeLimit(dto.getTimeLimit())
                .createdAt(LocalDateTime.now())
                .build();
        testRepository.saveAndFlush(test);

        List<Question> questions = new ArrayList<>();

        for (QuestionDto dtoQuestion : dto.getQuestions()) {
            Question ques = Question.builder()
                    .test(test)
                    .numberOrder(dtoQuestion.getNumberOrder())
                    .testPageNumber(dtoQuestion.getTestPageNumber())
                    .textFormat(dtoQuestion.isTextFormat())
                    .correctAnswer(dtoQuestion.getCorrectAnswer())
                    .weight(dtoQuestion.getWeight())
                    .topic(topicRepository.findById(dtoQuestion.getTopicId()).orElse(null))
                    .build();
            questions.add(ques);
        }
        questionRepository.saveAll(questions);
    }
}
