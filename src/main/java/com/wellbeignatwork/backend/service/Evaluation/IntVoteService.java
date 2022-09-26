package com.wellbeignatwork.backend.service.Evaluation;



import com.wellbeignatwork.backend.entity.Evaluation.Sujet;
import com.wellbeignatwork.backend.entity.Evaluation.VoteIdea;

import java.util.List;

public interface IntVoteService {
     int AddYes(VoteIdea v, int sujetId, Long userId);
     int AddNo(VoteIdea v, int sujetId, Long userId);
     VoteIdea getVote(int sujetId, Long userId);
     void addSujet(Sujet sujet);
     String VerifYourChoice(Long userId, int sujetId);
     int countYes(int sujetId);
     int countNo(int sujetId);
     List<String> findNomdesUsersVoter(int sujetId);
     void deletevoteById(int sujetId, Long userId);
     void UpdateYes(int sujetId, Long userId);
     void UpdateNo(int sujetId, Long userId);
     Boolean verificationvote(int sujetId, Long userId);

}
