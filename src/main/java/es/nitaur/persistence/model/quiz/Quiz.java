package es.nitaur.persistence.model.quiz;

import es.nitaur.persistence.model.GenericEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "QUIZ")
public class Quiz extends GenericEntity {
    
    private static final long serialVersionUID = 1L;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<QuizForm> forms;
}