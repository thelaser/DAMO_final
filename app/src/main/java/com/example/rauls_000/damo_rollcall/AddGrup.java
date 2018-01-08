package com.example.rauls_000.damo_rollcall;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddGrup extends Activity {

    Button afegir_boto;
    EditText nomgrup;
    dbManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grup);
        inicialitzar();
    }

    public void inicialitzar() {
        afegir_boto = (Button) findViewById(R.id.button_add_gr);
        nomgrup = (EditText) findViewById(R.id.editText);
        manager = new dbManager(this);
        afegir_boto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afegir_grup_bd();
            }
        });
    }

    public void afegir_grup_bd() {
        String acronim = getIntent().getStringExtra("idAssignatura");
        if(nomgrup.getText().length() != 0) {
            manager.insertarGrup(nomgrup.getText().toString());
            manager.insertarGrupAssignatura(nomgrup.getText().toString(), acronim);
            onBackPressed();
        }
        else {
            Toast.makeText(this, "Siusplau, introdueix el nom del grup", Toast.LENGTH_SHORT).show();
        }
    }

}
