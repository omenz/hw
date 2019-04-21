package es.nitaur.api.quiz.dto;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class QuizSectionDTO {
    private Long id;
    private List<QuizQuestionDTO> quizQuestions = Collections.emptyList();
    private List<QuizSectionDTO> childSections = Collections.emptyList();
}
