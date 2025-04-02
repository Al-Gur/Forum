package telran.java57.forum.posts.service;

import telran.java57.forum.posts.dto.NewPostDto;
import telran.java57.forum.posts.dto.PostDto;

public interface PostService {

    PostDto addNewPost(String author, NewPostDto newPostDto);
    PostDto getPostById(String id);
    PostDto updatePost(String id, NewPostDto newPostDto);
}
