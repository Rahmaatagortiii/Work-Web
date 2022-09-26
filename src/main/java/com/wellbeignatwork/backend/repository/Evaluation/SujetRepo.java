package com.wellbeignatwork.backend.repository.Evaluation;

import com.wellbeignatwork.backend.entity.Evaluation.Sujet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SujetRepo extends CrudRepository<Sujet,Integer> {


}
