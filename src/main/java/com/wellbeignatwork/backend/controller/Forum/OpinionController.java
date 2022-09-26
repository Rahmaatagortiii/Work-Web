package com.wellbeignatwork.backend.controller.Forum;

import com.wellbeignatwork.backend.entity.Forum.Opinion;
import com.wellbeignatwork.backend.service.Forum.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Opinion")
public class OpinionController {
    @Autowired
    private OpinionService opinionService;


    @PostMapping("/add-opinion/{idUser}")
    public Opinion AddOpinion(@RequestBody Opinion opinion,@PathVariable Long idUser) {
        return opinionService.AddOpinion(opinion,idUser);
    }
    @PutMapping("/update-opinion")
    public void UpdateOpinion(@RequestBody Opinion opinion) {
        opinionService.UpdateOpinion(opinion);
    }
    @GetMapping("/get-all-opinion")
    public List<Opinion> getAllOpinions() {
        return opinionService.getAllOpinions();
    }
    @DeleteMapping("/delete/{id}")
    public void DeleteOpinion(@PathVariable("id") Long id) {
        opinionService.DeleteOpinion(id);
    }
    @PostMapping("/assignOpinionToUser/{idOpinion}/{idUser}")
    public void AddOpinionToUser(@PathVariable Long idOpinion, @PathVariable("idUser") Long idUser) {
        opinionService.assignOpinionToUser(idOpinion, idUser);
    }
    @GetMapping("/get-all-opinion-by-user/{idUser}")
    public List<Opinion> getWhatsTheirOpinionOnMe(@PathVariable Long idUser){
        return opinionService.getWhatsTheirOpinionOnMe(idUser);
    }
}
