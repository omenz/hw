package es.nitaur.api.quiz.dto;

import lombok.Data;

@Data
public class QuizFormDTO {
    private Long id;
    private String language;
    private QuizSectionDTO section;
}
