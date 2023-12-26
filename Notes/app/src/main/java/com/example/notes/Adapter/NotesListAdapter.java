package com.example.notes.Adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Entities.Note;
import com.example.notes.Interfaces.INoteClickListener;
import com.example.notes.R;
import com.example.notes.View.NotesViewHolder;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesViewHolder>{

    private Context context;
    private List<Note> notesList;
    private SparseBooleanArray selectedNotes= new SparseBooleanArray();
    private boolean isSelectionMode = false;


    INoteClickListener clickListener;

    public NotesListAdapter(Context context, List<Note> notesList, INoteClickListener clickListener)
    {
        this.context = context;
        this.notesList = notesList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.note, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.textViewTitle.setText(notesList.get(position).getTitle());
        holder.textViewDate.setText(notesList.get(position).getDate());

        holder.textViewTitle.setSelected(true);
        holder.textViewDate.setSelected(true);

        holder.checkBox.setVisibility(isSelectionMode ? View.VISIBLE : View.GONE);
        holder.checkBox.setChecked(selectedNotes.get(position, false));


        holder.notesContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSelectionMode) {
                    toggleSelection(holder.getAdapterPosition());
                    holder.checkBox.setChecked(selectedNotes.get(holder.getAdapterPosition(), false));
                }
                else {
                    clickListener.onClick(notesList.get(holder.getAdapterPosition()));
                }
            }
        });

        holder.notesContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clickListener.onLongClick();
                switchSelectionMode();
                toggleSelection(holder.getAdapterPosition());
                holder.checkBox.setChecked(selectedNotes.get(holder.getAdapterPosition(), false));
                notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    private void toggleSelection(int position) {
        selectedNotes.put(position, !selectedNotes.get(position, false));
    }

    public void switchSelectionMode() {
        isSelectionMode = !isSelectionMode;
        clearSelection();
    }

    public void clearSelection() {
        selectedNotes.clear();
    }

    public SparseBooleanArray getSelectedNotes() {
        return selectedNotes;
    }
}
