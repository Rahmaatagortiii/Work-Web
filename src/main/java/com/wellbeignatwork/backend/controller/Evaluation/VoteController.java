package com.wellbeignatwork.backend.controller.Evaluation;

import com.wellbeignatwork.backend.entity.Evaluation.Sujet;
import com.wellbeignatwork.backend.entity.Evaluation.VoteIdea;
import com.wellbeignatwork.backend.service.Evaluation.IntVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/Vote")
public class VoteController {

    @Autowired
    IntVoteService intVoteService;




    @PostMapping("/ajouterPour/{sujetId}/{userId}")
    @ResponseBody
    public ResponseEntity<?> ajouterPour(@PathVariable(value = "sujetId") int sujetid,
                                         @PathVariable(value = "userId") Long userid,
                                        @RequestBody VoteIdea v )
    {

        intVoteService.AddYes( v,sujetid, userid);
        return ResponseEntity.created(null).body(v);

    }
    @PostMapping("/ajouterContre/{sujetId}/{userId}")
    @ResponseBody
    public ResponseEntity<?> ajouterContre(@PathVariable(value = "sujetId") int sujetid,
                                         @PathVariable(value = "userId") Long userid,
                                         @RequestBody VoteIdea v )
    {

        intVoteService.AddNo( v,sujetid, userid);
        return ResponseEntity.created(null).body(v);

    }




    @PostMapping("/addsujet")
    @ResponseBody
    public void addSujet(@RequestBody Sujet sujet)
    {
        intVoteService.addSujet(sujet);
    }


    @GetMapping("/GetNbrYes/{sujetId}")
    @ResponseBody
    public int countYes(@PathVariable("sujetId") int sujetId)
    {
        return intVoteService.countYes(sujetId);
    }



    @GetMapping("/GetNbrNo/{sujetId}")
    @ResponseBody
    public int countNo(@PathVariable("sujetId") int sujetId)
    {
        return intVoteService.countNo(sujetId);
    }



    @GetMapping("/GetVote/{sujetId}/{iduser}")
    @ResponseBody
    public ResponseEntity<?> getVote(@PathVariable(value = "sujetId") int sujetId,
                                          @PathVariable("iduser") Long iduser) {
        VoteIdea vote;
        vote=intVoteService.getVote(sujetId, iduser) ;
        return 	ResponseEntity.ok().body(vote);
    }



    @GetMapping("/findNameUsersVoter/{sujetId}")
    @ResponseBody
    public List<String> findNomdesUsersVoter(@PathVariable("sujetId") int sujetId)
    {return intVoteService.findNomdesUsersVoter(sujetId);}


    @PutMapping("/UpDateYes/{sujetId}/{userId}")
    @ResponseBody
    public void UpdateYes(@PathVariable("sujetId") int sujetId,@PathVariable("userId") Long userId)
    {
        intVoteService.UpdateYes(sujetId,userId);

    }
    @PutMapping("/UpdateNo/{sujetId}/{userId}")
    @ResponseBody
    public void UpdateNo(@PathVariable("sujetId") int sujetId,@PathVariable("userId") Long userId)
    {
        intVoteService.UpdateNo(sujetId,userId);
    }



    @PutMapping(value = "/delete/{idsujet}/{iduser}")
    @ResponseBody
    public ResponseEntity<?> deleteVote(@PathVariable("idsujet") int idsujet, @PathVariable("iduser") Long iduser) {
        intVoteService.deletevoteById(idsujet, iduser);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/Verification/{userId}/{sujetId}")
    @ResponseBody
    public String verificationvoteChoix(@PathVariable("userId") Long userId,@PathVariable("sujetId") int sujetId)
    { return intVoteService.VerifYourChoice(userId,sujetId); }

    @GetMapping("/verification/{sujetId}/{iduser}")
    @ResponseBody
    public Boolean verificationvote(@PathVariable("sujetId") int sujetId,@PathVariable("iduser") Long userId)
    { return intVoteService.verificationvote(sujetId,userId);}







/*
    @PutMapping(value = "/affecterpoint/{idsujet}")
    @ResponseBody
    public ResponseEntity<?> affecterpoint(@PathVariable("idsujet") int idsujet) {
        intVoteService.affecterdespoints(idsujet);
        return ResponseEntity.ok().build();
    }

 */

}


