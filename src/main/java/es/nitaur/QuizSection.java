package es.nitaur;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "QUIZ_SECTION")
public class QuizSection extends GenericEntity {

    private static final long serialVersionUID = 1L;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "section_fk")
    @Fetch(FetchMode.SUBSELECT)
    private List<QuizQuestion> quizQuestions;

    @OneToOne
    @JoinColumn(name = "quiz_form_fk")
    private QuizForm quizForm;

    @OneToMany(mappedBy="parentSection", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<QuizSection> childSections;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="parent_fk")
    private QuizSection parentSection;

    public List<QuizQuestion> getQuizQuestions() {
        return quizQuestions;
    }

    public void setQuizQuestions(List<QuizQuestion> quizQuestions) {
        this.quizQuestions = quizQuestions;
    }

    @JsonIgnore
    public QuizForm getQuizForm() {
        return quizForm;
    }

    public void setQuizForm(QuizForm quizForm) {
        this.quizForm = quizForm;
    }

    @JsonIgnore
    public QuizSection getParentSection() {
        return parentSection;
    }

    public void setParentSection(QuizSection parentSection) {
        this.parentSection = parentSection;
    }

    public List<QuizSection> getChildSections() {
        return childSections;
    }

    public void setChildSections(List<QuizSection> childSections) {
        this.childSections = childSections;
    }
}
