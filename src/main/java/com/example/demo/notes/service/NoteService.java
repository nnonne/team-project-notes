package com.example.demo.notes.service;

import com.example.demo.notes.domain.Note;
import com.example.demo.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
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
    public List<Note> listAll() {
        return noteRepository.findAll();
    }

    public Note add(Note note) {
        return noteRepository.save(note);
    }

    public void deleteById(UUID id) {
        if (!noteRepository.existsById(id)) {
            throw new NoSuchElementException("Note with ID " + id + " not found.");
        }
        noteRepository.deleteById(id);
    }

    public void update(Note note) {
        if (!noteRepository.existsById(note.getId())) {
            throw new NoSuchElementException("Note with ID " + note.getId() + " not found.");
        }
        noteRepository.save(note);
    }

    public Note getById(UUID id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Note with ID " + id + " not found."));
    }

}