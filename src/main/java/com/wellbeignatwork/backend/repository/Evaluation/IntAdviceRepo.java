package com.wellbeignatwork.backend.repository.Evaluation;

import com.wellbeignatwork.backend.entity.Evaluation.Advice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntAdviceRepo extends CrudRepository<Advice,Integer> {
}
