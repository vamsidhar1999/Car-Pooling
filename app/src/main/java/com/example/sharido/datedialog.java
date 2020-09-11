package com.example.sharido;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class datedialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (DatePickerDialog.OnDateSetListener)getActivity(),year,month,day);
        calendar.add(Calendar.DATE, 0);
        Date date1 = calendar.getTime();
        datePickerDialog.getDatePicker().setMinDate(date1.getTime()-(date1.getTime()%(24*60*60*1000)));

        return datePickerDialog;
    }
}
