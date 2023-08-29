package com.example.Note.feature.auth;

import com.example.Note.feature.note.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/*
- Якщо користувач відкриває кореневу адресу, і він не авторизований - його перекидає на сторінку входу (/login).
- Якщо користувач відкриває кореневу адресу (/), і він авторизований у цьому браузері – його перекидає на список нотаток
 (/note/list).
- Імена користувачів зберігаються у базі даних. Допустиме ім'я користувача - будь-які символи латиниці та цифри.
Довжина імені – від 5 до 50 символів включно.
- Пароль користувача включає будь-які символи від 8 до 100 символів включно. У БД не зберігаємо паролі користувачів,
 лише хеші паролів.
 */

@RequestMapping ("/note")
@RequiredArgsConstructor
@Controller
public class NoteController {




    public List<Note> listNotes = Stream.of(

            new Note(1l, "some title note #1", "some context note#1"),
            new Note(2l, "some title note #2", "some context note#2"),
            new Note(3l, "some title note #3", "some context note#3")).collect(Collectors.toList());



    @GetMapping("/list")
    public ModelAndView getAll() {
    ModelAndView result = new ModelAndView("index");
    result.addObject("notes", listNotes);
        return result;
    }


//    @GetMapping("/{id}")
//    public Note getFindNoteById(@PathVariable long id) {
//    ModelAndView result = new ModelAndView("index");
//    result
//    return listNotes.stream().filter(e->e.getId().equals(id))
//                .findFirst()
//                .orElse(null);
//    }
}
