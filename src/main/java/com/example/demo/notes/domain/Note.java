package com.example.demo.notes.domain;

import com.example.demo.auth.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "uuid", unique = true, nullable = false)
    private UUID id;

    @NotEmpty(message = "Name should not be empty.")
    @Size(min = 5, max = 100, message = "The length of the name must be from 5 to 100 characters inclusive.")
    private String name;

    @NotEmpty(message = "Content should not be empty.")
    @Size(min = 5, max = 10000, message = "The length of the content must be from 5 to 10000 characters inclusive.")
    private String content;

    @Enumerated(EnumType.STRING)
    private EAccessType accessType;
    @ManyToOne // Багато нотаток можуть бути у одного користувача
    @JoinColumn(name = "user_id") // Зв'язок через поле user_id
    private User owner; // Поле для власника нотатки
    public Note(UUID id, String name, String content, EAccessType accessType) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.accessType = accessType;
    }

    public Note(UUID id) {
        this.id = id;
    }

    public Note() {
    }
}