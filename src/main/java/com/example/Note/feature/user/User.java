package com.example.Note.feature.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Table(name = "\"user\"")
@Data
@Entity
public class User {
    @Id
    private long id;
    private String name;
    private String password;
    private String role;


}
