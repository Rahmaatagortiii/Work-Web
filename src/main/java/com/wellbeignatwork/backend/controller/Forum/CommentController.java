package com.wellbeignatwork.backend.controller.Forum;

import com.wellbeignatwork.backend.entity.Forum.Comment;
import com.wellbeignatwork.backend.service.Forum.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/Comment")
public class CommentController {
    private CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService){
        this.commentService=commentService;
    }
    @PostMapping("/add-comment/{idPost}/{idUser}")
    public ResponseEntity<Comment> createPost(@RequestBody Comment comment,@PathVariable int idPost,@PathVariable Long idUser) {
        return new ResponseEntity<>(this.commentService.createcomment(comment,idPost,idUser), HttpStatus.OK);
    }
    @GetMapping("/all-comment")
    public ResponseEntity<Collection<Comment>> getAllPosts() {
        return new ResponseEntity<>(this.commentService.getAll(), HttpStatus.OK);
    }
    @PutMapping("/update-comment")
    public ResponseEntity<Comment> updatePost(@RequestBody Comment comment) {
        return new ResponseEntity<>(this.commentService.updatecomment(comment), HttpStatus.OK);
    }
    @DeleteMapping("/delete-comment/{id}")
    public ResponseEntity deletePostById(@PathVariable int id) {
        this.commentService.deletecomment(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/PostSatisfaction/{idPost}")
    public List<Integer> PostSatisfaction(@PathVariable int idPost){
        return commentService.PostSatisfaction(idPost);
    }
    @GetMapping("/sortByDate/{idPost}")
    public List<Comment> sortByDate(@PathVariable int idPost){
        return commentService.sortByDate(idPost);
    }

}
