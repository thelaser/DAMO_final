package com.example.rauls_000.damo_rollcall;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rauls_000 on 03/01/2018.
 */

public class SelectAlumneAdapter extends ArrayAdapter{

    private List<Alumne> alumnes;
    private List<Alumne> alumnes_seleccionats;

    public SelectAlumneAdapter(Context context, ArrayList<Alumne> alumnes)
    {
        super(context,0, alumnes);
        this.alumnes = alumnes;
        alumnes_seleccionats = new ArrayList<Alumne>();
    }

    // Metode cridat per AddGrup per poder saber sobre quin alumne del array estem treballant
    public Alumne getItem(int index) {
        return alumnes.get(index);
    }

    // Metode cridat per AddGrup per a pasar al adapter la llista de alumnes seleccionats
    public void setAlumnes_seleccionats(ArrayList<Alumne> alumnes) {
        this.alumnes_seleccionats = alumnes;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater;

        if(view == null) {
            holder = new ViewHolder();
            inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.row_alumn, null);
            holder.nom = (TextView) view.findViewById(R.id.list_noms);
            holder.box = (CheckBox) view.findViewById(R.id.list_checkbox);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Alumne alumne = alumnes.get(position);
        holder.nom.setText(alumne.getNom());
        if(alumnes_seleccionats.contains(alumne)){
            holder.box.setChecked(true);
        }
        else {
            holder.box.setChecked(false);
        }

        return view;
    }

    public ArrayList<Alumne> getSelectedAlumnes() {
        return (ArrayList) alumnes_seleccionats;
    }

    static class ViewHolder
    {
        TextView nom;
        CheckBox box;
    }
}