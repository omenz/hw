package es.nitaur.api.quiz.dto;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class QuizDTO {
    private Long id;
    private String name;
    private List<QuizFormDTO> forms = Collections.emptyList();
}
