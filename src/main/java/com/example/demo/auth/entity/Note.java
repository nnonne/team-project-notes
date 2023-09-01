package com.example.demo.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Table(name = "notes")
@Data
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    //@NotEmpty(message = "Note should belong to someone. Please login.")
    @Column(columnDefinition = "BINARY(16)")
    private long userId;

    @NotEmpty(message = "Title should not be empty.")
    @Size(min = 5, max = 100, message = "The length of the title must be from 5 to 100 characters inclusive.")
    private String title;

    @NotEmpty(message = "Content should not be empty.")
    @Size(min = 5, max = 10000, message = "The length of the content must be from 5 to 10000 characters inclusive.")
    private String content;

    @Enumerated(EnumType.STRING)
    private NoteAccess access;

    public Note(long userId) {
        this.userId = userId;
    }

    public boolean isValid() {
        return true;
    }
}