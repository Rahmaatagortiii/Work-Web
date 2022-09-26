package com.wellbeignatwork.backend.service.Evaluation;

import com.wellbeignatwork.backend.entity.Evaluation.Sujet;
import com.wellbeignatwork.backend.entity.Evaluation.VoteIdea;
import com.wellbeignatwork.backend.entity.User.User;
import com.wellbeignatwork.backend.repository.Evaluation.IntVoteIdeaRepo;
import com.wellbeignatwork.backend.repository.Evaluation.SujetRepo;
import com.wellbeignatwork.backend.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VoteService implements IntVoteService {
    @Autowired
    private SujetRepo MySUjetRepo;
    @Autowired
    private UserRepository MyUserRepo;

    @Autowired
    private IntVoteIdeaRepo MyVoteIdeaRepo;

    @Override
    public void addSujet(Sujet sujet) {
        MySUjetRepo.save(sujet);

    }

    @Override
    public int AddYes(VoteIdea v, int sujetId, Long userId) {
        v.setNbYes(1);
        v.setNbNo(0);
        Sujet sujet = MySUjetRepo.findById(sujetId).orElse(null);
        User user = MyUserRepo.findById(userId).orElse(null);
        v.setIdUser(user);
        v.setIdSujet(sujet);
        MyVoteIdeaRepo.save(v);

        return 0;
    }

    @Override
    public int AddNo(VoteIdea v, int sujetId, Long userId) {

        v.setNbYes(0);
        v.setNbNo(1);
        Sujet sujet = MySUjetRepo.findById(sujetId).orElse(null);
        User user = MyUserRepo.findById(userId).orElse(null);
        v.setIdUser(user);
        v.setIdSujet(sujet);
        MyVoteIdeaRepo.save(v);


        return 0;
    }

    @Override
    public VoteIdea getVote(int sujetId, Long userId) {

        return MyVoteIdeaRepo.getVoteBySujetAndUser(sujetId,userId);
    }



    @Override
    public String VerifYourChoice(Long userId, int sujetId) {
        String AucunVote="there is No Vote For this user ";
        String AlreadyVotedForYes="this User Voted With Yes";
        String AlreadyVotedForNo="this User Voted With No";

        VoteIdea v = MyVoteIdeaRepo.getVoteBySujetAndUserYes(sujetId, userId);
        VoteIdea v1 = MyVoteIdeaRepo.getVoteBySujetAndUserNO(sujetId, userId);
        if (v == null && v1 == null)
            return AucunVote;
        else if (v != null && v1 == null)
            return AlreadyVotedForYes;
        else
            return AlreadyVotedForNo;
    }

    @Override
    public int countYes(int sujetId) {
    int nbYes = MyVoteIdeaRepo.countYes( sujetId);
        Sujet sujet = MySUjetRepo.findById(sujetId).get();
        sujet.setNbYes(nbYes);
        MySUjetRepo.save(sujet);
        return (nbYes);




    }

    @Override
    public int countNo(int sujetId) {
          int nbNo = MyVoteIdeaRepo.countNo(sujetId);
        Sujet sujet = MySUjetRepo.findById(sujetId).get();
        sujet.setNbYes(nbNo);
        MySUjetRepo.save(sujet);
        return (nbNo);

    }

    @Override
    public List<String> findNomdesUsersVoter(int sujetId) {
        Sujet sujet = MySUjetRepo.findById(sujetId).orElse(null);
        List<String> noms = new ArrayList<>();
        for (VoteIdea v : sujet.getVotesSujet())
            noms.add(v.getIdUser().getFirstName() + " " + v.getIdUser().getLastName());
        return noms;
    }


    @Override
    public void deletevoteById(int sujetId, Long userId) {
        VoteIdea v = MyVoteIdeaRepo.getVoteBySujetAndUser(sujetId, userId);
        MyVoteIdeaRepo.delete(v);

    }

    @Override
    public void UpdateYes(int sujetId, Long userId) {
        VoteIdea v = MyVoteIdeaRepo.getVoteBySujetAndUser(sujetId, userId);
        v.setNbYes(1);
        v.setNbNo(0);
        MyVoteIdeaRepo.save(v);

    }

    @Override
    public void UpdateNo(int sujetId, Long userId) {
        VoteIdea v = MyVoteIdeaRepo.getVoteBySujetAndUser(sujetId, userId);
        v.setNbYes(0);
        v.setNbNo(1);
        MyVoteIdeaRepo.save(v);
    }

    @Override
    public Boolean verificationvote(int sujetId, Long userId) {
        VoteIdea v = MyVoteIdeaRepo.getVoteBySujetAndUser(sujetId, userId);
        if (v == null)
            return false;
        return true;
    }



}
