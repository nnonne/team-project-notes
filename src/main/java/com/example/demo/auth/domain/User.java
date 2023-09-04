package com.example.demo.auth.domain;

import com.example.demo.notes.domain.Note;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @NotEmpty(message = "Username should not be empty.")
    @Size(min = 5, max = 50, message = "The length of the username must be from 5 to 50 characters inclusive.")
    private String username;

    @NotEmpty(message = "Password should not be empty.")
    @Size(min = 8, max = 100, message = "The length of the password must be from 8 to 100 characters inclusive.")
    private String password;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Note> notes;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
