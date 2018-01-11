package com.example.rauls_000.damo_rollcall;


import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

// we create a FragmentActivity that will contain fragments in it
public class SlideAssignatures extends FragmentActivity {

    /*
     *   This is the widget which contains the fragments.
     *   It's a layout manager that allows the user to flip left and right through pages of data.
     *
     */
    ViewPager mViewPager;
    private dbManager manager;

    // This is the adapter that will manage the fragments inside the activity's ViewPager
    SlidesPagerAdapter mSlidesPagerAdapter;

    // Here we add the subjects we are going to use in the list app
    ArrayList<Assignatura> assignatures;
    FragmentManager frag_manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //this.deleteDatabase("dades.db");
        manager = new dbManager(this);


        assignatures = new ArrayList<Assignatura>();

        // ################# CARREGAR DADES DES DE XML ###########################


        // ESTOS DATOS SE CARGARIAN DANDO A UN BOTON EN LA PANTALLA PRINCIPAL



        // ########################################################################

        // we create a fragment manager to handle fragment transactions
         frag_manager = getSupportFragmentManager();

        /* Create the adapter that will return a fragment for each of the three
           primary sections of the app. */
        // We pass to it the assignatures so it can handle them, although it's probably not the best approach.
        mSlidesPagerAdapter = new SlidesPagerAdapter(frag_manager);

