package xyz.shiild.android.criminalintent.database;

/**
 * @author Stephen Hildebrand
 * @version 7/27/2016
 */
public class CrimeDbSchema {

    /**
     * The CrimeTable class exists only to define the String constants needed to describe
     * the moving pieces of the table definition.
     */
    public static final class CrimeTable {

        /** The name of the table in the database. */
        public static final String NAME = "crimes";

        /**
         * Inner class describing the columns of the table.
         */
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }
}
