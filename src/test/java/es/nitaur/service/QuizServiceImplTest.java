package es.nitaur.service;

import com.google.common.collect.Lists;
import es.nitaur.persistence.model.quiz.*;
import es.nitaur.persistence.repository.QuizFormRepository;
import es.nitaur.persistence.repository.QuizQuestionRepository;
import es.nitaur.persistence.repository.QuizRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.Collections;

public class QuizServiceImplTest {

    private QuizRepository quizRepository = Mockito.mock(QuizRepository.class);
    private QuizQuestionRepository quizQuestionRepository = Mockito.mock(QuizQuestionRepository.class);
    private QuizFormRepository quizFormRepository = Mockito.mock(QuizFormRepository.class);
    private QuizService quizService = new QuizServiceImpl(quizRepository, quizQuestionRepository, quizFormRepository);

    @Test
    public void updateForm() {
        Long formId = 1L;
        QuizForm existingForm = existingForm(formId);
        Mockito.when(quizFormRepository.findOne(formId)).thenReturn(existingForm);
        Mockito.when(quizFormRepository.save(Matchers.any(QuizForm.class))).thenReturn(existingForm);

        QuizForm updatedForm = updatedForm(formId);
        QuizForm updateResult = quizService.updateForm(formId, updatedForm);
        Assert.assertEquals(updatedForm.getSection(), updateResult.getSection());
        Assert.assertEquals(updatedForm.getLanguage(), updateResult.getLanguage());
        Assert.assertEquals("should populate section with related form", existingForm, updatedForm.getSection().getQuizForm());
        assertParentRelationEntityIsPopulated(updateResult);

        Mockito.verify(quizFormRepository, Mockito.atLeastOnce()).findOne(formId);
        Mockito.verify(quizFormRepository, Mockito.atLeastOnce()).save(existingForm);
    }

    @Test
    public void createForm() {
        Long quizId = 2L;
        QuizForm newForm = newForm();
        Mockito.when(quizFormRepository.save(Matchers.any(QuizForm.class))).thenReturn(newForm);

        QuizForm createResult = quizService.createForm(quizId, newForm);
        Assert.assertEquals("should populate form with Quiz entity", quizId, newForm.getQuiz().getId());
        Assert.assertEquals(newForm.getSection(), createResult.getSection());
        Assert.assertEquals(newForm.getLanguage(), createResult.getLanguage());
        Assert.assertEquals("should populate section with related form", newForm, newForm.getSection().getQuizForm());
        assertParentRelationEntityIsPopulated(newForm);
        
        Mockito.verify(quizFormRepository, Mockito.atLeastOnce()).save(newForm);
    }

    @Test
    public void deleteForm() {
        Long formId = 3L;
        QuizForm deleteForm = deleteForm(formId);
        Mockito.when(quizFormRepository.findOne(formId)).thenReturn(deleteForm);
        Quiz quiz = deleteForm.getQuiz();

        Assert.assertFalse(quiz.getForms().isEmpty());
        quizService.deleteForm(formId);
        Assert.assertTrue(quiz.getForms().isEmpty());
        
        Mockito.verify(quizFormRepository, Mockito.atLeastOnce()).findOne(formId);
        Mockito.verify(quizRepository, Mockito.atLeastOnce()).save(quiz);
    }

    private void assertParentRelationEntityIsPopulated(QuizForm quizForm) {
        Assert.assertFalse(quizForm.getSection().getQuizQuestions().isEmpty());
        quizForm.getSection().getQuizQuestions().forEach(question ->
                Assert.assertEquals("should populate question with related section", quizForm.getSection(), question.getSection()));
        quizForm.getSection().getChildSections().forEach(section ->
                Assert.assertNull("child sections are not related to form", section.getQuizForm()));
        quizForm.getSection().getQuizQuestions()
                .forEach(quizQuestion -> {
                    quizQuestion.getAnswers().forEach(answer ->
                            Assert.assertEquals("should populate answer with related question", quizQuestion, answer.getQuestion()));
                });
    }

    private QuizForm existingForm(Long id) {
        QuizForm form = new QuizForm();
        form.setId(id);
        form.setSection(new QuizSection());
        form.getSection().setChildSections(Lists.newArrayList(new QuizSection()));
        return form;
    }

    private QuizForm updatedForm(Long id) {
        QuizForm form = new QuizForm();
        form.setId(id);
        form.setLanguage("EN");
        form.setSection(createTestSection(9L));
        form.getSection().setChildSections(Lists.newArrayList(createTestSection(15L)));
        return form;
    }

    private QuizForm newForm() {
        QuizForm form = new QuizForm();
        form.setLanguage("EN");
        form.setSection(createTestSection(9L));
        form.getSection().setChildSections(Lists.newArrayList(createTestSection(15L)));
        return form;
    }

    private QuizForm deleteForm(Long id) {
        QuizForm form = new QuizForm();
        Quiz quiz = new Quiz();
        quiz.setForms(Lists.newArrayList(form));
        form.setQuiz(quiz);
        form.setId(id);
        form.setLanguage("EN");
        form.setSection(createTestSection(9L));
        form.getSection().setChildSections(Lists.newArrayList(createTestSection(15L)));
        return form;
    }

    private QuizSection createTestSection(Long id) {
        QuizSection section = new QuizSection();
        section.setId(id);
        section.setQuizQuestions(Lists.newArrayList(
                createTestQuestion(1L, "What is the meaning of life?"),
                createTestQuestion(2L, "Por que?"))
        );
        section.setChildSections(Collections.emptyList());
        return section;
    }

    private QuizQuestion createTestQuestion(Long id, String question) {
        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setId(id);
        quizQuestion.setQuestion(question);
        quizQuestion.setUpdateCount(0L);
        quizQuestion.setAnswers(Lists.newArrayList(
                createTestAnswer(1L, "Answer 1"),
                createTestAnswer(2L, "Answer 2")
        ));
        return quizQuestion;
    }

    private QuizAnswer createTestAnswer(Long id, String answer) {
        QuizAnswer quizAnswer = new QuizAnswer();
        quizAnswer.setId(id);
        quizAnswer.setAnswer(answer);
        return quizAnswer;
    }
}