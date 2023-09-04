package com.example.demo.auth.repository;
import com.example.demo.auth.repository.NoteRepository;
import com.example.demo.notes.domain.EAccessType;
import com.example.demo.notes.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE notes " +
            "SET " +
            "title =:title, " +
            "content= :content, " +
            "access= :access, " +
            "user_id= :userId " +
            "WHERE id =:id"
    )
    void updateNotesByIds (
            @Param("id") UUID id,
            @Param("title") String title,
            @Param("content") String content,
            @Param("access") EAccessType accessType,
            @Param("userId") long userId
    );
}