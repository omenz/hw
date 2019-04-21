package es.nitaur.persistence.model.quiz;

import es.nitaur.persistence.model.GenericEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
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
}
