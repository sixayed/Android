package com.example.notes.Activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;

import com.example.notes.Adapter.NotesListAdapter;
import com.example.notes.DataAccess.AppDatabase;
import com.example.notes.Entities.Note;
import com.example.notes.Interfaces.INoteClickListener;
import com.example.notes.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton addNoteButton;
    private FloatingActionButton deleteButton;
    NotesListAdapter notesListAdapter;
    AppDatabase database;
    List<Note> notes = new ArrayList<Note>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        addNoteButton = findViewById(R.id.button_add_note);
        deleteButton = findViewById(R.id.button_delete_note);

        database = AppDatabase.getInstance(this);
        notes = database.noteDao().getAll();

        addNoteButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            addNoteLauncher.launch(intent);
        });

        deleteButton.setOnClickListener(v -> {
            deleteSelectedNotes();
        });

        setRecycle();
    }

    public void switchButtons() {
        deleteButton.setVisibility(deleteButton.getVisibility() == View.GONE? View.VISIBLE : View.GONE);
        addNoteButton.setVisibility(deleteButton.getVisibility() == View.GONE? View.VISIBLE : View.GONE);
    }

    private void deleteSelectedNotes() {
        SparseBooleanArray selectedNotes= notesListAdapter.getSelectedNotes();

        for (int i = selectedNotes.size() - 1; i >= 0; i--) {
            if (selectedNotes.valueAt(i)) {
                int position = selectedNotes.keyAt(i);
                Note note = notes.get(position);
                database.noteDao().delete(note);
                notes.remove(position);
                notesListAdapter.notifyItemRemoved(position);
            }
        }

        switchButtons();
        notesListAdapter.switchSelectionMode();
        notesListAdapter.notifyDataSetChanged();
    }

    private final ActivityResultLauncher<Intent> addNoteLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Note receivedNote = (Note) data.getSerializableExtra("note");
                        database.noteDao().insert(receivedNote);

                        notes.clear();
                        notes.addAll(database.noteDao().getAll());
                        notesListAdapter.notifyDataSetChanged();
                    }
                }
            });

    private final ActivityResultLauncher<Intent> updateNoteLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if(data != null) {
                        Note receivedNote = (Note) data.getSerializableExtra("note");
                        database.noteDao().update(receivedNote);

                        notes.clear();
                        notes.addAll(database.noteDao().getAll());
                        notesListAdapter.notifyDataSetChanged();
                    }
                }
            }
    );
    
    private void setRecycle() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        notesListAdapter = new NotesListAdapter(MainActivity.this, notes, clickListener);
        recyclerView.setAdapter(notesListAdapter);
    }

    private final INoteClickListener clickListener = new INoteClickListener() {
        @Override
        public void onClick(Note note) {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            intent.putExtra("notePreviousState", note);
            updateNoteLauncher.launch(intent);
        }

        @Override
        public void onLongClick() {
            switchButtons();
        }
    };
}