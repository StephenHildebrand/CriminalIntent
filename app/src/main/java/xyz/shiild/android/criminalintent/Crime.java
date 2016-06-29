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
        mId = UUID.randomUUID();
    }

    public UUID getID() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
