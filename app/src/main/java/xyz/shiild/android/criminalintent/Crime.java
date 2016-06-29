package xyz.shiild.android.criminalintent;

import java.util.UUID;

/**
 *
 * @author Stephen
 * @version 6/29/2016
 */
public class Crime {
    /** Unique class identifier. */
    private UUID mID;
    /** Title of the Crime class. */
    private String mTitle;

    /**
     * Crime class constructor.
     */
    public Crime() {
        // Generate unique identifier
        mID = UUID.randomUUID();
    }

    /** UUID getter (read-only). */
    public UUID getID() {
        return mID;
    }

    /** Title getter. */
    public String getTitle() {
        return mTitle;
    }

    /** Title setter. */
    public void setTitle(String title) {
        mTitle = title;
    }
}
