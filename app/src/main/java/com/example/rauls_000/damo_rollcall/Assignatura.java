package com.example.rauls_000.damo_rollcall;

/**
 * Created by thelaser on 28/12/17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.rauls_000.damo_rollcall.Grup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Assignatura {

    private CharSequence nom_assig;
    private CharSequence acronim;
    private ArrayList<Grup> grups;

    public Assignatura(CharSequence nom, CharSequence acron) {
        nom_assig = nom;
        acronim = acron;
        grups = new ArrayList<Grup>();
    }

    public Assignatura(Assignatura assignatura) {
        nom_assig = assignatura.getNom();
        acronim = assignatura.getAcronim();
        grups = assignatura.getGrups();
    }

    public ArrayList<Grup> getGrups() {
        return grups;
    }

    public void add_grup(CharSequence grup_nom) {
        Grup grup = new Grup(grup_nom);
        grups.add(grup);
    }

    public void add_alumne(CharSequence identif_dni, CharSequence nomAlumne, CharSequence nom_grup){
        Boolean done = false;
        Integer i = 0;
        while ((i < grups.size()) && !done) {
            if (nom_grup == grups.get(i).view_nom_grup()) {
                Alumne alumne = new Alumne(identif_dni, nomAlumne);
                grups.get(i).add_alumne(alumne);
            }
            ++i;
        }
    }

    public void mod_assist(CharSequence nom_alumne, CharSequence nom_grup, Calendar date) {
        Boolean done = false;
        Integer i = 0;
        while ((i < grups.size()) && !done) {
            if (nom_grup == grups.get(i).view_nom_grup()) {

            }
            ++i;
        }
    }

    public void add_alumne_list(CharSequence nom_grup, ArrayList<Alumne> alumnes_nous) {
        Boolean done = false;
        Integer i = 0;
        while ((i < grups.size()) && !done) {
            if (nom_grup == grups.get(i).view_nom_grup()) {
                for (Integer j = 0; j < alumnes_nous.size(); ++j){
                    grups.get(i).add_alumne(alumnes_nous.get(j));
                }
            }
            ++i;
        }

    }

    public void add_grup_list(ArrayList<Grup> grups_nous) {
        for (Integer i = 0; i < grups_nous.size(); ++i){
            grups.add(grups_nous.get(i));
        }
    }

    public void delete_grup(CharSequence nom_grup) {
        Boolean done = false;
        Integer i = 0;
        while ((i < grups.size()) && !done) {
            if (nom_grup == grups.get(i).view_nom_grup()) {
                grups.remove(i);
            }
            ++i;
        }
    }

    public static Grup get_grup_nom (ArrayList<Grup> grups, CharSequence nom_grup) {
        Boolean done = false;
        Integer i = 0;
        Grup search_target = null;
        while ((i < grups.size()) && !done) {
            if (nom_grup == grups.get(i).view_nom_grup()) {
                search_target = grups.get(i);
                done = true;
            }
            ++i;
        }
        return search_target;
    }

    public CharSequence getNom() {
        return nom_assig;
    }
    public CharSequence getAcronim() { return acronim; }
    public Grup get_grup(int position) {
        return grups.get(position);
    }
    public int quantity_grups(){
        return grups.size();
    }


    public ArrayList<CharSequence> getNomGrups() {
        ArrayList<CharSequence> noms = new ArrayList<>();
        for(int i = 0; i < grups.size(); i++) {
            noms.add(grups.get(i).view_nom_grup());
        }
        return noms;
    }

    public int sizeGrups() {
        return grups.size();
    }

}
