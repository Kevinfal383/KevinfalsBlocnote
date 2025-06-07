package com.example.kevinfalsblocnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BlocNote.db";
    private static final int DATABASE_VERSION = 1;

    // Table utilisateur
    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NOM = "nom";
    private static final String COLUMN_USER_PRENOM = "prenom";
    private static final String COLUMN_USER_AGE = "age";

    // Table notes
    private static final String TABLE_NOTES = "notes";
    private static final String COLUMN_NOTE_ID = "id";
    private static final String COLUMN_NOTE_TITRE = "titre";
    private static final String COLUMN_NOTE_CONTENU = "contenu";
    private static final String COLUMN_NOTE_DATE = "date_creation";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_NOM + " TEXT NOT NULL,"
                + COLUMN_USER_PRENOM + " TEXT NOT NULL,"
                + COLUMN_USER_AGE + " INTEGER NOT NULL" + ")";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
                + COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOTE_TITRE + " TEXT NOT NULL,"
                + COLUMN_NOTE_CONTENU + " TEXT NOT NULL,"
                + COLUMN_NOTE_DATE + " TEXT NOT NULL" + ")";
        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public long ajouterUtilisateur(String nom, String prenom, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NOM, nom);
        values.put(COLUMN_USER_PRENOM, prenom);
        values.put(COLUMN_USER_AGE, age);

        long result = db.insert(TABLE_USER, null, values);
        db.close();
        return result;
    }

    public boolean utilisateurExiste() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_USER, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count > 0;
    }

    public String getNomUtilisateur() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_USER_PRENOM + " FROM " + TABLE_USER + " LIMIT 1", null);
        String nom = "";
        if (cursor.moveToFirst()) {
            nom = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return nom;
    }

    public long ajouterNote(String titre, String contenu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITRE, titre);
        values.put(COLUMN_NOTE_CONTENU, contenu);
        values.put(COLUMN_NOTE_DATE, java.text.DateFormat.getDateTimeInstance().format(new java.util.Date()));

        long result = db.insert(TABLE_NOTES, null, values);
        db.close();
        return result;
    }

    public List<Note> getToutesLesNotes() {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTES + " ORDER BY " + COLUMN_NOTE_ID + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NOTE_ID)));
                note.setTitre(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE_TITRE)));
                note.setContenu(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE_CONTENU)));
                note.setDateCreation(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE_DATE)));
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    public Note getNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTES, null, COLUMN_NOTE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        Note note = null;
        if (cursor.moveToFirst()) {
            note = new Note();
            note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NOTE_ID)));
            note.setTitre(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE_TITRE)));
            note.setContenu(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE_CONTENU)));
            note.setDateCreation(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE_DATE)));
        }
        cursor.close();
        db.close();
        return note;
    }

    public int modifierNote(int id, String titre, String contenu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITRE, titre);
        values.put(COLUMN_NOTE_CONTENU, contenu);

        int result = db.update(TABLE_NOTES, values, COLUMN_NOTE_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
        return result;
    }

    public void supprimerNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, COLUMN_NOTE_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}