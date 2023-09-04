package com.example.demo.auth.service;

import com.example.demo.auth.repository.NoteRepository;

import com.example.demo.notes.domain.Note;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

@Data

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
    public List<Note> listAll() {
        return noteRepository.findAll();
    }


    public void add(Note note) {
        noteRepository.save(note);
    }


    public Note getById(long id) {
        return noteRepository.getReferenceById(id);
    }


    @Transactional
    public void update(Note note) {
        noteRepository.updateNotesByIds(note.getId(), note.getTitle(), note.getContent(), note.getAccessType(), note.getUserId());
    }


    public void deleteById(long id) {
        noteRepository.deleteById(id);
    }


}