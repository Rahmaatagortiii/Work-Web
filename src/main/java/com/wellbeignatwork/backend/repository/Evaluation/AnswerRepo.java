package com.wellbeignatwork.backend.repository.Evaluation;


import com.wellbeignatwork.backend.entity.Evaluation.Answer;
import com.wellbeignatwork.backend.entity.Evaluation.Sentiment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AnswerRepo extends CrudRepository<Answer,Integer> {

    @Query("select count (a) FROM Answer a where a.Sentiment = :status ")
    int nbreByStatus(@Param("status") Sentiment status);
    @Query(value="select count (*) from answer where sentiment = ?1 order by id_answer DESC ",nativeQuery = true)
    int nbreByStatus2(String status);
    @Query("SELECT a FROM Answer a ORDER BY a.idAnswer DESC")
    List<Answer> getLastAnswer(Pageable pageable);






}
