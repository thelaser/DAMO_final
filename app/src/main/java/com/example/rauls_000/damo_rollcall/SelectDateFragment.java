package com.example.rauls_000.damo_rollcall;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;


public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private EditText editData;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        editData = (EditText) getActivity().findViewById(R.id.editData);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        ((PasarLlista) getActivity()).setdate(yy, mm+1, dd);

    }
}