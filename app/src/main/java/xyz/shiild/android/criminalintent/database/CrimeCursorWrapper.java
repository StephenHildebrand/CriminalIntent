package xyz.shiild.android.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import xyz.shiild.android.criminalintent.Crime;
import xyz.shiild.android.criminalintent.database.CrimeDbSchema.CrimeTable;

/**
 * A wrapper around a Cursor, allowing for adding additional methods to it.
 * @author Stephen Hildebrand
 * @version 7/28/2016
 */
public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * Pulls out relevant column data in the for of a Crime with appropriate UUID.
     * @return A Crime with the relevant data.
     */
    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));

        // Return a Crime with an appropriate UUID.
        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);

        return crime;
    }
}
