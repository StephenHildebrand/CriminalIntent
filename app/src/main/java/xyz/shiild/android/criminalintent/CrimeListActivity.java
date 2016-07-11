package xyz.shiild.android.criminalintent;

import android.support.v4.app.Fragment;

/**
 * @author Stephen Hildebrand
 * @version 7/03/2016
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
