package xyz.shiild.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * @author Stephen
 * @version 7/15/2016
 */
public class CrimePagerActivity extends AppCompatActivity {
    /** Extra for the crime ID. */
    private static final String EXTRA_CRIME_ID = "xyz.shiild.android.criminalintent.crime_id";
    /** The ViewPager for the Crimes. */
    private ViewPager mViewPager;
    /** The list of Crimes. */
    private List<Crime> mCrimes;

    /**
     * Method to create a new intent to CrimePagerActivity with an extra.
     *
     * @param packageContext The parent Activity/View?
     * @param crimeId ID for the crime
     * @return the new intent
     */
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        // Create a new explicit intent.
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        // Store the crimeId extra along with an ID for the extra itself?
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    /**
     * The onCreate method for creating the CrimePagerActivity. Remember that the
     * FragmentStatePagerAdapter is your agent for managing the conversation with ViewPager. For
     * it to do its job with fragments that getItem(int) returns, it needs to be able to add them
     * to your activity, which is why it needs your FragmentManager.
     *
     *
     * @param savedInstanceState Bundle from the previous instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        // Find the ViewPager in the activity's view.
        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);
        // Get the data set from CrimeLab - the List of crimes.
        mCrimes = CrimeLab.get(this).getCrimes();
        // Get the activity's instance of FragmentManager.
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Set the adapter to be an unnamed instance of FragmentStatePagerAdapter
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }
            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        // By default, ViewPager shows the first item in its PagerAdapter
        // Find the index of the Crime to display by looping through and matching the crime's ID.
        for (int i = 0; i < mCrimes.size(); i++)
            // When the Crime instance is found, set the current item to the index of that crime
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
    }
}
