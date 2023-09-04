package com.example.demo.notes.service;

import com.example.demo.auth.domain.User;
import com.example.demo.notes.domain.Note;
import com.example.demo.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNotes(User user) {
        return noteRepository.findByAuthor(user);
    }

    public Note getNoteById(UUID id) {
        return (Note) noteRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Note not found."));
    }

    public Note createOrUpdateNote(Note note) {
        return noteRepository.save(note);
    }

    public void deleteNoteById(UUID id) {
        noteRepository.deleteById(id);
    }

}
