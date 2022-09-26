package com.wellbeignatwork.backend.service.Forum;

import com.wellbeignatwork.backend.entity.Forum.Opinion;
import com.wellbeignatwork.backend.entity.User.User;
import com.wellbeignatwork.backend.repository.Forum.OpinionRepository;
import com.wellbeignatwork.backend.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpinionServiceImpl implements OpinionService{
    private OpinionRepository opinionRepository;
    private UserRepository userRepository;
    @Autowired
    public OpinionServiceImpl(OpinionRepository opinionRepository, UserRepository userRepository){
        this.opinionRepository=opinionRepository;
        this.userRepository=userRepository;
    }


    @Override
    public Opinion AddOpinion(Opinion opinion,Long idUser) {
        User user=userRepository.findById(idUser).orElse(null);
        opinion.setUser(user);

        return opinionRepository.save(opinion);
    }

    @Override
    public List<Opinion> getAllOpinions() {
        List<Opinion> opinions=new ArrayList<>();
        opinionRepository.findAll().forEach(opinions::add);
        return opinions;
    }

    @Override
    public void DeleteOpinion(Long id) {
        opinionRepository.deleteById(id);
    }

    @Override
    public void UpdateOpinion(Opinion opinion) {
        opinionRepository.save(opinion);
    }

    @Override
    public void assignOpinionToUser(Long idOpenion, Long idUser) {
        User user=userRepository.findById(idUser).orElse(null);
        Opinion opinion=opinionRepository.findById(idOpenion).orElse(null);
        opinion.setUser(user);
        opinionRepository.save(opinion);

    }
    public List<Opinion> getWhatsTheirOpinionOnMe(Long idUser){
        User user= userRepository.findById(idUser).orElse(null);
        return opinionRepository.findAllByUser(user);
    }

}
