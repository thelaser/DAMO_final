package com.example.rauls_000.damo_rollcall;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thelaser on 3/01/18.
 */

public class Sessio {
    private String data;
    private List<Assistencia> assistencies;
    private int duracio;

    public Sessio (List<Alumne> alumnes_grup, String data_grup){
        data = data_grup;
        assistencies = new ArrayList<Assistencia>();
        for(Integer i = 0; i < alumnes_grup.size(); ++i){
            Assistencia assist = new Assistencia(alumnes_grup.get(i).getNom(), data);
            assistencies.add(assist);
        }
        duracio=7200; //2h
    }

    public List<Assistencia> getAssistencies() {
        return assistencies;
    }

    public String getDate() { return data; }

    public void setAssistencia(CharSequence nom_alumne) {
        Boolean done = false;
        Integer i = 0;
        while ((i < assistencies.size()) && !done) {
            if (nom_alumne == assistencies.get(i).getDNI()) {
                assistencies.get(i).changeTipus();
            }
            ++i;
        }
    }
}