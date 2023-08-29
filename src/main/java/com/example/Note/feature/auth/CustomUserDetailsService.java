package com.example.Note.feature.auth;

import com.example.Note.feature.user.User;
import com.example.Note.feature.user.UserRepository;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData userData = getByIdOrNull(username);
        if (userData == null) {
            throw new UsernameNotFoundException(username);
        }

        UserDetails result = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Arrays.stream(userData.getRole().split(","))
                        .map(it -> (GrantedAuthority) () -> it)
                        .collect(Collectors.toList());
            }

            @Override
            public String getPassword() {
                return userData.getPassword();
            }

            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };

        return result;
    }


    private UserData getByIdOrNull(String name) {
     userRepository.searchUserByName(name);
        User newUser = userRepository.searchUserByName(name);
   return new UserData(newUser.getPassword(), newUser.getRole());

    }



//    private UserData getByIdOrNull(String name) {
//        String sql = "SELECT password, role FROM \"user\" WHERE name = :name";
//        return jdbcTemplate.queryForObject(
//                sql,
//                Map.of("name", name),
//                new UserDataMapper()
//        );
//    }

    private class UserDataMapper implements RowMapper<UserData> {
        @Override
        public UserData mapRow(ResultSet rs, int rowNum) throws SQLException {
            return UserData.builder()
                    .password(rs.getString("password"))
                    .role(rs.getString("role"))
                    .build();
        }
    }

    @Builder
    @Data
    public static class UserData {
        private String password;
        private String role;
        /*

INSERT INTO "user" (name, password, authority)
VALUES ('user@mail.com', 'user_password', 'USER'),
       ('admin@mail.com', 'admin_password', 'ADMIN');

         */
    }
}
