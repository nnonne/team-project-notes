package com.example.demo.notes.controller;

import com.example.demo.notes.service.NoteService;
import com.example.demo.notes.domain.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/list")
    public String listNotes(Model model) {
        List<Note> notes = noteService.getAllNotes();
        model.addAttribute("notes", notes);
        return "list";
    }

    @GetMapping("/create")
    public String createNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "create";
    }

    @PostMapping("/create")
    public String createNote(@ModelAttribute("note") Note note) {
        noteService.createOrUpdateNote(note);
        return "redirect:/note/list";
    }

    @GetMapping("/edit/{id}")
    public String editNoteForm(@PathVariable UUID id, Model model) {
        Note note = noteService.getNoteById(id);
        model.addAttribute("note", note);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String editNote(@PathVariable UUID id, @ModelAttribute("note") Note note) {
        note.setId(id);
        noteService.createOrUpdateNote(note);
        return "redirect:/note/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteNote(@PathVariable UUID id) {
        noteService.deleteNoteById(id);
        return "redirect:/note/list";
    }
}
