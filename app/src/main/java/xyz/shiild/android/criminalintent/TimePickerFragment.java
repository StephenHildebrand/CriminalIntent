package xyz.shiild.android.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Dialog fragment that allows the user to select what time of day the crime occurred using
 * a TimePicker widget.
 *
 * @author Stephen Hildebrand
 * @version 7/20/2016
 */
public class TimePickerFragment extends DialogFragment {
    /** Id for the extra time intent argument. */
    public static final String EXTRA_TIME = "xyz.shiild.android.criminalintent.time";
    /** Id for the time argument.  */
    private static final String ARG_TIME = "time";
    /** The TimePicker object. */
    private TimePicker mTimePicker;

    /**
     * Fragment used to present a Time widget to the user.
     * @param time The time.
     * @return The fragment to pick the time.
     */
    public static TimePickerFragment newInstance(Time time) {
        Bundle args = new Bundle();             // Create new Bundle.
        args.putSerializable(ARG_TIME, time);   //Add the args to the bundle
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Initialize the TimePicker using the bundle's Time.
        Time time = (Time)getArguments().getSerializable(ARG_TIME);

        // Instantiate calendar with the Time.
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);

        // Retrieve the time data from the calendar.
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);

        // Inflate the TimePicker view.
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);

        // Initialize the TimePicker using the time from the Calendar.
        mTimePicker = (TimePicker)v.findViewById(R.id.dialog_time_time_picker);
        mTimePicker.setHour(hour);
        mTimePicker.setMinute(minute);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hour = mTimePicker.getHour();
                        int minute = mTimePicker.getMinute();
                        Date date = new GregorianCalendar(hour, )
                    }
                })
    }

}
