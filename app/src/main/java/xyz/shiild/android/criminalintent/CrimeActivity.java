package xyz.shiild.android.criminalintent;

import android.support.v4.app.Fragment;

/**
 * @author Stephen Hildebrand
 * @version 7/10/2016
 */
public class CrimeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}