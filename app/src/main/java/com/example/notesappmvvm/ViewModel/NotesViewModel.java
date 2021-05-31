package com.example.notesappmvvm.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;



import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notesappmvvm.Model.Notes;
import com.example.notesappmvvm.Repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {           //import data from repository


    public NotesRepository repository;

    public LiveData<List<Notes>> getAllNotes;

    public LiveData<List<Notes>> highToLow;
    public LiveData<List<Notes>> lowToHigh;


    public NotesViewModel(Application application) {
        super(application);


        repository = new NotesRepository(application);
        getAllNotes = repository.getAllNotes;
        lowToHigh = repository.lowToHigh;
        highToLow = repository.highToLow;
    }

    public void insertNote(Notes notes){
        repository.insertNotes(notes);
    }

    public void deleteNote(int id){
        repository.deleteNotes(id);
    }

    public void updateNote(Notes notes){
        repository.updateNotes(notes);
    }
}
