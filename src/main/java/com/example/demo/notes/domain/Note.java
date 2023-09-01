package com.example.demo.notes.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@Entity
public class Note {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @NotEmpty(message = "Name should not be empty.")
    @Size(min = 5, max = 100, message = "The length of the name must be from 5 to 100 characters inclusive.")
    private String name;

    @NotEmpty(message = "Content should not be empty.")
    @Size(min = 5, max = 10000, message = "The length of the content must be from 5 to 10000 characters inclusive.")
    private String content;

    @Enumerated(EnumType.STRING)
    private EAccessType accessType;

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