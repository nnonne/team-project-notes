package com.example.Note.feature.note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Data@AllArgsConstructor
@Service
public class Note {

    private Long id;
    private String title;
    private String content;


}
