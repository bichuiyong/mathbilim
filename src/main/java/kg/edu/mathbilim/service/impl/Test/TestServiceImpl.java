package kg.edu.mathbilim.service.impl.Test;

import kg.edu.mathbilim.dto.test.*;
import kg.edu.mathbilim.exception.nsee.AttemptNotFoundException;
import kg.edu.mathbilim.exception.nsee.TestNotFoundException;
import kg.edu.mathbilim.mapper.TestMapper;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.test.*;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.test.*;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.PDFService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.test.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;
    private final TopicRepository topicRepository;
    private final FileService fileService;
    private final PDFService pdfService;
    private final TestMapper testMapper;
    private final AttemptRepository attemptRepository;
    private final UserService userService;
    private final AttemptAnswerRepository attemptAnswerRepository;

    @Override
    public List<Topic> getTopics() {
        return topicRepository.findAll();
    }

    @Transactional
    @Override
    public void createTest(TestCreateDto dto) {
        List<MultipartFile> multipartFiles = pdfService.splitPdf(dto.getFile());
        List<Integer> pages = new ArrayList<>();
        List<MultipartFile> sorted  = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for (QuestionDto dtoQuestion : dto.getQuestions()) {
            Question ques = Question.builder()
                    .numberOrder(dtoQuestion.getNumberOrder())
                    .testPageNumber(dtoQuestion.getTestPageNumber())
                    .textFormat(dtoQuestion.isTextFormat())
                    .correctAnswer(dtoQuestion.getCorrectAnswer())
                    .weight(dtoQuestion.getWeight())
                    .topic(topicRepository.findById(dtoQuestion.getTopicId()).orElse(null))
                    .build();
            pages.add(dtoQuestion.getTestPageNumber());
            questions.add(ques);
        }
        pages = pages.stream()
                .distinct()
                .sorted(Comparator.comparingInt(pageNum -> pageNum))
                .toList();
        for (Integer pageNum : pages) {
            sorted.add(multipartFiles.get(pageNum - 1));
        }
        MultipartFile finalPdf = pdfService.mergePdfFiles(sorted, dto.getName());
        File file = fileService.uploadFileReturnEntity(finalPdf, "test");

        Test test = Test.builder()
                .name(dto.getName())
                .file(file)
                .hasLimit(dto.getHasLimit())
                .timeLimit(dto.getTimeLimit())
                .createdAt(LocalDateTime.now())
                .build();
        Test createdTest = testRepository.saveAndFlush(test);
        questions.forEach(question -> question.setTest(createdTest));

        questionRepository.saveAll(questions);



    }
    @Transactional
    @Override
    public TestDto getTestDtoForPassById(Long id) {
        Test test = testRepository.findById(id).orElseThrow(TestNotFoundException::new);
        TestDto testDto = testMapper.toDto(test);
        testDto.setQuestionCount(test.getQuestions().size());
        Attempt attempt = new Attempt();
        attempt.setTest(test);
        attempt.setUser(userService.getAuthUserEntity());
        attemptRepository.save(attempt);

        return testDto;
    }

    @Override
    @Transactional
    public void passTest(TestPassDto testPassDto, Long id) {
        List<AttemptAnswerDto> attemptAnswerDtoList = testPassDto.getAttemptAnswerDtoList();
        Test currentTest = testRepository.findById(id).orElseThrow(TestNotFoundException::new);
        User currentUser = userService.getAuthUserEntity();
        Attempt attempt = attemptRepository.findFirstByUserAndTestOrderByIdDesc(currentUser, currentTest)
                .orElseThrow(AttemptNotFoundException::new);
        List<AttemptAnswer> attemptAnswers = new ArrayList<>();
        for (AttemptAnswerDto attemptAnswerDto : attemptAnswerDtoList) {
            AttemptAnswer attemptAnswer = new AttemptAnswer();
            attemptAnswer.setChosenAnswer(attemptAnswerDto.getChosenAnswer());
            Question question = questionRepository
                    .findByTestIdAndNumberOrder(id, attemptAnswerDto.getQuestionNumber())
                    .orElse(null);
            attemptAnswer.setQuestion(question);
            attemptAnswer.setIsCorrect(question.getCorrectAnswer().equalsIgnoreCase(attemptAnswerDto.getChosenAnswer()));
            attemptAnswer.setAttempt(attempt);
            attemptAnswers.add(attemptAnswer);
        }
        attempt.setFinishedAt(LocalDateTime.now());
        attemptRepository.save(attempt);
        attemptAnswerRepository.saveAll(attemptAnswers);

    }
}
