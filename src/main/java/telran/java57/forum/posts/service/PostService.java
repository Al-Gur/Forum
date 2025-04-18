package telran.java57.forum.posts.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import telran.java57.forum.posts.dto.DatePeriodDto;
import telran.java57.forum.posts.dto.NewCommentDto;
import telran.java57.forum.posts.dto.NewPostDto;
import telran.java57.forum.posts.dto.PostDto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface PostService {

    PostDto addNewPost(String author, NewPostDto newPostDto);
    PostDto getPostById(String id);
    PostDto updatePost(String id, NewPostDto newPostDto);
    boolean deletePost(String id);
    Collection<PostDto> getPostsByAuthor(String author);
    PostDto addComment(String postId, String user, NewCommentDto newCommentDto);
    Collection<PostDto> findPostsByTags(List<String> tags);

    Iterable<PostDto> findPostsByPeriod(DatePeriodDto datePeriodDto);

    void addLike(String postId);
}
