package com.wellbeignatwork.backend.controller.Forum;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.WriterException;
import com.lowagie.text.DocumentException;
import com.wellbeignatwork.backend.entity.Forum.Post;
import com.wellbeignatwork.backend.service.Event.ActivityServiceImp;
import com.wellbeignatwork.backend.service.Forum.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/Post")
public class PostController {
    private PostService postService;
    @Autowired
    public PostController(PostService postService){
        this.postService=postService;
    }
    @PostMapping("/create-post")
    public ResponseEntity<Post> createPost(@RequestParam("file") MultipartFile file, @RequestParam("post")String post) throws IOException {

        Post p = new ObjectMapper().readValue(post, Post.class);
        log.info("post : "+p.getContent());

        return new ResponseEntity<>(this.postService.createpost(p,file), HttpStatus.OK);
    }
    @PostMapping("/add-post/{idUser}")
    public ResponseEntity<Post> addPost(@RequestBody Post post,@PathVariable Long idUser) {

        return new ResponseEntity<>(this.postService.addPost(post,idUser), HttpStatus.OK);
    }
    @GetMapping("/all-post")
    public ResponseEntity<Collection<Post>> getAllPosts() {
        return new ResponseEntity<>(this.postService.getAll(), HttpStatus.OK);
    }
    @PutMapping("/update-post")
    public ResponseEntity<Post> updatePost(@RequestBody Post post) {
        return new ResponseEntity<>(this.postService.updatepost(post), HttpStatus.OK);
    }
    @DeleteMapping("/delete-post/{id}")
    public ResponseEntity deletePostById(@PathVariable int id) {
        this.postService.deletepost(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/groupByPreference/{idUser}")
    public List<Post> groupByPreference(@PathVariable Long idUser){
        return this.postService.groupByPreference(idUser);
    }
    @GetMapping("assignUserToPost/{idUser}/{idPost}")
    public Post assignUserToPost(@PathVariable Long idUser,@PathVariable int idPost){
        return postService.assignUserToPost(idUser, idPost);
    }
    @GetMapping("/TrendingPost")
    public List<Post> getTrendingPost(){
        return postService.getTrendingPost();
    }

    @GetMapping("/downloadArticle/{idPost}")
    @ResponseBody
    public void downloadArticle (@PathVariable int idPost ,

                                   HttpServletResponse response
    )throws DocumentException, IOException {

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=article" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        postService.downloadArticle(idPost,response);


    }
}
