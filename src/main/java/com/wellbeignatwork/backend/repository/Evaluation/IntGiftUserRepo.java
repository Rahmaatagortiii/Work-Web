package com.wellbeignatwork.backend.repository.Evaluation;

import com.wellbeignatwork.backend.entity.Evaluation.UserGift;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntGiftUserRepo extends CrudRepository<UserGift,Integer> {
        @Query(value = "SELECT 	count(*) FROM `user_gift`where validite=0", nativeQuery = true)
         float nombreCodeValidee();

        @Query(value = "SELECT id FROM `user_gift`where validite=0 and montant BETWEEN 0 AND 299 ", nativeQuery = true)
         List<String> Gift300();
        @Query(value = "SELECT id FROM `user_gift`where validite=0 and montant BETWEEN 299 AND 499 ", nativeQuery = true)
        List<String> Gift500();
        @Query(value = "SELECT id FROM `user_gift`where validite=0 and montant BETWEEN 499 AND 999 ", nativeQuery = true)
        List<String> Gift1000();










}
