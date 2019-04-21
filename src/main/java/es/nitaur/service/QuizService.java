package es.nitaur.service;

import es.nitaur.persistence.model.quiz.Quiz;
import es.nitaur.persistence.model.quiz.QuizAnswer;
import es.nitaur.persistence.model.quiz.QuizForm;
import es.nitaur.persistence.model.quiz.QuizQuestion;

import java.util.Collection;
import java.util.List;

public interface QuizService {

    Collection<Quiz> findAll();

    Quiz findOne(Long id);

    Quiz create(Quiz quiz);

    Quiz update(Quiz quiz);

    void delete(Long id);

    QuizQuestion updateQuestion(Long id, QuizQuestion quiz);

    QuizQuestion answerQuestion(Long id, List<QuizAnswer> quizAnswers);

    QuizQuestion answerQuestion(Long formId, Long id, List<QuizAnswer> quizAnswers);

    QuizQuestion getQuestion(Long id);

    Collection<QuizQuestion> getAllQuestions();

    List<QuizQuestion> updateQuestions(List<QuizQuestion> quizQuestions);

    List<QuizQuestion> getQuestions(Long sectionId);

    void deleteForm(Long id);

    QuizForm createForm(Long quizId, QuizForm form);

    QuizForm updateForm(Long id, QuizForm form);
}
