package telran.java57.forum.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import telran.java57.forum.accounting.dao.UserAccountRepository;
import telran.java57.forum.accounting.dto.exceptions.UserNotFoundException;
import telran.java57.forum.accounting.dto.exceptions.WrongPasswordException;
import telran.java57.forum.accounting.model.UserAccount;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

@Component
@Order(10)
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {
    final UserAccountRepository userAccountRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            String[] credentials = getCredentials(authorizationHeader);

            UserAccount userAccount = userAccountRepository.findById(credentials[0])
                    .orElseThrow(UserNotFoundException::new);

            if (BCrypt.checkpw(credentials[1], userAccount.getPassword())) {
                request = new WrappedRequest(request, credentials[0]);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String[] getCredentials(String headerValue) {
        String token = headerValue.split(" ")[1];
        String decode = new String(Base64.getDecoder().decode(token));
        return decode.split(":");
    }

    private class WrappedRequest extends HttpServletRequestWrapper {
        private String login;

        public WrappedRequest(HttpServletRequest request, String login) {
            super(request);
            this.login = login;
        }

        @Override
        public Principal getUserPrincipal() {
            return () -> {
                return login;
            };
        }
    }
}
