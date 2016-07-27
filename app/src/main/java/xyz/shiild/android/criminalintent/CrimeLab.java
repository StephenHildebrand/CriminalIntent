package xyz.shiild.android.criminalintent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import xyz.shiild.android.criminalintent.database.CrimeBaseHelper;

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
    /** Context instance variable...to be used in chapter 16. */
    private Context mContext;
    /** Storage for the crime database. */
    private SQLiteDatabase mDatabase;

    /**
     * Private constructor for the singleton CrimeLab. Retrieves the current application context,
     * then calls getWritableDatabase to open/initialize the database.
     * @param context The context to initialize with.
     */
    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
        mCrimes = new ArrayList<>();
    }

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null)
            sCrimeLab = new CrimeLab(context);
        return sCrimeLab;
    }

    /**
     * Add a new crime.
     * @param c The crime to add.
     */
    public void addCrime(Crime c) {
        mCrimes.add(c);
    }

    /**
     * Deletes the crime.
     * @param c The crime to delete.
     */
    public void deleteCrime(Crime c) {
        mCrimes.remove(c);
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
