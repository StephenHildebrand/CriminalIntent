package xyz.shiild.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Tells CrimeFragment which Crime to display by passing the crime ID as an Intent
 * extra when CrimeActivity is started.
 *
 * @author Stephen Hildebrand
 * @version 7/10/2016
 */
public class CrimeActivity extends SingleFragmentActivity {
    public static final String EXTRA_CRIME_ID = "xyz.shiild.android.criminalintent.crime_id";

    /**
     *
     * @param packageContext    The context of the package.
     * @param crimeId   The crime's Serializable UUID.
     * @return an Intent with the extra for the for the crime.
     */
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        // Create an explicit Intent.
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        // Put in a string key and the value the key maps to as an Extra.
        intent.putExtra(EXTRA_CRIME_ID, crimeId);

        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}