package com.example.rauls_000.damo_rollcall;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class dbManager {

    dbHelper helper;

    public dbManager(Context context) {
        helper = new dbHelper(context);
    }

    public void insertarAlumne(String nom, String dni) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("dni", dni);
        values.put("nom", nom);
        database.insert("alumne", null, values);
    }

    public void insertarAssignatura(String nom, String acronim) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("acronim", acronim);
        database.insert("assignatura", null, values);
    }

    public void insertarAssistencia(String idSessio, String idAlumne, int tipusAssistencia) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idSessio", idSessio);
        values.put("idAlumne", idAlumne);
        values.put("tipusAssistencia", tipusAssistencia);
        database.insert("assistencia", null, values);
    }

    public void insertarGrup(String idGrup) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", idGrup);
        database.insert("grup", null, values);
    }

    public void insertarAlumneGrup(String dni, String idGrup) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idGrup", idGrup);
        values.put("idAlumne", dni);
        database.insert("grup_alumne", null, values);
    }

    public void insertarGrupAssignatura(String idGrup, String acronimAssignatura) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idGrup", idGrup);
        values.put("idAssignatura", acronimAssignatura);
        database.insert("grup_assignatura", null, values);
    }

    public void insertarSessioGrup(String idSessio, String idGrup) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idGrup", idGrup);
        values.put("idSessio", idSessio);
        database.insert("grup_sessio", null, values);
    }

    public void insertarSessio(String data) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("data", data);
        database.insert("sessio", null, values);
    }

    public List<String> getGrupsAssignatura(String acronim) {
        List<String> labels = new ArrayList<>();
        SQLiteDatabase database = helper.getReadableDatabase();
        String[] projection = new String[]{"idGrup"};
        Cursor cursor = database.query("grup_assignatura", projection, "idAssignatura = ?", new String[]{acronim}, null, null, null);
        while (cursor.moveToNext()) {
            labels.add(cursor.getString(0));
        }
        cursor.close();
        return labels;
    }


    public int getAssignaturaSize() {
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor  cursor = database.rawQuery("SELECT * FROM assignatura", null);
        int numAssignatures = cursor.getCount();
        cursor.close();
        return numAssignatures;
    }

    public List<String> getAlumesGrup(String idGrup) {
        List<String> labels = new ArrayList<>();
        SQLiteDatabase database = helper.getReadableDatabase();
        String[] projection = new String[]{"idAlumne"};
        Cursor cursor = database.query("grup_alumne", projection, "idGrup = ?", new String[]{idGrup}, null, null, null);
        while (cursor.moveToNext()) {
            labels.add(cursor.getString(0));
        }
        return labels;
    }

    public List<String> getSessionsGrup(String idGrup) {
        List<String> labels = new ArrayList<>();
        SQLiteDatabase database = helper.getReadableDatabase();
        String[] projection = new String[]{"idSessio"};
        Cursor cursor = database.query("grup_sessio", projection, "idGrup = ?", new String[]{idGrup}, null, null, null);
        while (cursor.moveToNext()) {
            labels.add(cursor.getString(0));
        }
        return labels;
    }

    public CharSequence getAcronimAssignatura(int id) {
        SQLiteDatabase database = helper.getReadableDatabase();
        String acronimAssignatura = null;
        Cursor cursor = database.query("assignatura", new String[]{"acronim"}, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if(cursor.moveToFirst()) {
            acronimAssignatura = cursor.getString(0);
        }
        return acronimAssignatura;
    }

    public List<Alumne> getAlumnes(String idGrup) {
        Alumne alumne;
        List<Alumne> alumnes = new ArrayList<>();
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT alumne.dni, alumne.nom FROM alumne, grup_alumne WHERE alumne.dni = grup_alumne.idAlumne AND grup_alumne.idGrup = "+"'"+idGrup+"'", null);
        while(cursor.moveToNext()) {
            String dni, nom;
            dni = cursor.getString(0);
            nom = cursor.getString(1);
            alumne = new Alumne(dni, nom);
            alumnes.add(alumne);
        }
        Log.d("RESULTAT ALUMNES OBJECTE GRUP", String.valueOf(alumnes.size()));
        return alumnes;
    }

    public String getAssistenciaAlumne(String dniAlumne, String idSessio) {
        String assistencia = "-1";
        List<String>assistencies = new ArrayList<>();
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query("assistencia", new String[]{"tipusAssistencia"}, "idAlumne = ? AND idSessio = ?", new String[]{dniAlumne, idSessio}, null, null, null);
        while(cursor.moveToNext()) {
            assistencia = cursor.getString(0);
            assistencies.add(assistencia);
        }
        Log.d("ASISTENCIES", assistencies.toString());
        return assistencia;
    }

    public void updateAssistencia(String idSessio, String idAlumne, int tipusAssistencia) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tipusAssistencia", tipusAssistencia);
        database.update("assistencia", values, "idSessio = ? AND idAlumne = ?", new String[]{idSessio, idAlumne});
    }
}
