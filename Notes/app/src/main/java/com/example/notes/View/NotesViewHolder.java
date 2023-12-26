package com.example.notes.View;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;

public class NotesViewHolder extends RecyclerView.ViewHolder {

    public CardView notesContainer;
    public TextView textViewTitle, textViewDate;
    public CheckBox checkBox;
    
    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);

        notesContainer  = itemView.findViewById(R.id.notes_container);
        textViewTitle = itemView.findViewById(R.id.tv_title);
        textViewDate = itemView.findViewById(R.id.tv_date);
        checkBox = itemView.findViewById(R.id.check_box);
    }
}
