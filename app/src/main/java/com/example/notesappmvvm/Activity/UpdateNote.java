package com.example.notesappmvvm.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesappmvvm.Model.Notes;
import com.example.notesappmvvm.R;
import com.example.notesappmvvm.ViewModel.NotesViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class UpdateNote extends AppCompatActivity {

    EditText upTitle, upSubtitle, upNotes;
    FloatingActionButton mupdtNotebtn;
    ImageView grnPr, yelPr, redPr;

    NotesViewModel notesViewModel;

    String priority = "1";
    String stitle, ssubtitle,  spriority, snotes;
    int iId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);


        upTitle = findViewById(R.id.upTitle);
        upSubtitle = findViewById(R.id.upSubtitle);
        upNotes = findViewById(R.id.upNotes);

        iId = getIntent().getIntExtra("id" , 0);
        stitle = getIntent().getStringExtra("title");
        ssubtitle = getIntent().getStringExtra("subtitle");
        spriority = getIntent().getStringExtra("priority");
        snotes = getIntent().getStringExtra("notes");


        upTitle.setText(stitle);
        upSubtitle.setText(ssubtitle);
        upNotes.setText(snotes);

        grnPr = findViewById(R.id.grnPr);
        yelPr = findViewById(R.id.yelPr);
        redPr = findViewById(R.id.redPr);


        switch (spriority){
            case "1":
                grnPr.setImageResource(R.drawable.done);
                break;
            case "2":
                yelPr.setImageResource(R.drawable.done);
                break;
            case "3":
                redPr.setImageResource(R.drawable.done);
                break;
        }






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

        mupdtNotebtn = findViewById(R.id.updtNotebtn);
        mupdtNotebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = upTitle.getText().toString();
                String subtitle = upSubtitle.getText().toString();
                String notes = upNotes.getText().toString();

                UpdateNotes(title, subtitle, notes);
            }
        });
    }

    private void UpdateNotes(String title, String subtitle, String notes) {


        //this is to show date in notes
        Date date = new Date();
        CharSequence sequence = DateFormat.format("dd-MMMM-yyyy \n'T' HH:mm", date.getTime());

        Notes updateNotes = new Notes();

        updateNotes.id = iId;
        updateNotes.notesTitle = title;
        updateNotes.notesSubtitle = subtitle;
        updateNotes.notes = notes;
        updateNotes.notesPriority = priority;
        updateNotes.notesDate = sequence.toString();

        notesViewModel.updateNote(updateNotes);



        Toast.makeText(this, "Notes Updated Successfully!", Toast.LENGTH_SHORT).show();

        finish();







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.ic_delete){
            BottomSheetDialog sheetDialog = new BottomSheetDialog(UpdateNote.this , R.style.BottomSheetStyle);


            View view = LayoutInflater.from(UpdateNote.this)
                    .inflate(R.layout.delete_bottom_sheet , (LinearLayout)findViewById(R.id.bottomSheet));

            sheetDialog.setContentView(view);

            TextView yes , no;
            yes = view.findViewById(R.id.delete_yes);
            no = view.findViewById(R.id.delete_no);

            yes.setOnClickListener(v -> {
                notesViewModel.deleteNote(iId);
                finish();
            });

            no.setOnClickListener(v -> {
                sheetDialog.dismiss();
            });



            sheetDialog.show();
        }

        return true;

    }
}
