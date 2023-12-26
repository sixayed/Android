package com.example.notes.DataAccess;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notes.Entities.Note;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase database;

    public synchronized static AppDatabase getInstance(Context context) {
        if(database == null)
            database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "NoteDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        return database;
    }

    public abstract NoteDao noteDao();
}
