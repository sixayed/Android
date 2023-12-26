package com.example.notes.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.Entities.Note;
import com.example.notes.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {

    public ImageButton saveButton;
    public ImageButton backButton;

    private EditText editTextTitle, editTextNote;
    private Note note;

    private boolean isEditing = false;

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextNote = findViewById(R.id.et_note);
        editTextTitle = findViewById(R.id.et_title);

        saveButton = findViewById(R.id.save_button);
        backButton = findViewById(R.id.back_button);

        note = (Note) getIntent().getSerializableExtra("notePreviousState");
        if(note != null) {
            editTextTitle.setText(note.getTitle());
            editTextNote.setText(note.getNote());
            isEditing = true;
        }


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String noteDesc = editTextNote.getText().toString();

                if(title.isEmpty()) {
                    Toast.makeText(AddNoteActivity.this, "Empty title", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(noteDesc.isEmpty()) {
                    Toast.makeText(AddNoteActivity.this, "Empty note description", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(title.length() > 100){
                    Toast.makeText(AddNoteActivity.this, "Too long title", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(noteDesc.length() > 2000){
                    Toast.makeText(AddNoteActivity.this, "Too long title description", Toast.LENGTH_SHORT).show();
                    return;
                }

                Date date = new Date();

                if(!isEditing)
                    note = new Note();

                note.setTitle(title);
                note.setNote(noteDesc);
                note.setDate(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("note", note);
                setResult(Activity.RESULT_OK, intent);

                finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
