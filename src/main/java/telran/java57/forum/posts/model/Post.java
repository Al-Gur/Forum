package telran.java57.forum.posts.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Document(collection = "posts")
public class Post {
    String id;
    @Setter
    String title;
    @Setter
    String content;
    @Setter
    String author;
    LocalDateTime dateCreated = LocalDateTime.now();
    Set<String> tags = new HashSet<>();
    Integer likes = 0;
    List<Comment> comments = new ArrayList<>();

    public Post(String title, String content, String author, Set<String> tags) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.tags = tags;
    }

    public boolean addTag(String tag){
        return tags.add(tag);
    }

    public boolean removeTag(String tag){
        return tags.remove(tag);
    }

    public void addLike(){
        likes++;
    }

    public boolean addComment(Comment comment){
        return comments.add(comment);
    }

    public boolean removeComment(Comment comment){
        return comments.remove(comment);
    }

}
