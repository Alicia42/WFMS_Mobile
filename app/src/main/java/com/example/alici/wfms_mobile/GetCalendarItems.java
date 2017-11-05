package com.example.alici.wfms_mobile;

/*
 * Created by Libby Jennings on 6/10/17.
 * Description: Class for getting and displaying scheduled booking calendar items
 */

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

import java.util.Timer;
import java.util.TimerTask;

public class GetCalendarItems extends Calendar_Base_Activity {

    public ArrayList<WeekViewEvent> bookingsList = new ArrayList<WeekViewEvent>();
    public boolean update = false;

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {

        //stops the application from constantly grabbing data from the database causing a thread pool exception
        if (bookingsList.isEmpty() || update) {
            getBookings();
            refreshFiveMinutes();
        }

        // Return only the events that matches newYear and newMonth.
        List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : bookingsList) {
            if (eventMatches(event, newYear, newMonth)) {
                matchedEvents.add(event);
            }
        }

        return matchedEvents;
    }

    //Method for returning events that are in the same year and month as users actual date
    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }

    private void refreshFiveMinutes() {

        update = false; //reset update
        int delay = 300000;// 5 minutes

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {
                update = true; //allow update
                Log.i("update", "true");
            }
        }, delay);
    }

    //Method for checking wifi and mobile network connection
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            //check wifi connection
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true; //wifi connection is there
            //check mobile network connection
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true; //mobile network connection is there
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    //Method for getting scheduled bookings
    private void getBookings() {

        //Check if there is internet connection
        if (!haveNetworkConnection()) {

            //Display alert dialog pop-up error message and refresh button to re-try when internet connection is reestablished
            Context context = getApplicationContext();
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(GetCalendarItems.this, R.style.myDialog);
            } else {
                builder = new AlertDialog.Builder(context);
            }
            builder.setTitle("No Internet Connection")
                    .setMessage("Please connect and click refresh to try again")
                    .setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            getBookings();
                        }
                    })
                    .show();
        }
        //Is internet connection
        else {

            List<Header> headers = new ArrayList<Header>();
            headers.add(new BasicHeader("Accept", "application/json"));

            //async get request to server
            WCHRestClient.get(GetCalendarItems.this, "/getbookingdetails", headers.toArray(new Header[headers.size()]),
                    null, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                            //add returned and converted bookings to array list
                            bookingsList = convertBookings(response);

                        }
                    });
        }

    }

    //Method for converting bookings to calendar items and displaying them
    public ArrayList<WeekViewEvent> convertBookings(JSONArray response) {

        ArrayList<Booking> bookingArrayList = new ArrayList<Booking>();
        ArrayList<WeekViewEvent> thisSchedulesList = new ArrayList<WeekViewEvent>();

        //get JSON objects and add to array list
        for (int i = 0; i < response.length(); i++) {
            try {
                bookingArrayList.add(new Booking(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String concat = "";

        //for each booking in the array, convert to a week view event and add to array list
        for (Booking booking : bookingArrayList) {

            try {

                //Filter bookings by logged in user ID
                if (booking.getUserID() == MainActivity.User.userID) {

                    //concat together customer first name + message to display on event
                    concat = booking.getFirstName() + "'s House";

                    //get date of booking and convert to Calendar object
                    java.sql.Date dat = booking.getInstallDate();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dat);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);
                    Calendar startTime = Calendar.getInstance();
                    Calendar endTime = (Calendar) startTime.clone();

                    //Set booking start time to 9am and end time to 1pm if scheduled in the AM
                    if (booking.getInstallTime().equals("AM")) {

                        startTime = Calendar.getInstance();
                        startTime.set(year, month, day, 9, 0); //set start time
                        endTime = Calendar.getInstance();
                        endTime.set(year, month, day, 13, 0); //set end time
                        WeekViewEvent eventAM = new WeekViewEvent(0, concat, startTime, endTime); //create week view event object
                        eventAM.setColor(getResources().getColor(R.color.event_color_01)); //set colour to blue
                        eventAM.setId(booking.getInstallID()); //set id to install id
                        thisSchedulesList.add(eventAM); //add event to list

                    }
                    //Otherwise set booking start time to 2pm and end time to 6pm if scheduled in the PM
                    else {

                        startTime = Calendar.getInstance();
                        startTime.set(year, month, day, 14, 0); //set start time
                        endTime = Calendar.getInstance();
                        endTime.set(year, month, day, 18, 0); //set end time
                        WeekViewEvent eventPM = new WeekViewEvent(0, concat, startTime, endTime); //create week view event object
                        eventPM.setColor(getResources().getColor(R.color.event_color_02)); //set colour to red
                        eventPM.setId(booking.getInstallID()); //set id to install id
                        thisSchedulesList.add(eventPM); //add event to list
                    }
                }

            } catch (Exception e) {

                Log.i("Error", e.toString());
            } finally {
                getWeekView().notifyDatasetChanged();
            }

        }

        return thisSchedulesList;
    }
}
