package com.example.demo.notes.repository;

import com.example.demo.auth.domain.User;
import com.example.demo.notes.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
    Optional<Note> findById(UUID id);
    List<Note> findByOwner(User owner);
    void deleteById(UUID id);
}
