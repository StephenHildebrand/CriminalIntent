package xyz.shiild.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * The Activity for a crime. Hosting activities should know the specifics of how to host their
 * fragments, but fragments shouldn't know specifics about their activities to maintain the
 * flexibility of independent fragments.
 *
 * @author Stephen Hildebrand
 * @version 7/10/2016
 */
public class CrimeActivity extends SingleFragmentActivity {
    /** UUID for the crime extra. */
    private static final String EXTRA_CRIME_ID = "xyz.shiild.android.criminalintent.crime_id";

    /**
     * Tells CrimeFragment which Crime to display by passing the crime ID as an Intent
     * extra when CrimeActivity is started.
     *
     * @param packageContext    The context of the package.
     * @param crimeId           The crime's Serializable UUID.
     * @return an Intent with the extra for the for the crime.
     */
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        // Create an explicit Intent.
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        // Put in a string key and the value the key maps to as an Extra.
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    /**
     * Call CrimeFragment.NewInstance(UUID) to create a new Fragment rather than the default
     * constructor. Passes in the UUID it retrieved from its extra.
     * @return  A new Crime Fragment.
     */
    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }
}