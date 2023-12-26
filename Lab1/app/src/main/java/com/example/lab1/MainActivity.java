package com.example.lab1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static final String KEY_COUNTER_VALUE = "counterValue";

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button incrementButton = findViewById(R.id.incrementButton);
        Button showCounterButton = findViewById(R.id.showCounterButton);

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt(KEY_COUNTER_VALUE, 0);
        }

        incrementButton.setOnClickListener(v -> counter++);

        showCounterButton.setOnClickListener(v -> showToast("Значение счетчика = " + counter));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_COUNTER_VALUE, counter);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
