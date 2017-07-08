package com.dan.facebooktracking.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.dan.facebooktracking.data.AppContract.TrackingEntry;
/**
 * Created by Dat T Do on 7/7/2017.
 */

/**
 * Database helper for Tracking app. Manages database creation and version management.
 * Create class to handle the app database:
 * to store the database name and version
 * to create the table
 * to update the table
 */

public class AppDbHelper extends SQLiteOpenHelper {
    /*
    constant for database name and version
     */
    public static final String DATA_BASE_NAME = "tracking.db";
    public static final int DATA_BASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link AppDbHelper}.
     *
     * @param context of the app
     */
    public AppDbHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }


    /*
    * the method returns new Cursor object which select all the columns and rows
    * from the tracking table within the tracking.db database
     */
    public static final Cursor readDatabaseInfo(Context context){

        // Create and/or open a database to read from it
        AppDbHelper mAppDbHelper = new AppDbHelper(context);
        SQLiteDatabase db = mAppDbHelper.getReadableDatabase();

        //define the projection which include all column of the database
        String[] projection = {
                TrackingEntry._ID,
                TrackingEntry.COLUMN_USERNAME,
                TrackingEntry.COLUMN_CHECKING_ON
        };

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        Cursor cursor = db.query(TrackingEntry.TABLE_NAME, projection,
                null, null, null, null, null);
        return cursor;
    }



    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * Create a String that contains the SQL statement to create the tracking table
         * The column_checking_on take the Type TIMESTAMP and set default value of CURRENT_TIMESTAMP
        */
         String SQL_CREATE_TRACKING = "CREATE TABLE " + TrackingEntry.TABLE_NAME + " ("
                + TrackingEntry._ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
                +TrackingEntry.COLUMN_USERNAME+ " TEXT, "
                + TrackingEntry.COLUMN_CHECKING_ON + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_TRACKING);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //this database is only a cache for the old data, so its
        //upgrade policy is to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /*
  define the SQLite command for to delete the Tracking Table
   */
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TrackingEntry.TABLE_NAME;
}
