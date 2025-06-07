package com.example.kevinfalsblocnote;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InscriptionActivity extends AppCompatActivity {
    private EditText editNom, editPrenom, editAge;
    private Button btnInscrire;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        dbHelper = new DatabaseHelper(this);
        initViews();
    }

    private void initViews() {
        editNom = findViewById(R.id.editNom);
        editPrenom = findViewById(R.id.editPrenom);
        editAge = findViewById(R.id.editAge);
        btnInscrire = findViewById(R.id.btnInscrire);

        btnInscrire.setOnClickListener(v -> inscrireUtilisateur());
    }

    private void inscrireUtilisateur() {
        String nom = editNom.getText().toString().trim();
        String prenom = editPrenom.getText().toString().trim();
        String ageStr = editAge.getText().toString().trim();

        if (TextUtils.isEmpty(nom)) {
            editNom.setError("Nom requis");
            editNom.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(prenom)) {
            editPrenom.setError("Prénom requis");
            editPrenom.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(ageStr)) {
            editAge.setError("Âge requis");
            editAge.requestFocus();
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
            if (age <= 0 || age > 120) {
                editAge.setError("Âge invalide");
                editAge.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            editAge.setError("Âge invalide");
            editAge.requestFocus();
            return;
        }

        long result = dbHelper.ajouterUtilisateur(nom, prenom, age);

        if (result != -1) {
            Toast.makeText(this, "Inscription réussie !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "L'inscription est obligatoire", Toast.LENGTH_SHORT).show();
    }
}