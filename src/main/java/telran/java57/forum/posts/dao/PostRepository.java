package telran.java57.forum.posts.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import telran.java57.forum.posts.model.Post;

import java.util.Collection;

public interface PostRepository extends MongoRepository<Post,String> {
    Post getPostById(String id);
    Collection<Post> getPostsByAuthor(String author);
}
