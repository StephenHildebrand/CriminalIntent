package xyz.shiild.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewGroupCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
    /** Member variable for the Crime instance. */
    private Crime mCrime;
    /** Member variable for the EditText instance. */
    private EditText mTitleField;

    @Override
    public void onCreate(Bundle savedInstanceState) { // Note that Fragment.onCreate is public, to
        super.onCreate(savedInstanceState);           // allow it to be called by its host fragment.
        mCrime = new Crime();
    }

    /**
     * The onCreateView method is where you inflate the layout for the fragment's view and return
     * the inflated View to the hosting activity.
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
        // After the view is inflated, get a reference to the EditText and add a listener.

        return v;
    }
}
