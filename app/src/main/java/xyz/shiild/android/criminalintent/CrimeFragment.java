package xyz.shiild.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewGroupCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

/**
 * Controller class that interacts with model and view objects. Its job is to present
 * the details of a specific crime and update them as the user changes them.
 *
 * As you subclass the Fragment class, you will notice that Android Studio finds
 * two classes with the Fragment name. You will see Fragment (android.app) and
 * Fragment (android.support.v4.app). The android.app Fragment is the version of
 * fragments that are built into the Android OS. We will use the support library
 * version, so be sure to select the android.support.v4.app version.
 *
 * @author Stephen Hildebrand
 * @version 6/30/2016
 */
public class CrimeFragment extends Fragment {
    /** UUID for the crime arguments bundle. */
    private static final String ARG_CRIME_ID = "crime_id";
    /** Constant for the DatePickerFragment's tag. */
    private static final String DIALOG_DATE = "DialogDate";
    /** Constant for the date request code. */
    private static final int REQUEST_DATE = 0;

    /** Member variable for the Crime instance. */
    private Crime mCrime;
    /** Member variable for the EditText instance. */
    private EditText mTitleField;
    /** The date displayed by the button. */
    private Button mDateButton;
    /** The solved/unsolved status. */
    private CheckBox mSolvedCheckBox;

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();                     // Create a bundle.
        args.putSerializable(ARG_CRIME_ID, crimeId);    // Attach the arguments it.
        CrimeFragment fragment = new CrimeFragment();   // Create a fragment instance.
        fragment.setArguments(args);                    // Attach the arguments bundle to it.

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { // Note that Fragment.onCreate is public, to
        super.onCreate(savedInstanceState);           // allow it to be called by its host fragment.
        // Retrieve the UUID from the fragment arguments.
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        // Use the retrieved UUID to fetch theCrime from CrimeLab.
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
        // Tell FragmentManager that CrimeFragment has a menu.
        setHasOptionsMenu(true);
    }

    /**
     * Updates CrimeLab's copy of the Crime in order to write out the Crime instances when
     * CrimeFragment is done.
     */
    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity()).updateCrime(mCrime);
    }

    /**
     * The onCreateView method is where you inflate the layout for the fragment's view and return
     * the inflated View to the hosting activity.
     *
     * @param inflater Used to inflate the layout.
     * @param container The View's parent, used to inflate the layout.
     * @param savedInstanceState Contains the data used to recreated the View from a saved state
     * @return The newly created view object.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // LayoutInflater.inflate inflates the fragments view via the layout resource ID.
        // False is pass in since the view will be added in the activity's code.
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        // Wire up EditText to respond to user input.
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank.
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
                // This one left blank intentionally too.
            }
        });

        // Set up the date button.
        mDateButton = (Button)v.findViewById(R.id.crime_date);
        // Set its text as the date of the crime.
        updateDate();

        // Set a View.OnClickListener that shows a DatePickerFragment when date button is pressed.
        mDateButton.setOnClickListener(new View.OnClickListener() {
            // Pass in a FragmentManager (the FragmentTransaction will be automatically created).
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment().newInstance(mCrime.getDate());

                // Make CrimeFragment the target fragment of the DatePickerFragment instance.
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        // Set up the Check box.
        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Set the crime's solved property.
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }

    /**
     * Create the options menu by inflating fragment_crime to show the delete crime action button.
     *
     * @param menu The menu instance to populate.
     * @param inflater The object to inflate the menu.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_crime:
                CrimeLab.get(getActivity()).deleteCrime(mCrime);
                // Pop the user back to the previous activity by calling the finish method on
                // CrimeFragment's hosting activity.
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Retrieve the extra, set the date on the Crime, and refresh the text of the date button.
     *
     * @param requestCode Code ID for the request.
     * @param resultCode Code ID for the result.
     * @param data The date.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }
    }

    /** Private method for updating the date text on a button. */
    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }
}
