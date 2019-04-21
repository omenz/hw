package es.nitaur.persistence.model.quiz;

import es.nitaur.persistence.model.GenericEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "QUIZ_FORM")
public class QuizForm extends GenericEntity {

    @NotNull
    private String language;

    @ManyToOne
    @JoinColumn(name = "quiz_fk")
    private Quiz quiz;

    @NotNull
    @OneToOne(mappedBy = "quizForm", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, optional = false, orphanRemoval = true)
    private QuizSection section;
}
