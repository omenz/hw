package es.nitaur.api.quiz;

import es.nitaur.api.quiz.dto.*;
import es.nitaur.persistence.model.quiz.*;
import es.nitaur.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/quiz", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public Collection<QuizDTO> getQuizzes() {
        return mapList(quizService.findAll(), this::toDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<QuizDTO> getQuiz(@PathVariable final Long id) {
        return Optional.ofNullable(quizService.findOne(id))
                .map(this::toDto)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public QuizDTO createQuiz(@RequestBody final QuizDTO quizDTO) {
        final Quiz quiz = toEntity(quizDTO);
        final Quiz result = quizService.create(quiz);
        return toDto(result);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteQuiz(@PathVariable("id") final Long id) {
        quizService.delete(id);
    }

    @PutMapping(value = "/question/{id}")
    public QuizQuestionDTO updateQuestion(@PathVariable("id") final Long id,
                                          @RequestBody final QuizQuestionDTO quizQuestionDTO) {
        final QuizQuestion question = toEntity(quizQuestionDTO);
        final QuizQuestion result = quizService.updateQuestion(id, question);
        return toDto(result);
    }

    @PutMapping(value = "/question")
    public List<QuizQuestionDTO> updateQuestions(@RequestBody final List<QuizQuestionDTO> quizQuestionDTOs) {
        final List<QuizQuestion> quizQuestions = mapList(quizQuestionDTOs, this::toEntity);
        final List<QuizQuestion> result = quizService.updateQuestions(quizQuestions);
        return mapList(result, this::toDto);
    }

    @GetMapping(value = "/question/{id}")
    public QuizQuestionDTO getQuestion(@PathVariable("id") final Long id) {
        return toDto(quizService.getQuestion(id));
    }

    @GetMapping(value = "/question")
    public List<QuizQuestionDTO> getQuestions(@RequestParam(value = "filterSectionId", required = false) final Long filterBySectionId) {
        return mapList(quizService.getQuestions(filterBySectionId), this::toDto);
    }

    @PostMapping(value = "/question/{id}/answer")
    public QuizQuestionDTO answer(@PathVariable("id") final Long id,
                                  @RequestBody final List<QuizAnswerDTO> quizAnswerDTOs) {
        final List<QuizAnswer> answers = mapList(quizAnswerDTOs, this::toEntity);
        final QuizQuestion result = quizService.answerQuestion(id, answers);
        return toDto(result);
    }

    @GetMapping(value = "/{id}/form")
    public ResponseEntity<List<QuizFormDTO>> getForms(@PathVariable("id") final Long id) {
        return Optional.ofNullable(quizService.findOne(id))
                .map(this::toDto)
                .map(dto -> new ResponseEntity<>(dto.getForms(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{quizId}/form", consumes = MediaType.APPLICATION_JSON_VALUE)
    public QuizFormDTO createForm(@PathVariable("quizId") final Long quizId,
                                  @RequestBody final QuizFormDTO formDTO) {
        final QuizForm form = toEntity(formDTO);
        final QuizForm result = quizService.createForm(quizId, form);
        return toDto(result);
    }

    @PutMapping(value = "/form/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public QuizFormDTO updateForm(@PathVariable("id") final Long id,
                                  @RequestBody final QuizFormDTO formDTO) {
        final QuizForm form = toEntity(formDTO);
        final QuizForm result = quizService.updateForm(id, form);
        return toDto(result);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/form/{id}")
    public void deleteForm(@PathVariable("id") final Long id) {
        quizService.deleteForm(id);
    }

    @PostMapping(value = "/form/{formId}/question/{questionId}/answer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public QuizQuestionDTO answer(@PathVariable("formId") final Long formId,
                                  @PathVariable("questionId") final Long questionId,
                                  @RequestBody final List<QuizAnswerDTO> quizAnswerDTOs) {
        final List<QuizAnswer> answers = mapList(quizAnswerDTOs, this::toEntity);
        final QuizQuestion result = quizService.answerQuestion(formId, questionId, answers);
        return toDto(result);
    }

    private <A, B> List<B> mapList(Collection<A> collection, Function<A, B> mapFn) {
        return Optional.ofNullable(collection).map(c -> c.stream()
                .map(mapFn)
                .collect(Collectors.toList())
        ).orElse(Collections.emptyList());
    }

    private Quiz toEntity(QuizDTO dto) {
        Quiz quiz = new Quiz();
        quiz.setId(dto.getId());
        quiz.setForms(dto.getForms().stream()
                .map(this::toEntity)
                .collect(Collectors.toList()));
        quiz.setName(dto.getName());
        return quiz;
    }

    private QuizForm toEntity(QuizFormDTO dto) {
        QuizForm form = new QuizForm();
        form.setId(dto.getId());
        form.setLanguage(dto.getLanguage());
        form.setSection(toEntity(dto.getSection()));
        return form;
    }

    private QuizSection toEntity(QuizSectionDTO dto) {
        QuizSection section = new QuizSection();
        section.setId(dto.getId());
        section.setQuizQuestions(mapList(dto.getQuizQuestions(), this::toEntity));
        section.setChildSections(mapList(dto.getChildSections(), this::toEntity));
        return section;
    }

    private QuizQuestion toEntity(QuizQuestionDTO dto) {
        QuizQuestion question = new QuizQuestion();
        question.setId(dto.getId());
        question.setQuestion(dto.getQuestion());
        question.setUpdateCount(dto.getUpdateCount());
        question.setAnswers(mapList(dto.getAnswers(), this::toEntity));
        return question;
    }

    private QuizAnswer toEntity(QuizAnswerDTO dto) {
        QuizAnswer answer = new QuizAnswer();
        answer.setId(dto.getId());
        answer.setAnswer(dto.getAnswer());
        return answer;
    }

    private QuizDTO toDto(Quiz entity) {
        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setId(entity.getId());
        quizDTO.setName(entity.getName());
        quizDTO.setForms(mapList(entity.getForms(), this::toDto));
        return quizDTO;
    }

    private QuizFormDTO toDto(QuizForm entity) {
        QuizFormDTO formDTO = new QuizFormDTO();
        formDTO.setId(entity.getId());
        formDTO.setLanguage(entity.getLanguage());
        formDTO.setSection(toDto(entity.getSection()));
        return formDTO;
    }

    private QuizSectionDTO toDto(QuizSection entity) {
        QuizSectionDTO sectionDTO = new QuizSectionDTO();
        sectionDTO.setId(entity.getId());
        sectionDTO.setChildSections(mapList(entity.getChildSections(), this::toDto));
        sectionDTO.setQuizQuestions(mapList(entity.getQuizQuestions(), this::toDto));
        return sectionDTO;
    }

    private QuizQuestionDTO toDto(QuizQuestion entity) {
        QuizQuestionDTO questionDTO = new QuizQuestionDTO();
        questionDTO.setId(entity.getId());
        questionDTO.setUpdateCount(entity.getUpdateCount());
        questionDTO.setQuestion(entity.getQuestion());
        questionDTO.setAnswers(mapList(entity.getAnswers(), this::toDto));
        return questionDTO;
    }

    private QuizAnswerDTO toDto(QuizAnswer entity) {
        QuizAnswerDTO answerDTO = new QuizAnswerDTO();
        answerDTO.setId(entity.getId());
        answerDTO.setAnswer(entity.getAnswer());
        return answerDTO;
    }
}
