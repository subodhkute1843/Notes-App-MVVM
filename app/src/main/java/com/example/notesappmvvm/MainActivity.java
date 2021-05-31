package com.example.notesappmvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.notesappmvvm.Activity.InsertNote;
import com.example.notesappmvvm.Adapter.NotesAdapter;
import com.example.notesappmvvm.Model.Notes;
import com.example.notesappmvvm.ViewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton mnewNotesBtn;
    RecyclerView notesRecyclerView;
    NotesAdapter adapter;

    TextView noFilter, highToLow, lowToHigh;

    NotesViewModel notesViewModel;

    List<Notes> filterNotesAllList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        mnewNotesBtn = findViewById(R.id.newNotesBtn);
        notesRecyclerView = findViewById(R.id.notesRecyclerView);

        mnewNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InsertNote.class));
            }
        });

        //this is livedata
        /**
         notesViewModel.lowToHigh.observe(this , notes -> {

         //we want grid layout with 2 parts that's why grid and 2
         notesRecyclerView.setLayoutManager(new GridLayoutManager(this , 2));
         adapter = new NotesAdapter(MainActivity.this , notes);
         notesRecyclerView.setAdapter(adapter);

         });
         **/


        noFilter = findViewById(R.id.noFilter);
        lowToHigh = findViewById(R.id.lowToHigh);
        highToLow = findViewById(R.id.highToLow);

        noFilter.setBackgroundResource(R.drawable.filter_seleceted);


        noFilter.setOnClickListener(v -> {
            loadData(0);
            lowToHigh.setBackgroundResource(R.drawable.yes_btn);
            highToLow.setBackgroundResource(R.drawable.yes_btn);
            noFilter.setBackgroundResource(R.drawable.filter_seleceted);
        });
        highToLow.setOnClickListener(v -> {
            loadData(1);
            lowToHigh.setBackgroundResource(R.drawable.yes_btn);
            noFilter.setBackgroundResource(R.drawable.yes_btn);
            highToLow.setBackgroundResource(R.drawable.filter_seleceted);
        });
        lowToHigh.setOnClickListener(v -> {
            loadData(2);
            noFilter.setBackgroundResource(R.drawable.yes_btn);
            highToLow.setBackgroundResource(R.drawable.yes_btn);
            lowToHigh.setBackgroundResource(R.drawable.filter_seleceted);
        });

        notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                setAdapter(notes);
                filterNotesAllList = notes;
            }
        });

    }

    private void loadData(int i) {
        if (i == 0) {
            notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filterNotesAllList = notes;
                }
            });
        } else if (i == 1) {
            notesViewModel.highToLow.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filterNotesAllList = notes;
                }
            });
        } else if (i == 2) {
            notesViewModel.lowToHigh.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filterNotesAllList = notes;
                }
            });
        }
    }

    public void setAdapter(List<Notes> notes) {
        notesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new NotesAdapter(MainActivity.this, notes);
        notesRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_notes, menu);


        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setQueryHint("Search Notes Here . . .");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                NotesFilter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void NotesFilter(String newText) {

        ArrayList<Notes> FilterNames = new ArrayList<>();

        for (Notes notes : this.filterNotesAllList) {
            if (notes.notesTitle.contains(newText) || notes.notesSubtitle.contains(newText)) {
                FilterNames.add(notes);
            }
        }
        this.adapter.searchNotes(FilterNames);

    }
}
