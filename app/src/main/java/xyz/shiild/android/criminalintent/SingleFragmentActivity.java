package xyz.shiild.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Abstract class for implementation an activity with a single fragment.
 * @author Stephen Hildebrand
 * @version 7/7/2016
 */
public abstract class SingleFragmentActivity extends FragmentActivity {
    /**
     * An abstract method called by subclasses to instantiate the fragment.
     *
     * @return An instance of the fragment the activity is hosting.
     */
    protected abstract Fragment createFragment();

    /** Called when the activity is first created. */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the activity's view to be inflated from activity_fragment.
        setContentView(R.layout.activity_fragment);


        FragmentManager fm = getSupportFragmentManager();

        // Give FragmentManager a fragment to manage, retrieved by container view ID
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            // Create and commit a fragment transaction.
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}