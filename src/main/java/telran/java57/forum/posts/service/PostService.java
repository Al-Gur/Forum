package telran.java57.forum.posts.service;

import telran.java57.forum.posts.dto.NewPostDto;
import telran.java57.forum.posts.dto.PostDto;

import java.util.Collection;
import java.util.List;

public interface PostService {

    PostDto addNewPost(String author, NewPostDto newPostDto);
    PostDto getPostById(String id);
    PostDto updatePost(String id, NewPostDto newPostDto);
    boolean deletePost(String id);
    Collection<PostDto> getPostsByAuthor(String author);


}
