package com.example.notesappmvvm.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.notesappmvvm.Dao.NotesDao;
import com.example.notesappmvvm.Database.NotesDatabase;
import com.example.notesappmvvm.Model.Notes;

import java.util.List;


public class NotesRepository {             //import data from Dao


    //this is variable of notesDao which is in database
    public NotesDao notesDao;

    public LiveData<List<Notes>> getAllNotes;

    public LiveData<List<Notes>> highToLow;
    public LiveData<List<Notes>> lowToHigh;


    public NotesRepository(Application application) {
        //we have to get Dao from notesDatabase
        NotesDatabase database = NotesDatabase.getDatabaseInstance(application);
        //we get notes dao and define here
        notesDao = database.notesDao();
        getAllNotes = notesDao.getAllNotes();
        highToLow = notesDao.highToLow();
        lowToHigh = notesDao.lowToHigh();
    }


    public void insertNotes(Notes notes)        //we get note from user and
    {
        notesDao.insertNotes(notes);     //add it here
    }

    public void deleteNotes(int id)
    {
        notesDao.deleteNotes(id);
    }

    public void updateNotes(Notes notes)
    {
        notesDao.updateNotes(notes);
    }
}
