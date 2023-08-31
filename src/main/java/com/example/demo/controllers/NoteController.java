package com.example.demo.controllers;

//потрібно дописати Authentication і потестити

import com.example.demo.entity.Note;
import com.example.demo.service.NoteService;
import com.example.demo.service.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/note/share/{id}")
    public String viewSharedNote(@PathVariable Long id, Model model, Authentication authentication) {
        Note note = noteService.getById(id);
        Long currentUserId = ((UserDetailsImpl) authentication.getPrincipal()).getId();

        if (note != null && (note.isValid() || currentUserId.equals(note.getUser()))) {
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
    public String showEditForm(@PathVariable Long id, Model model) {
        Note note = noteService.getById(id);
        model.addAttribute("note", note);
        return "edit_note";
    }

    @PostMapping("/edit")
    public String editNote(@ModelAttribute Note note) {
        noteService.update(note);
        return "redirect:/note/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        noteService.deleteById(id);
        return "redirect:/note/list";
    }
}
