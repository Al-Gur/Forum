package telran.java57.forum.posts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import telran.java57.forum.posts.dto.NewCommentDto;
import telran.java57.forum.posts.dto.NewPostDto;
import telran.java57.forum.posts.dto.PostDto;
import telran.java57.forum.posts.service.PostService;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
public class PostController {
    final PostService postService;

    @PostMapping("/post/{author}")
    public PostDto addNewPost(@PathVariable String author, @RequestBody NewPostDto newPostDto) {
        return postService.addNewPost(author, newPostDto);
    }

    @GetMapping("/post/{id}")
    public PostDto findPostById(@PathVariable String id) {
        return postService.getPostById(id);
    }

    @PutMapping("/post/{id}")
    public PostDto updatePost(@PathVariable String id, @RequestBody NewPostDto newPostDto) {
        return postService.updatePost(id, newPostDto);
    }

    @DeleteMapping("/post/{id}")
    public boolean deletePost(@PathVariable String id) {
        return postService.deletePost(id);
    }

    @GetMapping("/posts/author/{author}")
    public Collection<PostDto> findPostsByAuthor(@PathVariable String author) {
        return postService.getPostsByAuthor(author);
    }

    @PutMapping("/post/{postId}/comment/{user}")
    public PostDto addComment(@PathVariable String postId,
                              @PathVariable String user,
                              @RequestBody NewCommentDto newCommentDto) {
        return postService.addComment(postId, user, newCommentDto);
    }
}
