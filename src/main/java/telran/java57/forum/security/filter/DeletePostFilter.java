package telran.java57.forum.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import telran.java57.forum.accounting.model.Role;
import telran.java57.forum.posts.dao.PostRepository;
import telran.java57.forum.posts.model.Post;
import telran.java57.forum.security.filter.model.User;
import java.io.IOException;
import java.util.Optional;

@Component
@Order(60)
@RequiredArgsConstructor
public class DeletePostFilter implements Filter {
    final PostRepository postRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if(checkEndPoint(request.getMethod(), request.getServletPath())) {
            User user = (User) request.getUserPrincipal();
            String[] parts = request.getServletPath().split("/");
            String id = parts[parts.length-1];
            Optional<Post> post = postRepository.findById(id);
            if (post.isEmpty()){
                response.sendError(400, "Post not found");
                return;
            }
            String owner = post.get().getAuthor();
            if (!(user.getName().equalsIgnoreCase(owner) || user.getRoles().contains(Role.MODERATOR))){
                response.sendError(403, "You are not allowed to access this resource");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean checkEndPoint(String method, String path) {
        return HttpMethod.DELETE.matches(method) && path.matches("/forum/post/\\w+");
    }
}
