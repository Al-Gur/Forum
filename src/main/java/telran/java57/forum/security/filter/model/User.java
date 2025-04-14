package telran.java57.forum.security.filter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import telran.java57.forum.accounting.model.Role;

import java.security.Principal;
import java.util.Set;

@AllArgsConstructor
@Getter
@Builder
public class User implements Principal {
    private String name;
    @Singular
    Set<Role> roles;
}
