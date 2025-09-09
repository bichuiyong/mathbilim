package kg.edu.mathbilim.service.impl.Test;

import jakarta.ws.rs.NotFoundException;
import kg.edu.mathbilim.dto.test.*;
import kg.edu.mathbilim.exception.nsee.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
    public Page<TestsListDto> getTests(String keyword, Pageable pageable) {
        Page<Test> page;
        if (keyword == null || keyword.isEmpty()) {
            page = testRepository.findAllByDeletedFalse(pageable);
        } else {
            page = testRepository.findByNameContainingIgnoreCaseAndDeletedFalse(keyword, pageable);
        }

        return page.map(test -> {
            TestsListDto dto = new TestsListDto();
            dto.setId(test.getId());
            dto.setName(test.getName());
            dto.setDescription(test.getDescription());
            dto.setHasLimit(test.getHasLimit());
            dto.setTimeLimit(test.getTimeLimit());
            dto.setQuestionCount(test.getQuestions().size());
            return dto;
        });
    }

    @Override
    public TestsListDto getTestById(long id) {
        return testRepository.findByIdAndDeletedFalse(id)
                .map(test -> {
                    TestsListDto dto = new TestsListDto();
                    dto.setId(test.getId());
                    dto.setName(test.getName());
                    dto.setDescription(test.getDescription());
                    dto.setHasLimit(test.getHasLimit());
                    dto.setTimeLimit(test.getTimeLimit());
                    dto.setQuestionCount(test.getQuestions().size());
                    return dto;
                })
                .orElseThrow(() -> new NotFoundException("Test not found"));
    }

    @Override
    public List<Topic> getTopics() {
        return topicRepository.findAll();
    }

    @Transactional
    @Override
    public void createTest(TestCreateDto dto) throws TopicNotFoundException {
        List<MultipartFile> multipartFiles = pdfService.splitPdf(dto.getFile());
        List<Integer> pages = new ArrayList<>();
        List<MultipartFile> sorted  = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        int pageCount = 0;
        int tempPageCount = 0;

        for (QuestionDto dtoQuestion : dto.getQuestions()) {
            if (tempPageCount != dtoQuestion.getTestPageNumber()) {
                tempPageCount = dtoQuestion.getTestPageNumber();
                pageCount++;
            }
            Question ques = Question.builder()
                    .numberOrder(dtoQuestion.getNumberOrder())
                    .testPageNumber(pageCount)
                    .textFormat(dtoQuestion.isTextFormat())
                    .correctAnswer(dtoQuestion.getCorrectAnswer())
                    .weight(dtoQuestion.getWeight())
                    .topic(topicRepository.findById(dtoQuestion.getTopicId()).orElseThrow(TopicNotFoundException::new))
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
                .description(dto.getDescription())
                .createdAt(LocalDateTime.now())
                .build();
        Test createdTest = testRepository.saveAndFlush(test);
        questions.forEach(question -> question.setTest(createdTest));

        questionRepository.saveAll(questions);
    }

    @Transactional
    @Override
    public TestDto getTestDtoForPassById(Long id) {
        Test test = testRepository.findByIdAndDeletedFalse(id).orElseThrow(TestNotFoundException::new);
        List<QuestionDto> questions = test.getQuestions().stream()
                .map(q -> QuestionDto.builder()
                        .textFormat(q.getTextFormat())
                        .numberOrder(q.getNumberOrder())
                        .testPageNumber(q.getTestPageNumber())
                        .build())
                .toList();

        TestDto testDto = testMapper.toDto(test);
        testDto.setQuestionCount(test.getQuestions().size());
        testDto.setQuestionDtoList(questions);
        testDto.setHasLimit(test.getHasLimit());

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
        Test currentTest = testRepository.findByIdAndDeletedFalse(id).orElseThrow(TestNotFoundException::new);
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
    public TestResultDto getResultByAttemptId(Long attemptId, String username) {
        Attempt attempt = attemptRepository.findByIdWithAnswersAndTopics(attemptId)
                .orElseThrow(AttemptNotFoundException::new);

        User user = userService.findByEmail(username);
        if (!Objects.equals(user.getId(), attempt.getUser().getId())) {
            throw new NotOwnResult();
        }

        List<AttemptAnswer> attemptAnswers = attempt.getAttemptAnswers();
        double totalScore = 0.0;
        double maxScore = 0.0;
        int correctAnswersCount = 0;

        for (AttemptAnswer aa : attemptAnswers) {
            maxScore += aa.getQuestion().getWeight();
            if (aa.getIsCorrect()) {
                totalScore += aa.getQuestion().getWeight();
                correctAnswersCount++;
            }
        }

        double totalPercentage = maxScore > 0 ? (totalScore / maxScore) * 100 : 0.0;

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String wastedTime = "0 мин 0 сек";
        if (attempt.getStartedAt() != null && attempt.getFinishedAt() != null) {
            long seconds = ChronoUnit.SECONDS.between(attempt.getStartedAt(), attempt.getFinishedAt());
            long minutes = seconds / 60;
            long remainingSeconds = seconds % 60;
            wastedTime = String.format("%d мин %d сек", minutes, remainingSeconds);
        }
        String timeToFinish = "";
        if (attempt.getTest().getHasLimit()) {
            timeToFinish = attempt.getTest().getTimeLimit() + " мин";
        } else {
            timeToFinish = "none";
        }

        return TestResultDto.builder()
                .attemptId(attempt.getId())
                .wastedTime(wastedTime)
                .testId(attempt.getTest().getId())
                .testName(attempt.getTest().getName())
                .finished(attempt.getFinishedAt().format(formatter))
                .questionCount(attempt.getTest().getQuestions().size())
                .correctAnswersCount(correctAnswersCount)
                .totalScoreCount(totalScore)
                .maxScoreCount(maxScore)
                .timeToFinish(timeToFinish)
                .totalPercentage(totalPercentage)
                .topicResultDtoList(topicResults)
                .build();
    }

    @Override
    public List<Map<String, String>> getLastResultsForUser(String username, Long testId) {
        User user = userService.findByEmail(username);
        List<Attempt> attempts = attemptRepository.findTop5ByUserAndTestIdOrderByFinishedAtDesc(user, testId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        List<Map<String, String>> result = new ArrayList<>();

        for (Attempt attempt : attempts) {
            if (attempt.getFinishedAt() == null) {
                continue;
            }
            Map<String, String> map = new HashMap<>();
            map.put(String.valueOf(attempt.getId()), attempt.getFinishedAt().format(formatter));
            result.add(map);
        }

        return result;
    }

    @Override
    public void deleteTestById(Long id) {
        testRepository.deleteByIdAndDeletedFalse(id);
    }


}
