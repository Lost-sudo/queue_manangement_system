package com.lostsudo.queuesystem.security.services;

import com.lostsudo.queuesystem.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String schoolId;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UserDetailsImpl(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getSchoolId(), user.getPassword(), authorities);
    }

    @Override
    @NullMarked
    public String getUsername() {
        return email;
    }

}
