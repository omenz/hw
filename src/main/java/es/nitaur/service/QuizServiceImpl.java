package es.nitaur.service;

import es.nitaur.persistence.model.quiz.*;
import es.nitaur.persistence.repository.QuizFormRepository;
import es.nitaur.persistence.repository.QuizQuestionRepository;
import es.nitaur.persistence.repository.QuizRepository;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizServiceImpl.class);

    private QuizRepository quizRepository;
    private QuizQuestionRepository quizQuestionRepository;
    private QuizFormRepository quizFormRepository;

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository,
                           QuizQuestionRepository quizQuestionRepository,
                           QuizFormRepository quizFormRepository) {
        this.quizRepository = quizRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizFormRepository = quizFormRepository;
    }

    @Override
    public Collection<Quiz> findAll() {
        return quizRepository.findAll();
    }

    @Override
    public Quiz findOne(final Long id) {
        return quizRepository.findOne(id);
    }

    public Quiz create(final Quiz quiz) {
        if (quiz.getId() != null) {
            logger.error("Attempted to create a Quiz, but id attribute was not null.");
            throw new EntityExistsException(
                    "Cannot create new Quiz with supplied id. The id attribute must be null to create an entity.");
        }
        quiz.getForms().forEach(form -> {
            form.setQuiz(quiz);
            form.getSection().setQuizForm(form);
            populateSectionForPersistence(form.getSection());
        });
        return quizRepository.save(quiz);
    }

    @Override
    public Quiz update(final Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public void delete(final Long id) {
        quizRepository.delete(id);
    }

    @Override
    public QuizQuestion updateQuestion(final Long id, final QuizQuestion question) {
        final QuizQuestion questionToUpdate = getQuestion(id);
        questionToUpdate.setQuestion(question.getQuestion());
        questionToUpdate.setAnswers(question.getAnswers());
        return quizQuestionRepository.save(questionToUpdate);
    }

    @Override
    @Transactional
    public QuizQuestion answerQuestion(final Long id, final List<QuizAnswer> quizAnswers) {
        final QuizQuestion questionToUpdate = getQuestion(id);
        questionToUpdate.setAnswers(quizAnswers);
        questionToUpdate.setUpdateCount(questionToUpdate.getUpdateCount() + 1);
        return quizQuestionRepository.save(questionToUpdate);
    }

    @Override
    @Transactional
    public QuizQuestion answerQuestion(final Long formId, final Long id, final List<QuizAnswer> quizAnswers) {
        final QuizQuestion questionToUpdate = quizQuestionRepository.findOne(id);
        if (questionToUpdate == null || !questionToUpdate.getSection().getQuizForm().getId().equals(formId)) {
            logger.error("Attempted to answer Question, but Question is not found");
            throw new NoResultException(
                    "Cannot answer Question with supplied id. The object is not found.");
        }
        questionToUpdate.setAnswers(quizAnswers);
        questionToUpdate.setUpdateCount(questionToUpdate.getUpdateCount() + 1);
        return quizQuestionRepository.save(questionToUpdate);
    }

    @Override
    public QuizQuestion getQuestion(final Long id) {
        final QuizQuestion questions = quizQuestionRepository.findOne(id);
        if (questions == null) {
            logger.error("Attempted to answer Question, but Question is not found");
            throw new NoResultException(
                    "Cannot answer Question with supplied id. The object is not found.");
        }
        return questions;
    }

    @Override
    public Collection<QuizQuestion> getAllQuestions() {
        return quizQuestionRepository.findAll();
    }

    @Override
    public List<QuizQuestion> updateQuestions(final List<QuizQuestion> quizQuestions) {
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
    public List<QuizQuestion> getQuestions(final Long filterBySectionId) {
        return quizQuestionRepository.findBySection_Id(filterBySectionId);
    }

    @Override
    @Transactional
    public QuizForm createForm(final Long quizId, final QuizForm form) {
        if (form.getId() != null) {
            String msg = "Cannot create new Quiz with supplied id. The id attribute must be null to create an entity.";
            logger.error(msg);
            throw new EntityExistsException(msg);
        }
        final Quiz quiz = new Quiz();
        quiz.setId(quizId);
        form.setQuiz(quiz);
        form.getSection().setQuizForm(form);
        populateSectionForPersistence(form.getSection());
        return quizFormRepository.save(form);
    }

    @Override
    @Transactional
    public void deleteForm(final Long id) {
        final QuizForm formToRemove = selectFormForUpdate(id);
        final Quiz quiz = formToRemove.getQuiz();
        quiz.getForms().remove(formToRemove);
        quizRepository.save(quiz);
    }

    @Override
    @Transactional
    public QuizForm updateForm(final Long id, final QuizForm form) {
        final QuizForm formToUpdate = selectFormForUpdate(id);
        formToUpdate.setLanguage(form.getLanguage());
        formToUpdate.setSection(form.getSection());
        formToUpdate.getSection().setQuizForm(formToUpdate);
        populateSectionForPersistence(formToUpdate.getSection());
        return quizFormRepository.save(formToUpdate);
    }

    private QuizForm selectFormForUpdate(final Long id) {
        final QuizForm formToUpdate = quizFormRepository.findOne(id);
        if (formToUpdate == null) {
            String msg = "QuizForm with supplied id not found.";
            logger.error(msg);
            throw new NoResultException(msg);
        }
        return formToUpdate;
    }
    
    private void populateSectionForPersistence(final QuizSection parentSection) {
        parentSection.getChildSections().forEach(child -> {
            child.setParentSection(parentSection);
            populateSectionForPersistence(child);
        });
        populateQuestionsForPersistence(parentSection);
    }

    private void populateQuestionsForPersistence(final QuizSection section) {
        section.getQuizQuestions().forEach(question -> question.setSection(section));
    }
}
