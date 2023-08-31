package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }
    public List<Note> listNotes = Stream.of(

            new Note(1l, "some title note #1", "some context note#1"),
            new Note(2l, "some title note #2", "some context note#2"),
            new Note(3l, "some title note #3", "some context note#3")).collect(Collectors.toList());



    @GetMapping("/list")
    public ModelAndView getAll() {
        ModelAndView result = new ModelAndView("list");
        result.addObject("notes", listNotes);
        return result;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("note", new Note());
        return "create";
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
