package xyz.shiild.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

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
     * Override onResume() in order to call updateUI() to reload the list.
     */
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    /**
     * Private method used to set up CrimeListFragment's user interface by creating a CrimeAdapter
     * and setting it on the RecyclerView.
     */
    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        // Create a CrimeAdapter. Set it on the RecyclerView.
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {    // If CrimeAdapter is already made, then reload it instead.
            mAdapter.notifyDataSetChanged();
        }

    }

    /**
     * CrimeHolder is a private inner ViewHolder class for RecyclerView. Finds the title TextView,
     * date TextView and solved CheckBox. By storing the results of calls to findViewById(int) when
     * it is called in createViewHolder(...), the work is already done for onBindViewHolder(...)
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
         * Handles how the app should respond to user presses/clicks on a View/ViewHolder. Creates
         * an explicit intent that names the CrimeActivity class. Uses the getActivity(0 method to
         * pass its hosting activity as the Context object needed by the Intent constructor.
         * @param v The clicked View.
         */
        @Override
        public void onClick(View v) {
            // Replace the toast with code that starts an instance of CrimeActivity.
//            Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getActivity(), CrimeActivity.class);
            // Updated CrimeHolder to use the newIntent method while passing in the crime ID.
            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getID());
            startActivity(intent);
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
            // Inflate a layout, simple_list_item_1 which contains a single TextView, styled to look
            // nice in a list.
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

        /**
         * Get the count of crimes in the list. */
        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
