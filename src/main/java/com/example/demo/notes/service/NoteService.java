package com.example.demo.notes.service;

import com.example.demo.auth.domain.User;
import com.example.demo.auth.repository.UserRepository;
import com.example.demo.notes.domain.Note;
import com.example.demo.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public List<Note> getAllNotesByCurrentUser() {
        String currentUsername = getCurrentUsername();
        User currentUser = userRepository.findByUsername(currentUsername);
        if (currentUser != null) {
            return noteRepository.findByAuthor(currentUser);
        } else {
            throw new IllegalArgumentException("Current user not found.");
        }
    }

    public Note getNoteById(UUID id) {
        Note note = noteRepository.findById(id).orElse(null);
        if (note != null && note.getAuthor().getUsername().equals(getCurrentUsername())) {
            return note;
        } else {
            throw new IllegalArgumentException("Note not found.");
        }
    }

    public Note createOrUpdateNote(Note note) {
        String currentUsername = getCurrentUsername();
        User currentUser = userRepository.findByUsername(currentUsername);
        if (currentUser != null) {
            note.setAuthor(currentUser);
            return noteRepository.save(note);
        } else {
            throw new IllegalArgumentException("Current user not found.");
        }
    }

    public void deleteNoteById(UUID id) {
        Note note = noteRepository.findById(id).orElse(null);
        if (note != null && note.getAuthor().getUsername().equals(getCurrentUsername())) {
            noteRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Note not found.");
        }
    }

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
