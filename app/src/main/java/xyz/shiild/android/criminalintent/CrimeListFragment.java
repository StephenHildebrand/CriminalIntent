package xyz.shiild.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Fragment that uses the fragment_crime_list View. Defines ViewHolder as an inner class.
 * @author Stephen Hildebrand
 * @version 7/10/2016
 */
public class CrimeListFragment extends Fragment {
    /** A RecyclerView for viewing the list of Crimes. */
    private RecyclerView mCrimeRecyclerView;
    /** An Adapter for managing Crimes. */
    private CrimeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    /**
     * Inner class used to set up CrimeListFragment's user interface by creating a CrimeAdapter and
     * setting it on the RecyclerView.
     */
    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        // Create a CrimeAdapter. Set it on the RecyclerView.
        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Private inner ViewHolder class that maintains a reference to a single view: the title TextView.
     */
    private class CrimeHolder extends RecyclerView.ViewHolder {
        /** The Title TextView. */
        public TextView mTitleTextView;

        /**
         * Constructor for a CrimeHolder object.
         * @param itemView  The itemView to initialize with. Current implementation expects a TextView.
         */
        public CrimeHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }
    }

    /**
     * Private inner Adapter class. The RecyclerView will communicate with this adapter when a
     * ViewHolder needs to be created or connected with a Crime object.
     *
     * <p>
     *     The RecyclerView itself will not know anything about the Crime object, but the Adapter
     *     will know all of Crime’s intimate and personal details.
     * </p>
     */
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        /** The list of Crimes. */
        private List<Crime> mCrimes;

        /**
         * Constructor for a CrimeAdapter object used to create a list of Crimes via CrimeHolder.
         * @param crimes The crimes to initialize with.
         */
        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        /**
         * Called by the RecyclerView when it needs a new View to display an item. RecyclerView
         * does not expect it to have any data yet.
         *
         * @param parent The View's parent.
         * @param viewType The type of View.
         * @return A ViewHolder without any data yet.
         */
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // Create the View, wrap it in a ViewHolder.
            // Inflate a layout, simple_list_item_1 which contains a single TextView, styled to look
            // nice in a list.
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new CrimeHolder(view);
        }

        /**
         * Binds a ViewHolder's View to the model object. In order to bind the View, it uses the
         * position to find the right model data, then updates the View to reflect that data.
         *
         * @param holder The ViewHolder
         * @param position The position in the data set of the desired model data (Crime).
         */
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            // Get the Crime at position.
            Crime crime = mCrimes.get(position);
            // Bind this Crime to the View by sending it's title to the ViewHolder's TextView.
            holder.mTitleTextView.setText(crime.getTitle());
        }

        /**
         * Get the count of crimes in the list. */
        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
