package com.example.rauls_000.damo_rollcall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by rauls_000 on 07/01/2018.
 */

public class AddAssignatura extends Activity {

    private EditText editNom;
    private EditText editAcronim;
    private Button saveButton;
    private Assignatura assig;
    private dbManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nova_assignatura);
        inicialitzar();
    }

    private void inicialitzar() {
        manager = new dbManager(this);
        editNom = (EditText) findViewById(R.id.EditNomAssig);
        editAcronim = (EditText) findViewById(R.id.EditAcronimAssig);
        saveButton = (Button) findViewById(R.id.savebuttonNovaAssig);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarAssignatura();
            }
        });
    }

    private void guardarAssignatura() {
        CharSequence nom;
        CharSequence acronim;

        // Agafem el nom, si esta buït avisem
        nom = editNom.getText();
        if (nom.length() == 0) {
            Toast.makeText(this, "Siusplau, escrigui un nom", Toast.LENGTH_SHORT).show();
        } else {
            // Agafem el acronim, si esta buït avisem
            acronim = editAcronim.getText();
            if (acronim.length() == 0) {
                CharSequence text = "Siusplau, escrigui un acronim";
                Toast.makeText(getApplicationContext(), "Siusplau, escrigui un acronim", Toast.LENGTH_SHORT).show();
            } else {
                // Guardem la assignatura y tornem al menu principal
                manager.insertarAssignatura(String.valueOf(nom), String.valueOf(acronim));
                onBackPressed();
            }
        }
    }
}
