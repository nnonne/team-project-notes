package com.example.demo.auth.controller;

import com.example.demo.auth.domain.User;
import com.example.demo.auth.repository.UserRepository;
import com.example.demo.auth.service.UserDetailsServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.notes.domain.Note;
import com.example.demo.auth.service.NoteService;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    @GetMapping("/list")
    public ModelAndView getAll() {
        ModelAndView result = new ModelAndView("note/list");
        List<Note> listNotes = noteService.listAll();
        result.addObject("notes", listNotes);
        return result;
    }
    @GetMapping("/create")
    public String showCreateForm(Model model,  @RequestParam("user_id") long userId) {
        //User user = (User) model.getAttribute("user");
        //model.addAttribute("note", new Note(user.getId()));
        model.addAttribute("note", new Note(userId));
        return "note/create";
    }

    @PostMapping("/create")
    public String createNote(@ModelAttribute Note note) {
        if (note.isValid()){
            noteService.add(note);
            return "redirect:/note/list";
        }
        return "error";
    }

}
