package telran.java57.forum.posts.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import telran.java57.forum.posts.dao.PostRepository;
import telran.java57.forum.posts.dto.DatePeriodDto;
import telran.java57.forum.posts.dto.NewCommentDto;
import telran.java57.forum.posts.dto.NewPostDto;
import telran.java57.forum.posts.dto.PostDto;
import telran.java57.forum.posts.model.Comment;
import telran.java57.forum.posts.model.Post;

import java.time.LocalDate;
import java.util.*;

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

    @Override
    public PostDto addComment(String postId, String user, NewCommentDto newCommentDto) {
        Post post = postRepository.getPostById(postId);
        if (post == null) {
            return null;
        }
        post.addComment(new Comment(newCommentDto.getMessage(), user));
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public Collection<PostDto> findPostsByTags(List<String>tags) {
        return postRepository.findPostsByTagsInIgnoreCase(tags)
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }

    @Override
    public Iterable<PostDto> findPostsByPeriod(DatePeriodDto datePeriodDto) {
        return postRepository.findPostsByDateCreatedBetween(
                datePeriodDto.getDateFrom(), datePeriodDto.getDateTo())
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }

    @Override
    public void addLike(String postId) {
        Post post = postRepository.getPostById(postId);
        if (post == null) {
            return;
        }
        post.addLike();
        postRepository.save(post);
    }
}
