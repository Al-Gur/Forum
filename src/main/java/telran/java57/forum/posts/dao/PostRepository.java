package telran.java57.forum.posts.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import telran.java57.forum.posts.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface PostRepository extends MongoRepository<Post,String> {
    Post getPostById(String id);
    Collection<Post> getPostsByAuthor(String author);
    //@Query("{'tags': {$in: }}")
    Stream<Post> findPostsByTagsInIgnoreCase(List<String> tags);
    Stream<Post> findPostsByDateCreatedBetween(LocalDate date1, LocalDate date2);
}
