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
@Table(name = "QUIZ_QUESTION")
public class QuizQuestion extends GenericEntity {

    private static final long serialVersionUID = 1L;

    private String question;

    @Column(name = "update_count")
    private Long updateCount;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_fk")
    @Fetch(FetchMode.SUBSELECT)
    private List<QuizAnswer> answers;

    @ManyToOne
    @JoinColumn(name = "section_fk")
    private QuizSection section;
}
