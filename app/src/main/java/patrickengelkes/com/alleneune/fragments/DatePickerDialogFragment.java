package patrickengelkes.com.alleneune.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;


public class DatePickerDialogFragment extends DialogFragment {
    private DatePickerDialog.OnDateSetListener dateSetListener;

    public DatePickerDialogFragment() {

    }

    public DatePickerDialogFragment(DatePickerDialog.OnDateSetListener callback) {
        dateSetListener = callback;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();

        return new DatePickerDialog(getActivity(),
                dateSetListener, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    }
}
