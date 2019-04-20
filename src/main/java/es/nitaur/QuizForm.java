package es.nitaur;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    public QuizSection getSection() {
        return section;
    }

    public void setSection(QuizSection section) {
        this.section = section;
    }

    @JsonIgnore
    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
