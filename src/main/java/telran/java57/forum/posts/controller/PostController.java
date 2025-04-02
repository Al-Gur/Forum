package telran.java57.forum.posts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import telran.java57.forum.posts.dto.NewPostDto;
import telran.java57.forum.posts.dto.PostDto;
import telran.java57.forum.posts.service.PostService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
public class PostController {
     final PostService postService;
    @PostMapping("/post/{author}")
    public PostDto addNewPost(@PathVariable String author, @RequestBody NewPostDto newPostDto){
        return postService.addNewPost(author,newPostDto);
    }
    @GetMapping("/post/{id}")
    public PostDto getPostById(@PathVariable String id){
        return postService.getPostById(id);
    }
}
