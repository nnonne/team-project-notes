package com.example.demo.notes.controller;

import com.example.demo.auth.service.UserDetailsImpl;

import com.example.demo.notes.domain.EAccessType;
import com.example.demo.notes.service.NoteService;
import com.example.demo.notes.domain.Note;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;




import org.springframework.security.core.Authentication;


@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }
    //кнопка  share link
    @GetMapping("/note/share/{id}")
    public String viewSharedNote(@PathVariable UUID id, Model model, Authentication authentication) {
        Note note = noteService.getById(id);
        Long currentUserId = ((UserDetailsImpl) authentication.getPrincipal()).getId();

        if (note != null && (note.getAccessType()== EAccessType.PUBLIC || currentUserId.equals(note.getOwner().getId()))) {
            model.addAttribute("note", note);
        } else {
            model.addAttribute("message", "Такої нотатки не існує.");
        }

        return "view_shared_note";
    }

    @GetMapping("/list")
    public String listNotes(Model model) {
        model.addAttribute("notes", noteService.listAll());
        return "list_notes";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("note", new Note());
        return "create_note";
    }

    @PostMapping("/create")
    public String createNote(@ModelAttribute Note note) {
        noteService.add(note);
        return "redirect:/note/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model) {
        Note note = noteService.getById(id);
        model.addAttribute("note", note);
        return "edit_note";
    }


    @PostMapping("/delete/{id}")
    public String deleteNote(@PathVariable UUID id) {
        noteService.deleteById(id);
        return "redirect:/note/list";
    }
}