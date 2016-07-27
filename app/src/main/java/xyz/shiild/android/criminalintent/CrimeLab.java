package xyz.shiild.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import xyz.shiild.android.criminalintent.database.CrimeBaseHelper;
import xyz.shiild.android.criminalintent.database.CrimeDbSchema.CrimeTable;

/**
 * A centralized data stash for Crime objects.
 *
 * @author Stephen Hildebrand
 * @version 7/7/2016
 */
public class CrimeLab {
    /** A static CrimeLab variable for the CrimeLab singleton */
    private static CrimeLab sCrimeLab;
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

    }

    /**
     * Deletes the crime.
     * @param c The crime to delete.
     */
    public void deleteCrime(Crime c) {
//        mCrimes.remove(c);
    }

    /**
     * Returns the list of crimes.
     * @return The list of crimes.
     */
    public List<Crime> getCrimes() {
        return new ArrayList<>();
    }

    /**
     * Searches the list of crimes for a Crime with the given ID.
     * @param id The ID associated with the Crime to search for.
     * @return The Crime with the given ID, or null if not found.
     */
    public Crime getCrime(UUID id) {
//        for (Crime crime : mCrimes)
//            if (crime.getId().equals(id))
//                return crime;
        return null;
    }

    /**
     * Private method for shuttling a Crime into a ContentValues. Creates the ContentValues, then
     * puts each of the four keys with its associated column with it into it.
     * @param crime The Crime to be added
     * @return the ContentValues key-value pair
     */
    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

        return values;
    }
}