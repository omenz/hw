package es.nitaur.repository;

import es.nitaur.QuizForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface QuizFormRepository extends JpaRepository<QuizForm, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    QuizForm findOne(Long id);
}
