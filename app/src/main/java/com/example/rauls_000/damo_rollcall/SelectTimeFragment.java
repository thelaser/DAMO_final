package com.example.rauls_000.damo_rollcall;

/**
 * Created by rauls_000 on 03/01/2018.
 */

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;


public class SelectTimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public EditText editData;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        editData = (EditText) getActivity().findViewById(R.id.editData);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        int mm = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hh, mm,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hh, int mm) {
        ((PasarLlista) getActivity()).settime(hh, mm);
    }
}