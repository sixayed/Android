package com.example.notes.Interfaces;

import androidx.cardview.widget.CardView;

import com.example.notes.Entities.Note;

public interface INoteClickListener {
    void onClick(Note note);
    void onLongClick();
}
