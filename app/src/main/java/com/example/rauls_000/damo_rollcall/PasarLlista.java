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

public class PasarLlista extends Activity {

    private EditText editData;
    private EditText editHora;
    private boolean editDataChanged;
    private boolean editHoraChanged;

    private ListView llistaalum;
    private Button saveButton;
    private ListAlumneAdapter adaptador;
    private Calendar calendar;
    private dbManager manager;

    private String date_to_db;
    private String hour_to_db;

    private List<Alumne> alumnesGrup;
    private List<Assistencia> assistencies;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pasar_llista);
        inicialitzar();
    }

    private void inicialitzar() {
        manager = new dbManager(this);
        assistencies = new ArrayList<>();
        alumnesGrup = new ArrayList<>();
        editData = (EditText) findViewById(R.id.editData);
        editHora = (EditText) findViewById(R.id.editHora);
        saveButton = (Button) findViewById(R.id.savebuttonPasarLlista);
        llistaalum = (ListView) findViewById(R.id.llistaAlum);
        editDataChanged = false;
        editHoraChanged = false;
        date_to_db = null;
        hour_to_db = null;
        calendar = Calendar.getInstance();
        alumnesGrup = manager.getAlumnes(getIntent().getStringExtra("idGrup"));
        adaptador = new ListAlumneAdapter(
                this, // Context
                (ArrayList) alumnesGrup // Font de dades
        );
        llistaalum.setAdapter(adaptador);
        llistaalum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                canviarEstatAlumne(position);
            }
        });
        editData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                obrirDialegData();
            }
        });
        editData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                editDataChanged = true;
                if(editHoraChanged == true) {
                    crearAssistencies();
                }
            }
        });
        editHora.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                editHoraChanged = true;
                if(editDataChanged == true) {
                    crearAssistencies();
                }
            }
        });
        editHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obrirDialegHora();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarSessio();
            }
        });
    }

    private void obrirDialegHora() {
        DialogFragment newFragment = new SelectTimeFragment();
        newFragment.show(getFragmentManager(), "TimePicker");
    }

    private void obrirDialegData() {
        DialogFragment newFragment = new SelectDateFragment();
        newFragment.show(getFragmentManager(), "DatePicker");
    }

    private void canviarEstatAlumne(int position) {
        if(assistencies.size() == 0) {
            Toast.makeText(getBaseContext(), "Siusplau introdueix una data i hora", Toast.LENGTH_SHORT).show();
        }
        else {
            Assistencia assistencia = assistencies.get(position);
            assistencia.changeTipus();
            adaptador.notifyDataSetChanged();
        }
    }

    private void guardarSessio() {
        if(editHoraChanged == true && editDataChanged == true) {
            String idSessio = date_to_db+hour_to_db;
            assistencies = adaptador.getAssistencies();
            manager.insertarSessio(idSessio);
            manager.insertarSessioGrup(date_to_db+hour_to_db, getIntent().getStringExtra("idGrup"));
            for(int i = 0; i < assistencies.size(); i++) {
                String idAlumne = (String) assistencies.get(i).getDNI();
                int tipusAssistencia = assistencies.get(i).getAssist();
                manager.insertarAssistencia(idSessio, idAlumne, tipusAssistencia);
            }
            onBackPressed();
        }
        else {
            Toast.makeText(this, "Introdueix valors correctes", Toast.LENGTH_SHORT).show();
        }
    }

    private void crearAssistencies() {
        List<Assistencia> assistencies_nou = new ArrayList<Assistencia>();
        String idSessio = date_to_db+hour_to_db;
        for(int i = 0; i < alumnesGrup.size(); i++) {
            String dniAlumne = (String) alumnesGrup.get(i).getDNI();
            Assistencia assistencia = new Assistencia(dniAlumne, idSessio);
            assistencies_nou.add(assistencia);
        }
        assistencies = assistencies_nou;
        adaptador.setAssistencies((ArrayList) assistencies);
        adaptador.notifyDataSetChanged();
    }

    // Methods cridats pels Custom Fragments
    // Cambien els TEXTVIEW y es d'aqui on ens guardem els valors de data y hora
    // Guardem la data i l'hora en el calendar
    public void setdate(int year, int month, int day) {
        editData.setText(month+"/"+day+"/"+year);
        date_to_db = (day+"-"+month+"-"+year+"-");
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

    }

    public void settime(int hora, int min){
        hour_to_db = (hora+"-"+min);
        editHora.setText(hora+":"+min);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.HOUR, hora);
    }
}