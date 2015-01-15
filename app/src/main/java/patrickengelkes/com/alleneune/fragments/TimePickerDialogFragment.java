package patrickengelkes.com.alleneune.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import patrickengelkes.com.alleneune.R;


public class TimePickerDialogFragment extends DialogFragment {
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    public TimePickerDialogFragment() {

    }

    public TimePickerDialogFragment(TimePickerDialog.OnTimeSetListener callback) {
        this.timeSetListener = callback;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();

        return new TimePickerDialog(getActivity(),
                timeSetListener, cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), true);
    }
}
