package xyz.shiild.android.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/** Allows referral to the String constants in CrimeDbSchema.CrimeTable by typing in
 * CrimeTable.Cols.UUID, rather than the entirety of CrimeDBSchema.CrimeTable.Cols.UUID. */
import xyz.shiild.android.criminalintent.Crime;
import xyz.shiild.android.criminalintent.database.CrimeDbSchema.CrimeTable;

/**
 * SQLiteOpenHelper is a class designed to minimize work of opening a SQLiteDatabase
 * @author Stephen Hildebrand
 * @version 7/27/2016
 */
public class CrimeBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CrimeTable.NAME + "("
                + "_id integer primary key autoincrement, "
                + CrimeTable.Cols.UUID + ", "
                + CrimeTable.Cols.TITLE + ", "
                + CrimeTable.Cols.DATE + ", "
                + CrimeTable.Cols.SOLVED + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
