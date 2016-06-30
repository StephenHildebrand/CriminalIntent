package xyz.shiild.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;

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
    private Crime mCrime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }
}
