package com.example.rauls_000.damo_rollcall;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rauls_000 on 9/01/2018.
 */

public class AddGrup extends Activity {

    Button afegir_boto;
    EditText nomgrup;
    private ListView llistaAlumSelec;
    dbManager manager;

    private SelectAlumneAdapter adapter;
    private List<Alumne> alumnes;
    private ArrayList<Alumne> alumnes_seleccionats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grup);
        getActionBar().setTitle(getIntent().getStringExtra("idAssignatura") + ": nou grup");
        inicialitzar();
    }

    private void inicialitzar() {
        manager = new dbManager(this);
        carregarAlumnes();
        afegir_boto = (Button) findViewById(R.id.button_add_gr);
        nomgrup = (EditText) findViewById(R.id.editText);
        llistaAlumSelec = (ListView) findViewById(R.id.llistaAlumSelec);
        adapter = new SelectAlumneAdapter(
                this,
                (ArrayList) alumnes
        );
        llistaAlumSelec.setAdapter(adapter);
        llistaAlumSelec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                canviarSelected(position);
            }
        });

        afegir_boto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afegir_grup_bd();
            }
        });
    }

    private void carregarAlumnes() {
        alumnes = new ArrayList<Alumne>();
        alumnes_seleccionats = new ArrayList<Alumne>();
        alumnes = manager.getAlumes();
    }

    private void canviarSelected(int position){
        Alumne selected = alumnes.get(position);
        if (alumnes_seleccionats.contains(selected)){
            alumnes_seleccionats.remove(selected);
        } else {
            alumnes_seleccionats.add(selected);
        }
        Log.d("AddGrup", selected.getNom().toString());
        adapter.setAlumnes_seleccionats((ArrayList) alumnes_seleccionats);
        adapter.notifyDataSetChanged();

    }

    public void afegir_grup_bd() {
        String acronim = getIntent().getStringExtra("idAssignatura");
        if(nomgrup.getText().length() != 0) {
            alumnes_seleccionats = adapter.getSelectedAlumnes();  //Might not be needed <-----
            manager.insertarGrup(nomgrup.getText().toString());
            manager.insertarGrupAssignatura(nomgrup.getText().toString(), acronim);
            for (int i=0; i < alumnes_seleccionats.size(); i++){
                Alumne al = alumnes_seleccionats.get(i);
                Log.d("AddGruptoDB", al.getNom().toString());
                // Aixo no acaba de funcionar
                manager.insertarAlumneGrup(al.getDNI().toString(), nomgrup.getText().toString());
            }
            onBackPressed();
        }
        else {
            Toast.makeText(this, "Siusplau, introdueix el nom del grup", Toast.LENGTH_SHORT).show();
        }
    }

}
