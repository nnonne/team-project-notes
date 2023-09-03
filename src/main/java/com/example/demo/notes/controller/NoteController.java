package com.example.demo.notes.controller;

import com.example.demo.auth.domain.User;
import com.example.demo.notes.domain.EAccessType;
import com.example.demo.notes.repository.NoteRepository;
import com.example.demo.notes.service.NoteService;
import com.example.demo.notes.domain.Note;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static java.awt.SystemColor.text;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;
    private final NoteRepository noteRepository;

    public NoteController(NoteService noteService, NoteRepository noteRepository) {
        this.noteService = noteService;
        this.noteRepository = noteRepository;
    }

    @GetMapping("/list/{userId}")
    public String userNotes(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID userId,
            Model model
    ) {
        if (currentUser == null || !currentUser.getId().equals(userId)) {
            return "redirect:/login";
        }

        List<Note> userNotes = noteRepository.findAllById(userId);

        model.addAttribute("notes", userNotes);
        model.addAttribute("isCurrentUser", true);

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

//    @GetMapping("/edit/{id}")
//    public String editNoteForm(@PathVariable UUID id, Model model) {
//        Note note = noteService.getNoteById(id);
//        model.addAttribute("note", note);
//        return "edit";
//    }
//
//    @PostMapping("/edit/{id}")
//    public String editNote(@PathVariable UUID id, @ModelAttribute("note") Note note) {
//        note.setId(id);
//        noteService.createOrUpdateNote(note);
//        return "redirect:/note/list";
//    }
//
//    @PostMapping("/delete/{id}")
//    public String deleteNote(@PathVariable UUID id) {
//        noteService.deleteNoteById(id);
//        return "redirect:/note/list";
//    }
}
