package com.wellbeignatwork.backend.service.Forum;

import com.wellbeignatwork.backend.entity.Forum.Opinion;

import java.util.List;

public interface OpinionService {
    public Opinion AddOpinion(Opinion opinion,Long idUser);
    public List<Opinion> getAllOpinions();
    public void DeleteOpinion(Long id);
    public void UpdateOpinion(Opinion opinion);
    public void assignOpinionToUser(Long idOpenion, Long idUser);
    public List<Opinion> getWhatsTheirOpinionOnMe(Long idUser);
}
