package com.example.kevinfalsblocnote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private TextView textBienvenue;
    private TextView textAucuneNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DatabaseHelper(this);


        if (!dbHelper.utilisateurExiste()) {
            Intent intent = new Intent(this, InscriptionActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        initViews();
        setupRecyclerView();
        loadNotes();
    }

    private void initViews() {
        textBienvenue = findViewById(R.id.textBienvenue);
        textAucuneNote = findViewById(R.id.textAucuneNote);
        recyclerView = findViewById(R.id.recyclerViewNotes);
        FloatingActionButton fabAjouter = findViewById(R.id.fabAjouter);


        String nom = dbHelper.getNomUtilisateur();
        textBienvenue.setText("Bienvenue " + nom + " !");

        fabAjouter.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AjouterNoteActivity.class);
            startActivity(intent);
        });
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadNotes() {
        List<Note> notes = dbHelper.getToutesLesNotes();

        if (notes.isEmpty()) {
            textAucuneNote.setVisibility(TextView.VISIBLE);
            recyclerView.setVisibility(RecyclerView.GONE);
        } else {
            textAucuneNote.setVisibility(TextView.GONE);
            recyclerView.setVisibility(RecyclerView.VISIBLE);

            if (adapter == null) {
                adapter = new NotesAdapter(notes, this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.updateNotes(notes);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }
}