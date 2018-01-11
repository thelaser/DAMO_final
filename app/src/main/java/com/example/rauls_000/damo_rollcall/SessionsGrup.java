package com.example.rauls_000.damo_rollcall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SessionsGrup extends Activity {

    private dbManager manager;
    private ListView llistaSessions;
    private ArrayAdapter<CharSequence> adapterSessions;
    private Button botocrear;
    private List<String> resultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions_grup);
        getActionBar().setTitle(getIntent().getStringExtra("idGrup"));
        inicialitzar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resultados = manager.getSessionsGrup(getIntent().getStringExtra("idGrup"));
        Log.d("RESULTADOS SIZE", String.valueOf(resultados.size()));
        adapterSessions.clear();
        adapterSessions.addAll(resultados);
    }

    private void inicialitzar() {
        llistaSessions = (ListView) findViewById(R.id.sessionsGrup);
        botocrear = (Button) findViewById(R.id.botocrear);
        manager = new dbManager(this);
        resultados = new ArrayList<>();
        resultados = manager.getSessionsGrup(getIntent().getStringExtra("idGrup"));
        adapterSessions = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, (ArrayList) resultados);
        llistaSessions.setAdapter(adapterSessions);
        llistaSessions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cridaModificarLlista(position);
            }
        });
        botocrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cridaPassarLlista();
            }
        });
    }

    public void cridaPassarLlista() {
        Intent intent = new Intent(this, PasarLlista.class);
        intent.putExtra("idGrup", getIntent().getStringExtra("idGrup"));
        startActivity(intent);
    }

    public void cridaModificarLlista(Integer position) {
        Intent intent = new Intent(this, EditSession.class);
        intent.putExtra("idGrup", getIntent().getStringExtra("idGrup"));
        intent.putExtra("idSessio", resultados.get(position));
        startActivity(intent);
    }
}
