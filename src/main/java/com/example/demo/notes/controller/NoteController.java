package com.example.demo.notes.controller;

import com.example.demo.auth.domain.User;
import com.example.demo.auth.service.UserDetailsServiceImpl;
import com.example.demo.notes.domain.EAccessType;
import com.example.demo.notes.domain.Note;
import com.example.demo.notes.service.NoteService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

            String currentUsername = authentication.getName();
            User currentUser = userDetailsService.findUserByUsername(currentUsername);
            List<Note> userNotes = noteService.getAllNotes(currentUser);

            model.addAttribute("notes", userNotes);
            return "list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("note", new Note());
        return "/create";
    }

    @PostMapping("/create")
    public String createNote(@ModelAttribute("note") @Valid Note note, BindingResult bindingResult, @RequestParam(value = "accessType", defaultValue = "PRIVATE") EAccessType accessType, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList());
                model.addAttribute("errorMessages", errorMessages);
                return "error";
            } else {
                String currentUsername = authentication.getName();
                User currentUser = userDetailsService.findUserByUsername(currentUsername);

                note.setAccessType(accessType);
                note.setAuthor(currentUser);
                noteService.createOrUpdateNote(note);
                return "redirect:/note/list";
            }
    }

    @PostMapping("/delete/{id}")
    public String deleteNote(@PathVariable("id") UUID id) {
        Note existingNote = noteService.getNoteById(id);
        if (existingNote == null) {
            return "redirect:/note/list";
        }

        noteService.deleteNoteById(id);
        return "redirect:/note/list";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") UUID id, Model model) {
        Note existingNote = noteService.getNoteById(id);
        if (existingNote == null) {
            return "redirect:/note/list";
        }
        model.addAttribute("note", existingNote);
        return "edit";
    }


    @PostMapping("/edit")
    public String editNote(@ModelAttribute("note") @Valid Note note, BindingResult bindingResult, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList());
                model.addAttribute("errorMessages", errorMessages);
                return "error";
            } else {
                Note existingNote = noteService.getNoteById(note.getId());
                if (existingNote == null) {
                    return "redirect:/note/list";
                }

                existingNote.setName(note.getName());
                existingNote.setContent(note.getContent());
                existingNote.setAccessType(note.getAccessType());
                noteService.createOrUpdateNote(existingNote);

                return "redirect:/note/list";
            }
    }
    @GetMapping("/share/{id}")
    public String viewSharedNote(@PathVariable("id") UUID id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String currentUserUsername = authentication.getName();
            Note note = noteService.getNoteById(id);

            if (note == null) {
                return "redirect:/note/note_not_found";
            }

            if (note.getAuthor().getUsername().equals(currentUserUsername) || note.getAccessType() == EAccessType.PUBLIC) {
                model.addAttribute("note", note);
                model.addAttribute("noteId", id);
                return "share";
            } else {
                return "redirect:/note/note_not_found";
            }
        }
}
