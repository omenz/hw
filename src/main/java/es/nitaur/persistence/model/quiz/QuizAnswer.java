package es.nitaur.persistence.model.quiz;

import es.nitaur.persistence.model.GenericEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "QUIZ_ANSWER")
public class QuizAnswer extends GenericEntity {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String answer;

    @ManyToOne
    @JoinColumn(name = "question_fk")
    private QuizQuestion question;
}
