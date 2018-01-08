package com.example.rauls_000.damo_rollcall;

    import android.content.Context;
    import android.support.v4.content.ContextCompat;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.TextView;

    import java.util.ArrayList;
    import java.util.List;

/**
 * Created by rauls_000 on 03/01/2018.
 */

public class ListAlumneAdapter extends ArrayAdapter{

    private List<Alumne> alumnes;
    private List<Assistencia> assistencies;

    public ListAlumneAdapter(Context context, ArrayList<Alumne> alumnes)
    {
        super(context,0,alumnes);
        this.alumnes = alumnes;
        assistencies = null;
    }

    // Metode cridat per PassarLlista per poder saber quina assistencia del array estem treballant
    public Alumne getItem(int index) {
        return alumnes.get(index);
    }

    public void setAssistencies(ArrayList<Assistencia> assistencies) {
        this.assistencies = assistencies;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater;

        if(view == null) {
            holder = new ViewHolder();
            inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.row, null);
            holder.nom = (TextView) view.findViewById(R.id.alumnom);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Alumne alumne = alumnes.get(position);
        holder.nom.setText(alumne.getNom());

        if(assistencies != null) {
            int assistenciaAlumne = assistencies.get(position).getAssist();
            Log.d("Valor assistencia alumne "+alumne.getDNI(), String.valueOf(assistenciaAlumne));
            switch (assistenciaAlumne) {
                case 1:
                    view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.GREEN));
                    break;
                case 2:
                    view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.RED));
                    break;
                case 3:
                    view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.ORANGE));
                    break;
            }
        }

        return view;
    }

    public ArrayList<Assistencia> getAssistencies() {
        return (ArrayList) assistencies;
    }

    static class ViewHolder
    {
        TextView nom;
    }
}