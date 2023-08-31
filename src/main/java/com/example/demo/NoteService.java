package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
    public List<Note> listAll() {
        return (List<Note>) noteRepository.findAll();
    }
    public Note add(Note note) {
        return noteRepository.save(note);
    }

}