package xyz.shiild.android.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * The model layer for CriminalIntent.
 * @author Stephen
 * @version 6/29/2016
 */
public class Crime {
    /** Unique class identifier. */
    private UUID mId;
    /** Title of the Crime class. */
    private String mTitle;
    /** The date a crime occurred. */
    private Date mDate;
    /** Whether the crime has been solved. */
    private boolean mSolved;

    /**
     * Crime class constructor.
     */
    public Crime() {
        this(UUID.randomUUID()); // Generate unique identifier.
    }

    public Crime(UUID id) {
        mId = id;
        mDate = new Date(); // Current date via default Date constructor.
    }

    /**
     * Field Access Methods.
     */
    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

}
