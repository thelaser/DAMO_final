package com.example.rauls_000.damo_rollcall;

/**
 * Created by thelaser on 28/12/17.
 */

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Grup {

    private CharSequence nom_grup="sense_nom";
    private List<Alumne> alumnes;
    private List<Sessio> sessions;

    public Grup(CharSequence nom){
        nom_grup = nom;
        alumnes = new ArrayList<Alumne>();
        sessions = new ArrayList<Sessio>();
    }

    public void add_alumne(Alumne alumne){
        Log.d("alumne", String.valueOf(alumne.getNom()));
        alumnes.add(alumne);
    }

    public void remove_alumne(CharSequence nom) {
        Boolean found = false;
        Integer i = 0;
        while(i < alumnes.size() && !found){
            if (alumnes.get(i).getNom() == nom) {
                alumnes.remove(i);
                found = true;
            }
            ++i;
        }
    }



    public ArrayList<Sessio> getSessions() { return (ArrayList)sessions; }
    public CharSequence view_nom_grup() {
        return nom_grup;
    }
    public int get_classes_tot() {return sessions.size();}


}
