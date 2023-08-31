package com.example.demo.entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    public boolean isValid() {
        return true;
    }

}