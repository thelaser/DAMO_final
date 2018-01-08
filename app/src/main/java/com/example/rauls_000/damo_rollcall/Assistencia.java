package com.example.rauls_000.damo_rollcall;

import java.util.Calendar;

/**
 * Created by thelaser on 31/12/17.
 */

public class Assistencia {
    // There are 3 possible types A temps(1), Falta(2), Retard(3).
    private int tipusAssistencia;
    private CharSequence dni;
    private String date;

    public Assistencia(CharSequence dni, String data_assist) {
        tipusAssistencia = 1;
        this.dni = dni;
        date = data_assist;
    }

    public void changeTipus() {
        tipusAssistencia = tipusAssistencia%3 + 1;
    }

    public int getAssist() {
        return tipusAssistencia;
    }

    public CharSequence getDNI() {
        return dni;
    }

    public String getDate() {
        return date;
    }

    public void setTipus(int value) {
        tipusAssistencia = value;
    }
}

