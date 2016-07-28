package xyz.shiild.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Fragment that uses the fragment_crime_list View. Defines ViewHolder as an inner class.
 * @author Stephen Hildebrand
 * @version 7/10/2016
 */
public class CrimeListFragment extends Fragment {
    /** Key for tracking the subtitle visibility across instances. */
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    /** A RecyclerView for viewing the list of Crimes. */
    private RecyclerView mCrimeRecyclerView;
    /** An Adapter for managing Crimes. */
    private CrimeAdapter mAdapter;
    /** True if the subtitle is visible, false if not visible. */
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Tell FragmentManager that CrimeListFragment needs to receive menu callbacks.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView)view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Retrieve the savedInstanceState, if there is one.
        if (savedInstanceState != null)
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);

        updateUI();
        return view;
    }

    /**
     * Override onResume() in order to call updateUI() to reload the list.
     */
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    /**
     * Save the mSubtitleVisible instance variable across rotations.
     *
     * @param outState The instance state to load.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    /**
     * Creates the options menu by inflating the menu in fragment_crime_list. Also triggers a
     * re-creation of the action items when the user presses on the Show Subtitle action item.
     *
     * @param menu The menu instance to populate
     * @param inflater The object to inflate the menu
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        // Determine whether to show the Hide or Show Subtitle action button.
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible)
            subtitleItem.setTitle(R.string.hide_subtitle);
        else
            subtitleItem.setTitle(R.string.show_subtitle);
    }

    /**
     * Handle selection of menu items (currently only the add crime option).
     *
     * @param item  The selected menu item.
     * @return  True to indicate that no further processing is necessary
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:      // New Crime action button.
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:  // Show Subtitle action button
                mSubtitleVisible = !mSubtitleVisible;   // Switch the SubtitleVisible variable
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:    // Calls the superclass if the item ID isn't in your implementation.
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Sets the subtitle of the toolbar. The subtitle displays the number of crimes in
     * CriminalIntent. Called when the user presses on the new subtitle action item.
     */
    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
//        int crimeCount = crimeLab.getCrimes().size();
        // Generate the subtitle string with replacement values for the placeholders.
//        String subtitle = getString(R.string.subtitle_format, crimeCount);

        // Implement plural string resources.
        int crimeSize = crimeLab.getCrimes().size();
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plural, crimeSize, crimeSize);

        // Respect the mSubtitleVisible member variable when showing/hiding the subtitle toolbar.
        if (!mSubtitleVisible)
            subtitle = null;

        // Cast the activity hosting CrimeListFragment to an AppCompatActivity and set it.
        AppCompatActivity activity = (AppCompatActivity)getActivity();

        if (activity.getSupportActionBar() != null)
            activity.getSupportActionBar().setSubtitle(subtitle);
    }

    /**
     * Private method used to set up CrimeListFragment's user interface by creating a
     * CrimeAdapter and setting it on the RecyclerView. Creates a CrimeAdapter and sets it
     * on the RecyclerView. If CrimeAdapter is already made, then it's reloaded instead.
     */
    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);    // Create a CrimeAdapter.
            mCrimeRecyclerView.setAdapter(mAdapter);    // Set it on the RecyclerView.
        } else {    // Reload it instead.
            // Use RecyclerView.Adapter's notifyItemChanged(int) to reload a single list item.
            // First must determine which position has changed and reload the correct item.
            mAdapter.setCrimes(crimes); // Swap out the crimes that CrimeAdapter displays.
            mAdapter.notifyDataSetChanged();

        }
        updateSubtitle(); // Update the subtitle text when returning to CrimeListActivity.
    }

    /**
     * CrimeHolder is a private inner ViewHolder class for RecyclerView. It finds the title TextView,
     * date TextView and solved CheckBox. By storing the results of calls to findViewById(int)
     * when called in createViewHolder(...), the work is already done for onBindViewHolder(...)
     * which is important since it is called repeatedly. Also implements View.OnClickListener,
     * for reaction to user touch. Since each View has an associated ViewHolder, the ViewHolder
     * can be used as the OnClickListener for its View.
     */
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /** The current Crime. */
        private Crime mCrime;
        /** The title TextView. */
        private TextView mTitleTextView;
        /** The date TextView. */
        private TextView mDateTextView;
        /** The solved CheckBox. */
        private CheckBox mSolvedCheckBox;

        /**
         * Constructor for a CrimeHolder object.
         * @param itemView  The itemView to initialize with.
         */
        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView)itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox)itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }

        /**
         * Use the given Crime to update the title, date, and solved checkbox to reflect the state
         * of the Crime.
         * @param crime The current Crime.
         */
        public void bindCrime(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        /**
         * Handles how the app should respond to user presses/clicks on a View/ViewHolder.
         * Creates an explicit intent that names the CrimePagerActivity class. Uses the
         * getActivity() method to pass its hosting activity as the Context object needed
         * by the Intent constructor.
         *
         * @param v The clicked View.
         */
        @Override
        public void onClick(View v) {
            // Updated CrimeHolder to use the newIntent method while passing in the crime ID.
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent); // To get results back, would call startActivityForResult(...)
        }
    }


    /**
     * Private inner Adapter class. The RecyclerView will communicate with this adapter when a
     * ViewHolder needs to be created or connected with a Crime object. The RecyclerView itself
     * will not know anything about the Crime object, but the Adapter will know all of Crime’s
     * intimate and personal details.
     */
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        /** The list of Crimes. */
        private List<Crime> mCrimes;

        /**
         * Constructor for a CrimeAdapter object used to create a list of Crimes via CrimeHolder.
         *
         * @param crimes    The crimes to initialize with.
         */
        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        /**
         * Called by the RecyclerView when it needs a new View to display an item. RecyclerView
         * does not expect it to have any data yet.
         *
         * @param parent    The View's parent.
         * @param viewType  The type of View.
         * @return  A ViewHolder without data yet.
         */
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // Create the View, wrap it in a ViewHolder.
            // Inflate a layout, simple_list_item_1 which contains a single TextView, styled to
            // look nice in a list.
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        /**
         * Binds a ViewHolder's View to the model object. In order to bind the View, it uses the
         * position to find the right model data, then updates the View to reflect that data.
         *
         * @param holder    The ViewHolder
         * @param position  The position in the data set of the desired model data (Crime).
         */
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            // Get the Crime at position.
            Crime crime = mCrimes.get(position);
            // Use the new bindCrime method to bind the crime to
            holder.bindCrime(crime);
        }

        /** Get the count of crimes in the list. */
        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        /**
         * Swaps out the crimes that CrimeAdapter displays. Used in the updateUI method
         * to refresh the view of crimes.
         * @param crimes The list of crimes.
         */
        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
        }

    }
}
