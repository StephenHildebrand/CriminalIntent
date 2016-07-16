package xyz.shiild.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A centralized data stash for Crime objects.
 *
 * @author Stephen Hildebrand
 * @version 7/7/2016
 */
public class CrimeLab {
    /** A static CrimeLab variable for the CrimeLab singleton */
    private static CrimeLab sCrimeLab;
    /** A list of crimes. */
    private List<Crime> mCrimes;

    /**
     * Private constructor for the singleton CrimeLab.
     * @param context The context to initialize with.
     */
    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        // Initialize the list with 100 Crimes.
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0); // Every other crime set as solved.
            mCrimes.add(crime);
        }
    }

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null)
            sCrimeLab = new CrimeLab(context);
        return sCrimeLab;
    }

    /**
     * Returns the list of crimes.
     * @return The list of crimes.
     */
    public List<Crime> getCrimes() {
        return mCrimes;
    }

    /**
     * Searches the list of crimes for a Crime with the given ID.
     * @param id The ID associated with the Crime to search for.
     * @return The Crime with the given ID, or null if not found.
     */
    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes)
            if (crime.getId().equals(id))
                return crime;
        return null;
    }
}
