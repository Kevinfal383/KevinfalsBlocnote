package com.example.kevinfalsblocnote;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AjouterNoteActivity extends AppCompatActivity {
    private EditText editTitre, editContenu;
    private Button btnSauvegarder;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_note);

        dbHelper = new DatabaseHelper(this);
        initViews();
    }

    private void initViews() {
        editTitre = findViewById(R.id.editTitre);
        editContenu = findViewById(R.id.editContenu);
        btnSauvegarder = findViewById(R.id.btnSauvegarder);

        btnSauvegarder.setOnClickListener(v -> sauvegarderNote());
    }

    private void sauvegarderNote() {
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

        long result = dbHelper.ajouterNote(titre, contenu);

        if (result != -1) {
            Toast.makeText(this, "Note ajoutée avec succès !", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show();
        }
    }
}