        // Set up the ViewPager with the slides adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSlidesPagerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSlidesPagerAdapter.notifyDataSetChanged();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_assig:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent1 = new Intent(this, AddAssignatura.class);
                startActivity(intent1);
                return true;
            case R.id.add_alumne:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent2 = new Intent(this, NouAlumne.class);
                startActivity(intent2);
                return true;
            case R.id.carregarDades:
                // User chose the "Settings" item, show the app settings UI...
                carregarDades();
                mSlidesPagerAdapter.notifyDataSetChanged();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }



    /*  FragmentPagerAdapter:
     *  This version of the pager is best for use when there are a handful of typically
     *  more static fragments to be paged through, such as a set of tabs.
     *  The fragment of each page the user visits will be kept in memory,
     *  though its view hierarchy may be destroyed when not visible.
     *
     *  Subclasses only need to implement getItem(int) and getCount() to have a working adapter.
     */
    public class SlidesPagerAdapter extends FragmentPagerAdapter {

        public SlidesPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PopulatedSlideFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            position++;
            Fragment fragment;
            Bundle args;

            fragment = new PopulatedSlideFragment();
            args = new Bundle();

            args.putInt("assigval", position);

            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            // We add 1 to the total size because we will have the populated screens
            // plus the creation button screen.
            return manager.getAssignaturaSize();
        }



        @Override
        public CharSequence getPageTitle(int position) {

            position++;

            if (manager.getAssignaturaSize() > 0 && position <= manager.getAssignaturaSize()) {
                return manager.getAcronimAssignatura(position);
            }
            else {
                return null;
            }

        }
    }

    public static class PopulatedSlideFragment extends Fragment {

        Context context;
        ListView grup_list;
        Button add_grup;
        dbManager manager;
        Bundle args;
        View rootView;
        Assignatura assignatura;
        ArrayAdapter<CharSequence> grups_adapter;


        public PopulatedSlideFragment() {

        }

        @Override
        public void onResume() {
            super.onResume();
            grups_adapter.clear();
            grups_adapter.addAll(manager.getGrupsAssignatura((String) manager.getAcronimAssignatura(args.getInt("assigval"))));
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.fragment_populated, container,  false);

            args = getArguments();

            manager = new dbManager(getActivity());


            // ArrayAdapter needs as parameters the activity context, a layout xml with a single
            // TextView in it which will be used to portray the CharSequences, and the arraylist itself
            // that will be used.
            grups_adapter = new ArrayAdapter<CharSequence>(getActivity(), R.layout.grups_list, (ArrayList) manager.getGrupsAssignatura((String) manager.getAcronimAssignatura(args.getInt("assigval"))));

            grup_list = (ListView) rootView.findViewById(R.id.grup_list);

            grup_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CharSequence grup = (CharSequence) grup_list.getItemAtPosition(position);
                    mostrarSessions(grup);
                }
            });

            grup_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    return false;
                }
            });

            grup_list.setAdapter(grups_adapter);

            add_grup = (Button) rootView.findViewById(R.id.add_grup_pop);

            add_grup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cridaCrearGrup(String.valueOf(manager.getAcronimAssignatura(args.getInt("assigval"))));
                }
            });




            return rootView;
        }

        private void obreCreacioGrup() {
            Intent intent = new Intent(getActivity(), NouAlumne.class);
            startActivity(intent);
        }

        private void cridaCrearGrup(String idAssignatura) {
            Intent intent2 = new Intent(getActivity(), AddGrup.class);
            intent2.putExtra("idAssignatura", (String) idAssignatura);
            startActivity(intent2);
        }




        public void mostrarSessions(CharSequence idGrup) {
            Intent intent = new Intent(getActivity(), SessionsGrup.class);
            intent.putExtra("idGrup", (String) idGrup);
            startActivity(intent);
        }

    }


    private String readText(XmlResourceParser parser) throws IOException, XmlPullParserException {
        String resultat = "";
        if (parser.next () == XmlPullParser.TEXT) {
            resultat = parser.getText();
            parser.nextTag();
        }
        return resultat;
    }


    private void carregarGrupsAssignatura() {
        XmlResourceParser parser = getResources().getXml(R.xml.grup_assignatura);
        dbManager manager = new dbManager(this);
        try {
            while(parser.next () != XmlPullParser.END_TAG) {
                if(parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String tipus = parser.getName();
                if(tipus.equals("Assignatura")) {
                    String nomAssignatura = readText(parser);
                    parser.nextTag();
                    tipus = parser.getName();
                    if(tipus.equals("LlistatGrups")) {
                        String idGrup = null;
                        while(parser.next () != XmlPullParser.END_TAG) {
                            if(parser.getEventType() != XmlPullParser.START_TAG) {
                                continue;
                            }
                            tipus = parser.getName();
                            if(tipus.equals("Grup")) {
                                idGrup = readText(parser);
                                manager.insertarGrupAssignatura(idGrup, nomAssignatura);
                            }
                        }
                    }
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarAlumnesGrups() {
        // Abrir fitxer de ruta?
        XmlResourceParser parser = getResources().getXml(R.xml.grup_alumnes);
        dbManager manager = new dbManager(this);
        try {
            while(parser.next () != XmlPullParser.END_TAG) {
                if(parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String tipus = parser.getName();
                if(tipus.equals("Grup")) {
                    String idGrup = readText(parser);
                    Log.d("ID GRUP", idGrup);
                    parser.nextTag();
                    tipus = parser.getName();
                    Log.d("TIPUS", tipus);
                    if(tipus.equals("LlistatAlumnes")) {
                        Log.d("Llistat alumnes", "");
                        String dniAlumne = null;
                        while(parser.next () != XmlPullParser.END_TAG) {
                            if(parser.getEventType() != XmlPullParser.START_TAG) {
                                continue;
                            }
                            tipus = parser.getName();
                            if(tipus.equals("Alumne")) {
                                dniAlumne = readText(parser);
                                Log.d("DNI ALUMNEEEEEEEEEEEEEEEEEEEEEEE", dniAlumne);
                                manager.insertarAlumneGrup(dniAlumne, idGrup);
                            }
                        }
                    }
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarGrups() {
        XmlResourceParser parser = getResources().getXml(R.xml.grups);
        dbManager manager = new dbManager(this);
        try {
            while(parser.next () != XmlPullParser.END_TAG) {
                if(parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String tipus = parser.getName();
                String idGrup = null;
                if(tipus.equals("Grup")) {
                    idGrup = readText(parser);
                    manager.insertarGrup(idGrup);
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarAssignatures() {
        XmlResourceParser parser = getResources().getXml(R.xml.assignatures);
        dbManager manager = new dbManager(this);
        try {
            while(parser.next () != XmlPullParser.END_TAG) {
                if(parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String tipus = parser.getName();
                if(tipus.equals("Assignatura")) {
                    String nom = null, acronim = null;
                    while(parser.next () != XmlPullParser.END_TAG) {
                        if(parser.getEventType() != XmlPullParser.START_TAG) {
                            continue;
                        }
                        tipus = parser.getName();
                        if(tipus.equals("nom")) {
                            nom = readText(parser);
                        }
                        else if(tipus.equals("acronim")) {
                            acronim = readText(parser);
                        }
                    }
                    manager.insertarAssignatura(nom, acronim);
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarAlumnes() {
        XmlResourceParser parser = getResources().getXml(R.xml.alumnes);
        try {
            while(parser.next () != XmlPullParser.END_TAG) {
                if(parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String tipus = parser.getName();
                if(tipus.equals("Alumne")) {
                    String dni = null, nom = null;
                    while(parser.next () != XmlPullParser.END_TAG) {
                        if(parser.getEventType() != XmlPullParser.START_TAG) {
                            continue;
                        }
                        tipus = parser.getName();
                        if(tipus.equals("dni")) {
                            dni = readText(parser);
                        }
                        else if(tipus.equals("nom")) {
                            nom = readText(parser);
                        }
                    }
                    Log.d("DNI", dni);
                    Log.d("NOM", nom);
                    manager.insertarAlumne(nom, dni);
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarSessionsGrup() {
        // Abrir fitxer de ruta?
        XmlResourceParser parser = getResources().getXml(R.xml.grup_sessio);
        dbManager manager = new dbManager(this);
        try {
            while(parser.next () != XmlPullParser.END_TAG) {
                if(parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String tipus = parser.getName();
                if(tipus.equals("Grup")) {
                    String idGrup = readText(parser);
                    Log.d("ID GRUP", idGrup);
                    parser.nextTag();
                    tipus = parser.getName();
                    Log.d("TIPUS", tipus);
                    if(tipus.equals("LlistatSessions")) {
                        Log.d("Llistat sessions", "");
                        String data = null;
                        while(parser.next () != XmlPullParser.END_TAG) {
                            if(parser.getEventType() != XmlPullParser.START_TAG) {
                                continue;
                            }
                            tipus = parser.getName();
                            if(tipus.equals("Sessio")) {
                                data = readText(parser);
                                Log.d("DNI ALUMNEEEEEEEEEEEEEEEEEEEEEEE", data);
                                manager.insertarSessioGrup(data, idGrup);
                            }
                        }
                    }
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarAssistencies() {
        XmlResourceParser parser = getResources().getXml(R.xml.assistencies);
        try {
            while(parser.next () != XmlPullParser.END_TAG) {
                if(parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String tipus = parser.getName();
                if(tipus.equals("Assistencia")) {
                    String dni = null, idsessio = null;
                    while(parser.next () != XmlPullParser.END_TAG) {
                        if(parser.getEventType() != XmlPullParser.START_TAG) {
                            continue;
                        }
                        tipus = parser.getName();
                        if(tipus.equals("idAlumne")) {
                            dni = readText(parser);
                        }
                        else if(tipus.equals("idSessio")) {
                            idsessio = readText(parser);
                        }
                    }
                    Log.d("DNI", dni);
                    Log.d("DATA SESSIO", idsessio);
                    manager.insertarAssistencia(idsessio, dni, 1);
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void carregarDades(){
        carregarAlumnes();
        carregarAssignatures();
        carregarGrups();
        carregarAlumnesGrups();
        carregarGrupsAssignatura();
        //carregarSessionsGrup();
        //carregarAssistencies();
    }

    public void Inicialitzar(){

    }

}