package com.example.demo.notes.repository;

import com.example.demo.auth.domain.User;
import com.example.demo.notes.domain.EAccessType;
import com.example.demo.notes.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
    Optional<Note> findById(UUID id);
    void deleteById(UUID id);
    List<Note> findAllById(@Param("userId") UUID userId);

    List<User> findByUsername(String username);
    List<User> findByAuthor(User author);

}
