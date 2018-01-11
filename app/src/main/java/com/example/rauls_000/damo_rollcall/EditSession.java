package com.example.rauls_000.damo_rollcall;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by rauls_000 on 03/01/2018.
 */

public class EditSession extends Activity {

    private EditText editData;
    private EditText editHora;

    private ListView llistaalum;
    private Button saveButton;
    private ListAlumneAdapter adaptador;
    private dbManager manager;

    private List<Alumne> alumnesGrup;
    private List<Assistencia> assistencies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pasar_llista);
        getActionBar().setTitle(getIntent().getStringExtra("idGrup") + ": " + getIntent().getStringExtra("idSessio"));
        inicialitzar();
    }

    private void inicialitzar() {
        manager = new dbManager(this);
        assistencies = new ArrayList<>();
        alumnesGrup = new ArrayList<>();
        alumnesGrup = manager.getAlumnes(getIntent().getStringExtra("idGrup"));
        editData = (EditText) findViewById(R.id.editData);
        editHora = (EditText) findViewById(R.id.editHora);
        saveButton = (Button) findViewById(R.id.savebuttonPasarLlista);
        llistaalum = (ListView) findViewById(R.id.llistaAlum);
        adaptador = new ListAlumneAdapter(
                this, // Context
                (ArrayList) alumnesGrup // Font de dades
        );
        llistaalum.setAdapter(adaptador);
        llistaalum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(assistencies.size() == 0) {
                    Toast.makeText(getBaseContext(), "Siusplau introdueix una data i hora", Toast.LENGTH_SHORT).show();
                }
                else {
                    Assistencia assistencia = assistencies.get(position);
                    assistencia.changeTipus();
                    adaptador.notifyDataSetChanged();
                }

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarSessio();

            }
        });
        String[] recovered = getIntent().getStringExtra("idSessio").split("-");
        String dataText = recovered[1]+"/"+recovered[0]+"/"+recovered[2];
        String horaText = recovered[3]+":"+recovered[4];
        editData.setText(dataText);
        editHora.setText(horaText);
        carregarAssistencies();
    }

    private void guardarSessio() {
        String idSessio = getIntent().getStringExtra("idSessio");
        assistencies = adaptador.getAssistencies();
        for(int i = 0; i < assistencies.size(); i++) {
            String idAlumne = (String) assistencies.get(i).getDNI();
            int tipusAssistencia = assistencies.get(i).getAssist();
            manager.updateAssistencia(idSessio, idAlumne, tipusAssistencia);
        }
        onBackPressed();
    }

    public void carregarAssistencies() {
        for(int i = 0; i < alumnesGrup.size(); ++i) {
            Assistencia carrega_assist = new Assistencia((String) (alumnesGrup.get(i).getDNI()), getIntent().getStringExtra("idSessio"));
            carrega_assist.setTipus(Integer.valueOf(manager.getAssistenciaAlumne((String) (alumnesGrup.get(i).getDNI()), getIntent().getStringExtra("idSessio"))));
            assistencies.add(carrega_assist);
        }
        adaptador.setAssistencies((ArrayList) assistencies);
        adaptador.notifyDataSetChanged();
    }
}