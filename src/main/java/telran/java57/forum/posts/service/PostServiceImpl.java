package telran.java57.forum.posts.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import telran.java57.forum.posts.dao.PostRepository;
import telran.java57.forum.posts.dto.NewPostDto;
import telran.java57.forum.posts.dto.PostDto;
import telran.java57.forum.posts.model.Post;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    final PostRepository postRepository;
    final ModelMapper modelMapper;

    @Override
    public PostDto addNewPost(String author, NewPostDto newPostDto) {
        Post post = new Post(newPostDto.getTitle(), newPostDto.getContent(), author, newPostDto.getTags());
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto getPostById(String id) {
        Post post = postRepository.getPostById(id);
        if (post == null) {
            return null;
        }
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto updatePost(String id, NewPostDto newPostDto) {
        Post post = postRepository.getPostById(id);
        if (post == null) {
            return null;
        }
        post.setTitle(newPostDto.getTitle());
        post.setContent(newPostDto.getContent());
        ArrayList<String> oldTags = new ArrayList<>();
        oldTags.addAll(post.getTags());
        for (String tag : oldTags) {
            post.removeTag(tag);
        }
        ArrayList<String> newTags = new ArrayList<>();
        newTags.addAll(newPostDto.getTags());
        for (String tag : newTags) {
            post.addTag(tag);
        }
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public boolean deletePost(String id) {
        Post post = postRepository.getPostById(id);
        if (post != null) {
            postRepository.delete(post);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Collection<PostDto> getPostsByAuthor(String author) {
        Collection<Post> postCollection = postRepository.getPostsByAuthor(author);
        ArrayList<PostDto> res = new ArrayList<>();
        for(Post post:postCollection){
            res.add(modelMapper.map(post, PostDto.class));
        }
        return res;
    }
}
