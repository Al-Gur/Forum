package telran.java57.forum.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.HttpSecurityDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@EnableWebSecurity
@Configuration

public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity.authorizeHttpRequests( authorize -> authorize
                .requestMatchers("/account/register").permitAll()

                .requestMatchers(HttpMethod.DELETE, "/account/user/{login}")
                .access(new WebExpressionAuthorizationManager("hasRole('ADMINISTRATOR') " +
                        "or authentication.name == #login"))

                .requestMatchers(HttpMethod.PUT, "/account/user/{login}")
                .access(new WebExpressionAuthorizationManager("authentication.name == #login"))

                .requestMatchers("/account/user/{login}/role/{role}")
                .access(new WebExpressionAuthorizationManager("hasRole('ADMINISTRATOR')"))

                .requestMatchers(HttpMethod.POST, "/forum/post/{author}")
                .access(new WebExpressionAuthorizationManager("authentication.name == #author"))

                //.requestMatchers(HttpMethod.DELETE, "/forum/post/{id}")
                //    .access(new WebExpressionAuthorizationManager("hasRole('MODERATOR') " +
                //        "or authentication.name == #login"))


                .requestMatchers(HttpMethod.PUT, "/forum/post/{id}/comment/{author}")
                .access(new WebExpressionAuthorizationManager("authentication.name == #author"))

                .requestMatchers("/forum/posts").permitAll()
                .anyRequest().authenticated());
        return httpSecurity.build();
    }
}
