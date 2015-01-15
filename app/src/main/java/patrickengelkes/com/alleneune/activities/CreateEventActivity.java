package patrickengelkes.com.alleneune.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import patrickengelkes.com.alleneune.fragments.DatePickerDialogFragment;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.fragments.TimePickerDialogFragment;

public class CreateEventActivity extends Activity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    protected EditText eventNameEditText;
    protected TextView datePickerTextView;
    protected TextView timePickerTextView;
    protected Button sendEventInvitesButton;
    protected Calendar globalCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        globalCalendar = new GregorianCalendar();

        eventNameEditText = (EditText) findViewById(R.id.event_name_edit_text);
        datePickerTextView = (TextView) findViewById(R.id.date_picker_text_view);
        datePickerTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                DialogFragment datePickerFragment = new DatePickerDialogFragment(CreateEventActivity.this);
                datePickerFragment.show(ft, "date_dialog");
            }
        });

        timePickerTextView = (TextView) findViewById(R.id.time_picker_text_view);
        timePickerTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                DialogFragment timePickerFragment = new TimePickerDialogFragment(CreateEventActivity.this);
                timePickerFragment.show(ft, "time_dialog");
            }
        });

        sendEventInvitesButton = (Button) findViewById(R.id.send_event_invites_button);
        sendEventInvitesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm");

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        datePickerTextView.setText(simpleDateFormat.format(calendar.getTime()));

        globalCalendar.set(Calendar.YEAR, year);
        globalCalendar.set(Calendar.MONTH, monthOfYear);
        globalCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        timePickerTextView.setText(simpleDateFormat.format(calendar.getTime()));

        globalCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        globalCalendar.set(Calendar.MINUTE, minute);
    }
}
