package es.nitaur;

import com.google.common.collect.Lists;
import es.nitaur.repository.QuizQuestionRepository;
import es.nitaur.repository.QuizRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizServiceImpl.class);

    private QuizRepository quizRepository;
    private QuizQuestionRepository quizQuestionRepository;

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository, QuizQuestionRepository quizQuestionRepository) {
        this.quizRepository = quizRepository;
        this.quizQuestionRepository = quizQuestionRepository;
    }

    @Override
    public Collection<Quiz> findAll() {
        final Collection<Quiz> quizzes = quizRepository.findAll();
        return quizzes;
    }

    @Override
    public Quiz findOne(final Long id) {
        final Quiz quiz = quizRepository.findOne(id);
        return quiz;
    }

    public Quiz create(final Quiz quiz) {
        if (quiz.getId() != null) {
            logger.error("Attempted to create a Quiz, but id attribute was not null.");
            throw new EntityExistsException(
                    "Cannot create new Quiz with supplied id. The id attribute must be null to create an entity.");
        }

        final Quiz savedQuiz = quizRepository.save(quiz);
        return savedQuiz;
    }

    @Override
    public Quiz update(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public void delete(final Long id) {
        quizRepository.delete(id);
    }

    @Override
    public QuizQuestion updateQuestion(Long id, QuizQuestion question) {
        QuizQuestion questionToUpdate = quizQuestionRepository.findOne(id);
        if (questionToUpdate == null) {
            logger.error("Attempted to update Question, but Question is not found");
            throw new NoResultException(
                    "Cannot update Question with supplied id. The object is not found.");
        }

        questionToUpdate.setQuestion(question.getQuestion());
        questionToUpdate.setAnswers(question.getAnswers());
        return quizQuestionRepository.save(questionToUpdate);
    }

    @Override
    @Transactional
    public QuizQuestion answerQuestion(Long id, List<QuizAnswer> quizAnswers) {
        QuizQuestion questionToUpdate = quizQuestionRepository.findOne(id);
        if (questionToUpdate == null) {
            logger.error("Attempted to answer Question, but Question is not found");
            throw new NoResultException(
                    "Cannot answer Question with supplied id. The object is not found.");
        }
        questionToUpdate.setAnswers(quizAnswers);
        questionToUpdate.setUpdateCount(questionToUpdate.getUpdateCount() + 1);
        QuizQuestion savedQuestion = quizQuestionRepository.save(questionToUpdate);
        return savedQuestion;
    }

    @Override
    public QuizQuestion getQuestion(Long id) {
        QuizQuestion questions = quizQuestionRepository.findOne(id);
        if (questions == null) {
            logger.error("Attempted to answer Question, but Question is not found");
            throw new NoResultException(
                    "Cannot answer Question with supplied id. The object is not found.");
        }
        return questions;
    }

    @Override
    public Collection<QuizQuestion> getAllQuestions() {
        final Collection<QuizQuestion> qs = quizQuestionRepository.findAll();
        return qs;
    }

    @Override
    public List<QuizQuestion> updateQuestions(List<QuizQuestion> quizQuestions) {
        return quizQuestions.stream().noneMatch(q -> StringUtils.isEmpty(q.getQuestion())) ?
                quizQuestionRepository.save(quizQuestions.stream()
                        .map(q -> {
                            QuizQuestion questionToUpdate = quizQuestionRepository.findOne(q.getId());
                            questionToUpdate.setQuestion(q.getQuestion());
                            return questionToUpdate;
                        })
                        .collect(Collectors.toList())
                ) : Collections.emptyList();
    }

    @Override
    public List<QuizQuestion> getQuestions(Long filterBySectionId) {
        List<QuizQuestion> all = quizQuestionRepository.findAll();
        if (filterBySectionId != null) {
            Iterator<QuizQuestion> iterator = all.iterator();
            while (iterator.hasNext()) {
                QuizQuestion next = iterator.next();
                Long sectionId = next.getSection().getId();
                if (!filterBySectionId.equals(sectionId)) {
                    iterator.remove();
                }
            }
        }
        return all;
    }

}
