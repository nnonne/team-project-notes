package com.example.demo.notes.controller;

import com.example.demo.auth.domain.User;
import com.example.demo.auth.service.UserDetailsServiceImpl;
import com.example.demo.notes.domain.EAccessType;
import com.example.demo.notes.domain.Note;
import com.example.demo.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;
    private final UserDetailsServiceImpl userDetailsService;

    public NoteController(NoteService noteService, UserDetailsServiceImpl userDetailsService) {
        this.noteService = noteService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/list")
    public String listNotes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            String currentUsername = authentication.getName();
            User currentUser = userDetailsService.findUserByUsername(currentUsername);
            List<Note> userNotes = noteService.getAllNotes(currentUser);

            model.addAttribute("notes", userNotes);
        }

        return "list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")){
            model.addAttribute("note", new Note());
            return "/create";
        }
        return "redirect:/login";
    }

    @PostMapping("/create")
    public String createNote(@ModelAttribute("note") Note note, @RequestParam("accessType") String accessType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")){
            String currentUsername = authentication.getName();
            User currentUser = userDetailsService.findUserByUsername(currentUsername);
            note.setAccessType(EAccessType.valueOf(accessType));
            note.setAuthor(currentUser);

            noteService.createOrUpdateNote(note);
            return "redirect:/note/list";
        } else {
            return "redirect:/login";
        }
    }
}
