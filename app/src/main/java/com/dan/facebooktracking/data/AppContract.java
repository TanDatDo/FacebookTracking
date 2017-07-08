package com.dan.facebooktracking.data;

import android.provider.BaseColumns;
import com.dan.facebooktracking.data.AppDbHelper;

/**
 * //Contract class define constant for table name, all column names
 */

public class AppContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private AppContract() {}

    /**
     * Inner class that defines constant values for the tracking database table.
     * Each entry in the table represents a single pet.
     */
    public static final class TrackingEntry implements BaseColumns {

        //Name of the database table for counting
        public final static String TABLE_NAME= "tracking";

        //Name for the ID column
        public final static String COLUMN_ID = BaseColumns._ID;

        //User name
        public final static String COLUMN_USERNAME =" User_Name";

        //specific time when the button is clicked
        public final static String COLUMN_CHECKING_ON= "opening_Facebook_at";
    }

}
