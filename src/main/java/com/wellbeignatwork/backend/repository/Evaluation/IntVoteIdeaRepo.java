package com.wellbeignatwork.backend.repository.Evaluation;


import com.wellbeignatwork.backend.entity.Evaluation.VoteIdea;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IntVoteIdeaRepo extends CrudRepository<VoteIdea,Integer> {

    @Query(value="select * from vote_idea where id_sujet= ?1 AND id_user= ?2",nativeQuery=true)
     VoteIdea getVoteBySujetAndUser(int sujetId, Long userId);

    @Query(value="select * from vote_idea where id_sujet= ?1 AND id_user= ?2 and nb_yes=1",nativeQuery=true)
     VoteIdea getVoteBySujetAndUserYes(int sujetId, Long userId);

    @Query(value="select * from vote_idea where id_sujet=?1 AND id_user=?2 and nb_no=1",nativeQuery=true)
     VoteIdea getVoteBySujetAndUserNO(int sujetId, Long userId);


    @Query(value = "select count(*) from vote_idea where nb_yes=1 and id_sujet= ?1", nativeQuery = true)
     int countYes(int sujetId);
    @Query(value = "select count(*) from vote_idea where nb_no=1 and id_sujet= ?1", nativeQuery = true)
     int countNo(int sujetId);






}
