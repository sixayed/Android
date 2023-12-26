package com.example.notes.DataAccess;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notes.Entities.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert()
    void insert(Note note);

    @Query("SELECT * FROM Notes ORDER BY id DESC")
    List<Note> getAll();

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}
