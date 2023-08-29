package com.example.Note.feature.user.dto;

import com.example.Note.feature.user.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Builder
@Data
public class UserDTO {
    private long id;
    private String name;
    private String password;
    private String role;


    public static UserDTO fromUser(User user) {


        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public static User fromDto(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        return user;
    }
}
