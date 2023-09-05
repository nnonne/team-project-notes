package com.example.demo.notes.domain;

import com.example.demo.auth.domain.User;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @NotEmpty(message = "Name should not be empty.")
    @Size(min = 5, max = 100, message = "The length of the name must be from 5 to 100 characters inclusive.")
    private String name;

    @NotEmpty(message = "Content should not be empty.")
    @Size(min = 5, max = 10000, message = "The length of the content must be from 5 to 10000 characters inclusive.")
    private String content;

    @NotNull(message = "Access type should not be empty.")
    @Enumerated(EnumType.STRING)
    @Column(name = "accessType")
    private EAccessType accessType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author")
    private User author;

    public Note() {
    }

    public Note(UUID id) {
        this.id = id;
    }

    public Note(UUID id, String name, String content, EAccessType accessType, User author) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.accessType = accessType;
        this.author = author;
    }
}
