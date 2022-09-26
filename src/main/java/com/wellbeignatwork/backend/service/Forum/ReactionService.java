package com.wellbeignatwork.backend.service.Forum;

import com.wellbeignatwork.backend.entity.Forum.Post;
import com.wellbeignatwork.backend.entity.Forum.Reaction;
import com.wellbeignatwork.backend.entity.Forum.ReactionType;

public interface ReactionService {
    public void addReactToPost(Reaction reaction,int idPost,Long idUser);
    public void deleteReaction(int idReaction);
    public int getNbrReactionByPost(int idPost);
}
