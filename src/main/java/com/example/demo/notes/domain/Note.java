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

    @Enumerated(EnumType.STRING)
    @Column(name = "accessType")
    private EAccessType accessType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
}
