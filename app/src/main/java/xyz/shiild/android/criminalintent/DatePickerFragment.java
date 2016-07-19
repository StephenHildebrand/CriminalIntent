package xyz.shiild.android.criminalintent;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 *
 * @author Stephen Hildebrand
 * @version 7/16/2016
 */
public class DatePickerFragment extends DialogFragment {

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
        // Inflate the view
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);

        return new AlertDialog.Builder(getActivity())
                .setView(v) // Set the dialog's View.
                .setTitle(R.string.date_picker_title)   // Set the dialog's Title.
                .setPositiveButton(android.R.string.ok, null) // Set the dialog's positive Button.
                .create();  // Create the dialog.


    }
}
