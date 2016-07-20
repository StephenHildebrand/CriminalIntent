package xyz.shiild.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Dialog Fragment that allows the user to select what time of day the crime occurred using a
 * DatePicker widget.
 *
 * @author Stephen Hildebrand
 * @version 7/16/2016
 */
public class DatePickerFragment extends DialogFragment {
    /** Id for the extra intent argument. */
    public static final String EXTRA_DATE = "xyz.shiild.android.criminalintent.date";
    /** Id for the date argument.  */
    private static final String ARG_DATE = "date";
    /** The DatePicker object. */
    private DatePicker mDatePicker;

    /**
     * To get data into the DatePickerFragment, the date is stashed in its arguments bundle
     * where it is able to be accessed. The arguments are created and set in the newInstance()
     * method which replaces the fragment constructor.
     *
     * @param date The date of the crime.
     * @return The Fragment now containing the bundle in its arguments.
     */
    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Creates the alert dialog. The AlertDialog.Builder class provides a fluent interface for
     * constructing an AlterDialog instance. First pass a Context into the AlertDialog.Builder
     * constructor, which returns an instance of AlertDialog.Builder. Next call the setTitle
     * and setPositiveButton methods to configure the dialog. setPositiveButton accepts a string
     * resource and an object that implements DialogInterface.OnClickListener. This button is
     * what the user should press to select the dialog's primary action.
     *
     * @param savedInstanceState    The previously saved instance
     * @return  The newly created alert dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Initialize the DatePicker using the timestamp info held in the Date.
        Date date = (Date)getArguments().getSerializable(ARG_DATE);

        // Create calendar object using the Date to configure it.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Retrieve the needed info from the calendar.
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Inflate the view
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);

        // Initialize the DatePicker using the Date from the arguments and a Calendar.
        mDatePicker = (DatePicker)v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year, month, day, null);

        return new AlertDialog.Builder(getActivity())
                .setView(v) // Set the dialog's View.
                .setTitle(R.string.date_picker_title)   // Set the dialog's Title.
                // Set the dialog's positive Button.
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    /**
                     * Retrieve the selected date and call sendResult.
                     *
                     * @param dialog The dialog to create.
                     * @param which The type of dialog.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();
                        Date date = new GregorianCalendar(year, month, day).getTime();
                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();  // Create the dialog.
    }

    /**
     * Creates an intent, puts it as an extra then calls CrimeFragment.onActivityResult(...).
     *
     * @param resultCode The code used to determine which what action to take.
     * @param date The date of the crime.
     */
    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null)
            return;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
