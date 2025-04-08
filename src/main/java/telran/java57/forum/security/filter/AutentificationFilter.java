package telran.java57.forum.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;
import telran.java57.forum.accounting.dao.UserRepository;
import telran.java57.forum.accounting.dto.exception.AccountAlreadyExistsException;
import telran.java57.forum.accounting.dto.exception.AccountNotFoundException;
import telran.java57.forum.accounting.model.UserAccount;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

@Component
public class AutentificationFilter implements Filter {
    UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String auth = request.getHeader("Authorization");
        String token = auth.split(" ")[1];
        String decode = new String(Base64.getDecoder().decode(token));
        String login = decode.split(":")[0];
        String password = decode.split(":")[1];
        UserAccount account = userRepository.findById(login).orElseThrow(AccountNotFoundException::new);
        if (!BCrypt.checkpw(password, account.getPassword())) {
            throw new AccountAlreadyExistsException();
        }
        request = new WrappedRequest(request, login);
        filterChain.doFilter(request, response);
    }

    private class WrappedRequest extends HttpServletRequestWrapper{
        String login;

        public WrappedRequest(HttpServletRequest request, String login) {
            super(request);
            this.login = login;
        }

        @Override
        public Principal getUserPrincipal() {
            return () -> login;
        }
    }

}
