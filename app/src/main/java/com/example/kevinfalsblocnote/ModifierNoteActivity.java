package com.example.kevinfalsblocnote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModifierNoteActivity extends AppCompatActivity {
    private EditText editTitre, editContenu;
    private Button btnModifier, btnSupprimer;
    private DatabaseHelper dbHelper;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_note);

        dbHelper = new DatabaseHelper(this);
        noteId = getIntent().getIntExtra("note_id", -1);

        if (noteId == -1) {
            Toast.makeText(this, "Erreur: Note introuvable", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        chargerNote();
    }

    private void initViews() {
        editTitre = findViewById(R.id.editTitre);
        editContenu = findViewById(R.id.editContenu);
        btnModifier = findViewById(R.id.btnModifier);
        btnSupprimer = findViewById(R.id.btnSupprimer);

        btnModifier.setOnClickListener(v -> modifierNote());
        btnSupprimer.setOnClickListener(v -> confirmerSuppression());
    }

    private void chargerNote() {
        Note note = dbHelper.getNote(noteId);
        if (note != null) {
            editTitre.setText(note.getTitre());
            editContenu.setText(note.getContenu());
        }
    }

    private void modifierNote() {
        String titre = editTitre.getText().toString().trim();
        String contenu = editContenu.getText().toString().trim();

        if (TextUtils.isEmpty(titre)) {
            editTitre.setError("Titre requis");
            editTitre.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(contenu)) {
            editContenu.setError("Contenu requis");
            editContenu.requestFocus();
            return;
        }

        int result = dbHelper.modifierNote(noteId, titre, contenu);

        if (result > 0) {
            Toast.makeText(this, "Note modifiée avec succès !", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erreur lors de la modification", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmerSuppression() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmer la suppression")
                .setMessage("Êtes-vous sûr de vouloir supprimer cette note ?")
                .setPositiveButton("Oui", (dialog, which) -> {
                    dbHelper.supprimerNote(noteId);
                    Toast.makeText(this, "Note supprimée", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Non", null)
                .show();
    }
}