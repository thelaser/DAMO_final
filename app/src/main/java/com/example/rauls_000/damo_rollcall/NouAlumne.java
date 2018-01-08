package com.example.rauls_000.damo_rollcall;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NouAlumne extends Activity {

    private EditText dni;
    private EditText nom;
    private Button guardarAlumne;
    private dbManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nou_alumne);
        inicialitzar();
    }

    private void inicialitzar() {
        dni = (EditText) findViewById(R.id.dni);
        nom = (EditText) findViewById(R.id.nom);
        guardarAlumne =  (Button) findViewById(R.id.guardarAlumne);
        guardarAlumne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarAlumne(dni.getText().toString(), nom.getText().toString());
            }
        });
        manager = new dbManager(this);
    }

    public void guardarAlumne(String dni, String nom) {
        if(dni.length() != 0 && nom.length() != 0) {
            manager.insertarAlumneGrup(dni, nom);
            onBackPressed();
        }
        else {
            Toast.makeText(this, "Introdueix valors correctes", Toast.LENGTH_SHORT).show();
        }
    }
}
