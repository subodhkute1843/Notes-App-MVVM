package com.example.notesappmvvm.Database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notesappmvvm.Model.Notes;
import com.example.notesappmvvm.Dao.NotesDao;

@Database(entities = {Notes.class} , version = 1 )
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDao notesDao();

    public static NotesDatabase INSTANCE;

    //to check if istance is created ot not lets make a fn
    public static NotesDatabase getDatabaseInstance(Context context)
    {
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    NotesDatabase.class ,
                    "Notes_Database")
                    .allowMainThreadQueries()
                    .build();
        }
            return INSTANCE;
    }

}
