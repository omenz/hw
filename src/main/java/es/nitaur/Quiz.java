package es.nitaur;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "QUIZ")
public class Quiz extends GenericEntity {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<QuizForm> forms;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QuizForm> getForms() {
        return forms;
    }

    public void setForms(List<QuizForm> forms) {
        this.forms = forms;
    }
}