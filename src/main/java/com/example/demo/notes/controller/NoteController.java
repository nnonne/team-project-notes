package com.example.demo.notes.controller;

import com.example.demo.auth.domain.User;
import com.example.demo.auth.service.UserDetailsImpl;

import com.example.demo.notes.domain.EAccessType;
import com.example.demo.notes.service.NoteService;
import com.example.demo.notes.domain.Note;

import com.example.demo.notes.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        Note note = noteService.getNoteById(id);
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
        // Отримуємо поточного користувача з контексту безпеки (Spring Security)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Перевіряємо, чи користувач авторизований (не анонімний користувач)
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            String currentUsername = authentication.getName();

            // Знаходимо користувача за його ім'ям (username)
            User currentUser = UserService.findByUsername(currentUsername);

            // Отримуємо всі нотатки для поточного користувача
            List<Note> userNotes = noteService.getAllNotes(currentUser);

            model.addAttribute("notes", userNotes);

            // Тут ви можете додати інші дані для моделі, якщо потрібно
        }

        return "list_notes";
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