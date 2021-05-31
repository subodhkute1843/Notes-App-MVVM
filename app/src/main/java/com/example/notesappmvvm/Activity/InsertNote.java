package com.example.notesappmvvm.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notesappmvvm.Model.Notes;
import com.example.notesappmvvm.R;
import com.example.notesappmvvm.ViewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;


public class InsertNote extends AppCompatActivity {

    EditText notesTitle, notesSubtitle, notesData;
    FloatingActionButton mdoneNoteBtn;
    ImageView grnPr, yelPr, redPr;

    NotesViewModel notesViewModel;

    String title, subtitle, notes;
    String priority = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_note);

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);


        notesTitle = findViewById(R.id.notesTitle);
        notesSubtitle = findViewById(R.id.notesSubtitle);
        notesData = findViewById(R.id.notesData);

        grnPr = findViewById(R.id.grnPr);
        yelPr = findViewById(R.id.yelPr);
        redPr = findViewById(R.id.redPr);

        grnPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grnPr.setImageResource(R.drawable.done);
                redPr.setImageResource(0);
                yelPr.setImageResource(0);
                priority = "1";
            }
        });

        yelPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yelPr.setImageResource(R.drawable.done);
                redPr.setImageResource(0);
                grnPr.setImageResource(0);
                priority = "2";
            }
        });

        redPr.setOnClickListener(v -> {
            redPr.setImageResource(R.drawable.done);
            grnPr.setImageResource(0);
            yelPr.setImageResource(0);
            priority = "3";
        });

        mdoneNoteBtn = findViewById(R.id.doneNoteBtn);

        mdoneNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = notesTitle.getText().toString();
                subtitle = notesSubtitle.getText().toString();
                notes = notesData.getText().toString();

                CreateNote(title, subtitle, notes);
            }


        });


    }

    private void CreateNote(String title, String subtitle, String notes) {

        //this is to show date in notes
        Date date = new Date();
        CharSequence sequence = DateFormat.format("dd-MM-yyyy \n'T' HH:mm", date.getTime());

        Notes notes1 = new Notes();
        notes1.notesTitle = title;
        notes1.notesSubtitle = subtitle;
        notes1.notes = notes;
        notes1.notesPriority = priority;
        notes1.notesDate = sequence.toString();

        notesViewModel.insertNote(notes1);

        Toast.makeText(InsertNote.this, "Notes Created Successfully!", Toast.LENGTH_SHORT).show();

        finish();

    }
}
