package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 6/10/17.
 */

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

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


        //stops the application from constantly grabbing data from the database caausing a thread pool exception
        if (bookingsList.isEmpty() || update) {
            getBookings();
            refreshHour();
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

    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }

    private void refreshHour() {

        update = false; //reset update
        int delay = 60000;// 1 minute

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {
                update = true; //allow update
                Log.i("update", "true");
            }
        }, delay);
    }

    private void getBookings() {

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mWifi.isConnected()) {

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
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    //.setIcon(android.R.drawable.d)
                    .show();
        }
        else {

            List<Header> headers = new ArrayList<Header>();
            headers.add(new BasicHeader("Accept", "application/json"));

            WCHRestClient.get(GetCalendarItems.this, "/getbookingdetails", headers.toArray(new Header[headers.size()]),
                    null, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                            bookingsList = convertSchedules(response);

                        }
                    });
        }

    }

    public ArrayList<WeekViewEvent> convertSchedules(JSONArray response) {

        ArrayList<Booking> bookingArrayList = new ArrayList<Booking>();
        ArrayList<WeekViewEvent> thisSchedulesList = new ArrayList<WeekViewEvent>();

        for (int i = 0; i < response.length(); i++) {
            try {
                bookingArrayList.add(new Booking(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String concat = "";

        for (Booking booking : bookingArrayList) {

            try {

                MainActivity.User user = new MainActivity.User();

                if(booking.getUserID() == MainActivity.User.userID) {

                    concat = booking.getFirstName() + "'s House";
                    java.sql.Date dat = booking.getInstallDate();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dat);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);

                    Calendar startTime = Calendar.getInstance();
                    Calendar endTime = (Calendar) startTime.clone();

                    if (booking.getInstallTime().equals("AM")) {

                        startTime = Calendar.getInstance();
                        startTime.set(year, month, day, 9, 00);
                        endTime = Calendar.getInstance();
                        endTime.set(year, month, day, 13, 00);
                        WeekViewEvent event2 = new WeekViewEvent(0, concat, startTime, endTime);
                        event2.setColor(getResources().getColor(R.color.event_color_01));
                        event2.setId(booking.getInstallID());
                        thisSchedulesList.add(event2);

                    } else {

                        startTime = Calendar.getInstance();
                        startTime.set(year, month, day, 14, 00);
                        endTime = Calendar.getInstance();
                        endTime.set(year, month, day, 18, 00);
                        WeekViewEvent event2 = new WeekViewEvent(0, concat, startTime, endTime);
                        event2.setColor(getResources().getColor(R.color.event_color_02));
                        event2.setId(booking.getInstallID());
                        thisSchedulesList.add(event2);
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
