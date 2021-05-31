package com.example.notesappmvvm.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesappmvvm.Activity.UpdateNote;
import com.example.notesappmvvm.MainActivity;
import com.example.notesappmvvm.Model.Notes;
import com.example.notesappmvvm.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.notesViewHolder> {

    MainActivity mainActivity;
    List<Notes> notes;
    List<Notes> allNotesItem;

    public NotesAdapter(MainActivity mainActivity, List<Notes> notes) {
        this.mainActivity = mainActivity;
        this.notes = notes;
        allNotesItem = new ArrayList<>(notes);


    }

    public void searchNotes(List<Notes> filteredName){

        this.notes = filteredName;
        notifyDataSetChanged();

    }


    @Override
    public notesViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        return new notesViewHolder(LayoutInflater.from(mainActivity).inflate(R.layout.item_notes , parent , false));
    }

    @Override
    public void onBindViewHolder( NotesAdapter.notesViewHolder holder, int position) {
        Notes note = notes.get(position);

        /**
        if (note.notesPriority != null && note.notesPriority.equals("1")){
            holder.notesPriority.setBackgroundResource(R.drawable.green_shape);
        } else if (note.notesPriority != null && note.notesPriority.equals("2")){
            holder.notesPriority.setBackgroundResource(R.drawable.yello_shape);
        } else if (note.notesPriority != null && note.notesPriority.equals("3")){
            holder.notesPriority.setBackgroundResource(R.drawable.red_shape);
        }
         **/

        switch (note.notesPriority){
            case "1":
                holder.notesPriority.setBackgroundResource(R.drawable.green_shape);
                break;
            case "2":
                holder.notesPriority.setBackgroundResource(R.drawable.yello_shape);
                break;
            case "3":
                holder.notesPriority.setBackgroundResource(R.drawable.red_shape);
                break;
        }



        holder.title.setText(note.notesTitle);
        holder.subtitle.setText(note.notesSubtitle);
        holder.date.setText(note.notesDate);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(mainActivity , UpdateNote.class);
                intent.putExtra("id" , note.id);
                intent.putExtra("title" , note.notesTitle);
                intent.putExtra("subtitle" , note.notesSubtitle);
                intent.putExtra("priority" , note.notesPriority);
                intent.putExtra("notes" , note.notes);
                mainActivity.startActivity(intent);

            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class notesViewHolder extends RecyclerView.ViewHolder {

        TextView title, subtitle , date;
        View notesPriority;


        public notesViewHolder( View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.noteTitle);
            subtitle = itemView.findViewById(R.id.noteSubtitle);
            date = itemView.findViewById(R.id.noteDate);
            notesPriority = itemView.findViewById(R.id.notesPriority);
        }
    }
    
}
