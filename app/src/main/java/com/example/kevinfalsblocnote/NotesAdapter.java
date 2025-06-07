package com.example.kevinfalsblocnote;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    private List<Note> notes;
    private Context context;

    public NotesAdapter(List<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.textTitre.setText(note.getTitre());
        holder.textContenu.setText(note.getContenu());
        holder.textDate.setText(note.getDateCreation());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ModifierNoteActivity.class);
            intent.putExtra("note_id", note.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void updateNotes(List<Note> newNotes) {
        this.notes = newNotes;
        notifyDataSetChanged();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView textTitre, textContenu, textDate;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitre = itemView.findViewById(R.id.textTitre);
            textContenu = itemView.findViewById(R.id.textContenu);
            textDate = itemView.findViewById(R.id.textDate);
        }
    }
}