package es.nitaur.persistence.repository;

import es.nitaur.persistence.model.quiz.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    QuizQuestion findOne(Long id);

    List<QuizQuestion> findBySection_Id(Long sectionId);
}
