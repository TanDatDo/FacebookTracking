package com.dan.facebooktracking;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dan.facebooktracking.data.AppContract.TrackingEntry;
import com.dan.facebooktracking.data.AppDbHelper;
import com.dan.facebooktracking.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Locale;

/*
* main activity that have a button to update information into database
* display the total times that users have used facebook
* display the last times users use facebook
 */

public class MainActivity extends AppCompatActivity {

    /**Pet Database Helper new object*/
    private AppDbHelper mAppDbHelper= new AppDbHelper(this);
    //use data binding to find view id (this method replace the findViewbyID method)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // find and set OnClickListener to the update_button
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* call insertData method to insert new data into database
                 */
                insertData();

                /*
                *call displayDatabase info display the total times the user has checked the facebook,
                * and the last time users check their facebook pages.
                */
                displayDatabaseInfo();
            }
        });
    }

    private void insertData(){
        //create new writable sqlite object to insert data
        SQLiteDatabase db = mAppDbHelper.getWritableDatabase();

        /**Create ContentValues object named values*/
        ContentValues values= new ContentValues();

        // input the user name into username column
        values.put(TrackingEntry.COLUMN_USERNAME, "Dan");


        /**Insert the data into the data base using values objects*/
        long newRowId= db.insert(TrackingEntry.TABLE_NAME, null, values);


        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with the update", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "successfully update", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    public void displayDatabaseInfo() {
        // Create and/or open a database to read from it
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

        /* codes used to show the info of the last check on facbook
             */
        try {
            // move the cursor to the last row
            cursor.moveToLast();
            // get the column index of the time
            int timeColumnIndex = cursor.getColumnIndex(TrackingEntry.COLUMN_CHECKING_ON);
            // get the String which show the time when the users last checked facebook
            String time=cursor.getString(timeColumnIndex);


            //find and set Text on the display Text in main activity layout
            final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
            binding.displayText.setText(
                    // Display the total number of rows in the Cursor
                    // (which reflects the total number of times that the users check facebook.)
                    "You have open Facebook for: " + cursor.getCount()+ " time(s)"
                            //display the last time the users using facebook
                    +"\n" + "The last time you check facebook was at " + time );

        } finally {
            //close the cursor
            cursor.close();
        }
    }}