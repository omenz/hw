package es.nitaur;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class QuizController {

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    @Autowired
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @RequestMapping(value = "/api/quiz",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Quiz>> getQuizzes() {
        final Collection<Quiz> quizzes = quizService.findAll();
        return new ResponseEntity<Collection<Quiz>>(quizzes, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/quiz/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Quiz> getQuiz(@PathVariable final Long id) {
        final Quiz quiz = quizService.findOne(id);
        if (quiz == null) {
            return new ResponseEntity<Quiz>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Quiz>(quiz, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/quiz",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Quiz> createQuiz(@RequestBody final Quiz quiz) {

        List<QuizForm> forms = quiz.getForms();
        for (QuizForm form : forms) {
            form.setQuiz(quiz);
            List<QuizQuestion> quizQuestions = form.getSection().getQuizQuestions();
            for (QuizQuestion quizQuestion : quizQuestions) {
                quizQuestion.setSection(form.getSection());
            }
        }

        final Quiz savedQuiz = quizService.create(quiz);
        return new ResponseEntity<>(savedQuiz, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/quiz/delete/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<Quiz> deleteQuiz(@PathVariable("id") final Long id) {
        quizService.delete(id);
        return new ResponseEntity<Quiz>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/api/quiz/updateQuestion/{id}",
            method = RequestMethod.POST)
    public ResponseEntity<QuizQuestion> updateQuestion(@PathVariable("id") final Long id, @RequestBody final QuizQuestion quizQuestion) {
        QuizQuestion updateQuestion = quizService.updateQuestion(id, quizQuestion);
        return new ResponseEntity<QuizQuestion>(updateQuestion, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/quiz/updateQuestions",
            method = RequestMethod.POST)
    public ResponseEntity<List<QuizQuestion>> updateQuestions(@RequestBody final List<QuizQuestion> quizQuestions) {
        List<QuizQuestion> updateQuestion = quizService.updateQuestions(quizQuestions);
        return new ResponseEntity<List<QuizQuestion>>(updateQuestion, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/quiz/getQuestion/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<QuizQuestion> getQuestion(@PathVariable("id") final Long id) {
        QuizQuestion question = quizService.getQuestion(id);
        return new ResponseEntity<QuizQuestion>(question, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/quiz/allQuestions",
            method = RequestMethod.GET)
    public ResponseEntity<List<QuizQuestion>> getQuestions(@RequestParam(value = "filterSectionId", required = false) Long filterBySectionId) {
        List<QuizQuestion> questions = quizService.getQuestions(filterBySectionId);
        return new ResponseEntity<List<QuizQuestion>>(questions, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/quiz/answerQuestion/{id}",
            method = RequestMethod.POST)
    public ResponseEntity<QuizQuestion> answer(@PathVariable("id") final Long id, @RequestBody final List<QuizAnswer> quizAnswers) {
        QuizQuestion updatedQuestion = quizService.answerQuestion(id, quizAnswers);
        return new ResponseEntity<QuizQuestion>(updatedQuestion, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/quiz/{id}/form", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuizForm>> getForms(@PathVariable("id") final Long id) {
        return Optional.ofNullable(quizService.findOne(id))
                .map(quiz -> new ResponseEntity<>(quiz.getForms(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @RequestMapping(value = "/api/quiz/{quizId}/form",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuizForm> createForm(@PathVariable("quizId") final Long quizId, @RequestBody final QuizForm form) {
        List<QuizQuestion> quizQuestions = form.getSection().getQuizQuestions();
        for (QuizQuestion quizQuestion : quizQuestions) {
            quizQuestion.setSection(form.getSection());
        }
        QuizForm createdForm = quizService.createForm(quizId, form);
        return new ResponseEntity<QuizForm>(createdForm, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/quiz/form/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuizForm> updateForm(@PathVariable("id") final Long id, @RequestBody final QuizForm form) {
        QuizForm updatedForm = quizService.updateForm(id, form);
        return new ResponseEntity<QuizForm>(updatedForm, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/quiz/form/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<QuizForm> deleteForm(@PathVariable("id") final Long id) {
        quizService.deleteForm(id);
        return new ResponseEntity<QuizForm>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/api/quiz/form/{formId}/answerQuestion/{questionId}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuizQuestion> answer(@PathVariable("formId") final Long formId,
                                               @PathVariable("questionId") final Long questionId,
                                               @RequestBody final List<QuizAnswer> quizAnswers) {
        QuizQuestion updatedQuestion = quizService.answerQuestion(formId, questionId, quizAnswers);
        return new ResponseEntity<QuizQuestion>(updatedQuestion, HttpStatus.OK);
    }
}
