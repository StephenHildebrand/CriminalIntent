package xyz.shiild.android.criminalintent;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 *
 * @author Stephen Hildebrand
 * @version 7/16/2016
 */
public class DatePickerFragment extends DialogFragment {

    // TODO Implement onCreateDialog() that builds an AlertDialog with a title and an OK button.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The AlertDialog.Builder class provides a fluent interface for constructing an
        // AlterDialog instance. First pass a Context into the AlertDialog.Builder constructor,
        // which returns an instance of AlertDialog.Builder.
        // Now call the setTitle and setPositiveButton methods to configure the dialog.
        // setPositiveButton accepts a string resource and an object that implements
        // DialogInterface.OnClickListener.
        return new AlertDialog.Builder(getActivity()).setTitle(
                R.string.date_picker_title).setPositiveButton(android.R.string.ok, null).create();
        // Above, you pass in an Android constant for OK and a null for the listener parameter
        // as it will be implemented later on. the .create() method returns the newly configured
        // AlertDialog instance.


    }
}
