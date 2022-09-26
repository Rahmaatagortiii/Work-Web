package com.wellbeignatwork.backend.controller.Forum;

import com.wellbeignatwork.backend.entity.Forum.Post;
import com.wellbeignatwork.backend.entity.Forum.Reaction;
import com.wellbeignatwork.backend.entity.Forum.ReactionType;
import com.wellbeignatwork.backend.service.Forum.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/React")
public class ReactionController {
    private ReactionService reactionService;
    @Autowired
    public ReactionController(ReactionService reactionService){
        this.reactionService=reactionService;
    }
    @PostMapping("/addReaction/{idPost}/{idUser}")
    public void addReactToPost(@RequestBody Reaction reaction, @PathVariable int idPost,@PathVariable Long idUser){
        reactionService.addReactToPost(reaction,idPost,idUser);
    }
    @DeleteMapping("/deleteReaction/{idReaction}")
    public void deleteReaction(@PathVariable int idReaction){
        reactionService.deleteReaction(idReaction);
    }
    @GetMapping("getNbrReactionByPost/{idPost}")
    public int getNbrReactionByPost(@PathVariable int idPost){

        return reactionService.getNbrReactionByPost(idPost);
    }

}
