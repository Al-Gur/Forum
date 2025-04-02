package telran.java57.forum.posts.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import telran.java57.forum.posts.model.Post;

public interface PostRepository extends MongoRepository<Post,String> {
    Post getPostById(String id);
}
