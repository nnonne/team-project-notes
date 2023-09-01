package com.example.demo.auth.domain;

import com.example.demo.notes.domain.Note;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Username should not be empty.")
    @Size(min = 5, max = 50, message = "The length of the username must be from 5 to 50 characters inclusive.")
    private String username;

    @NotEmpty(message = "Password should not be empty.")
    @Size(min = 8, max = 100, message = "The length of the password must be from 8 to 100 characters inclusive.")
    private String password;



    @OneToMany(mappedBy = "owner") // Зв'язок через поле owner в класі Note
    private List<Note> notes; // Поле для нотаток користувача
}