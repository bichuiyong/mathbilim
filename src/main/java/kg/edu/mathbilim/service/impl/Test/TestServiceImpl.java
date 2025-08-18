package kg.edu.mathbilim.service.impl.Test;

import kg.edu.mathbilim.dto.test.*;
import kg.edu.mathbilim.exception.nsee.AttemptNotFoundException;
import kg.edu.mathbilim.exception.nsee.QuestionNotFoundException;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    public Long passTest(TestPassDto testPassDto, Long id) {
        List<AttemptAnswerDto> attemptAnswerDtoList = testPassDto.getAttemptAnswerDtoList();
        Test currentTest = testRepository.findById(id).orElseThrow(TestNotFoundException::new);
        User currentUser = userService.getAuthUserEntity();
        Attempt attempt = attemptRepository.findFirstByUserAndTestOrderByIdDesc(currentUser, currentTest)
                .orElseThrow(AttemptNotFoundException::new);
        List<AttemptAnswer> attemptAnswers = new ArrayList<>();
        Map<Integer, Question> questionMap = questionRepository.findAllByTestId(id).stream()
                .collect(Collectors.toMap(Question::getNumberOrder, q -> q));
        double score = 0D;
        for (AttemptAnswerDto attemptAnswerDto : attemptAnswerDtoList) {
            Question question = questionMap.get(attemptAnswerDto.getQuestionNumber());
            if (question == null) {
                throw new QuestionNotFoundException();
            }

            AttemptAnswer attemptAnswer = new AttemptAnswer();
            attemptAnswer.setChosenAnswer(attemptAnswerDto.getChosenAnswer());
            attemptAnswer.setQuestion(question);
            attemptAnswer.setAttempt(attempt);

            boolean isCorrect = question.getCorrectAnswer().equalsIgnoreCase(attemptAnswerDto.getChosenAnswer());
            attemptAnswer.setIsCorrect(isCorrect);

            if (isCorrect) {
                score += question.getWeight();
            }

            attemptAnswers.add(attemptAnswer);
        }
        attempt.setFinishedAt(LocalDateTime.now());
        attempt.setScore(score);

        attemptRepository.save(attempt);
        attemptAnswerRepository.saveAll(attemptAnswers);

        return attempt.getId();

    }

    @Override
    public TestResultDto getResultByAttemptId(Long attemptId) {
        Attempt attempt = attemptRepository.findByIdWithAnswersAndTopics(attemptId).orElseThrow(AttemptNotFoundException::new);
        List<AttemptAnswer> attemptAnswers = attempt.getAttemptAnswers();
        double totalScore = 0.0;
        double maxScore = 0.0;

        for (AttemptAnswer aa : attemptAnswers) {
            maxScore += aa.getQuestion().getWeight();
            if (aa.getIsCorrect()) {
                totalScore += aa.getQuestion().getWeight();
            }
        }
        double totalPercentage = (totalScore / maxScore) * 100;

        Map<Topic, Double> scorePerTopic = new HashMap<>();
        Map<Topic, Double> maxPerTopic = new HashMap<>();

        for (AttemptAnswer aa : attemptAnswers) {
            Topic topic = aa.getQuestion().getTopic();

            scorePerTopic.putIfAbsent(topic, 0.0);
            maxPerTopic.putIfAbsent(topic, 0.0);

            maxPerTopic.put(topic, maxPerTopic.get(topic) + aa.getQuestion().getWeight());

            if (aa.getIsCorrect()) {
                scorePerTopic.put(topic, scorePerTopic.get(topic) + aa.getQuestion().getWeight());
            }
        }

        List<TopicResultDto> topicResults = new ArrayList<>();
        for (Topic topic : scorePerTopic.keySet()) {
            double percent = (scorePerTopic.get(topic) / maxPerTopic.get(topic)) * 100;
            topicResults.add(new TopicResultDto(
                    topic.getId(),
                    topic.getName(),
                    scorePerTopic.get(topic),
                    maxPerTopic.get(topic),

                    percent
            ));
        }

        return TestResultDto.builder()
                .totalScoreCount(totalScore)
                .maxScoreCount(maxScore)
                .totalPercentage(totalPercentage)
                .topicResultDtoList(topicResults)
                .build();
    }


}
