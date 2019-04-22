package es.nitaur.api.quiz.dto;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class QuizQuestionDTO {
    private Long id;
    private String question;
    private Long updateCount;
    private List<QuizAnswerDTO> answers = Collections.emptyList();
}
