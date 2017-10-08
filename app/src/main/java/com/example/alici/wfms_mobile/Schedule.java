package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 25/09/17.
 */

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Schedule {

    private java.util.Date DateValueUtil;

    private java.sql.Date InstallDate;
    private String InstallTime;
    private int UserID;
    private int InstallID;
    public ArrayList<WeekViewEvent> schedulesList = new ArrayList<WeekViewEvent>();

    public Schedule(JSONObject object) {
        try {

            String InstallDateString = object.getString("InstallDate");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                DateValueUtil = sdf1.parse(InstallDateString);
            } catch (java.text.ParseException e) {
                Log.i("Error", "couldn't parse date");
                Log.i("Error", e.getMessage());
            }
            java.sql.Date DateValueSql = new java.sql.Date(DateValueUtil.getTime());

            this.InstallDate = DateValueSql;
            this.InstallTime = object.getString("InstallTime");
            this.UserID = object.getInt("UserID");
            this.InstallID = object.getInt("InstallID");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public java.sql.Date getInstallDate() {
        return InstallDate;
    }

    public void setInstallDate(java.sql.Date installDate) {
        InstallDate = installDate;
    }

    public String getInstallTime() {
        return InstallTime;
    }

    public void setInstallTime(String installTime) {
        InstallTime = installTime;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getInstallID() {
        return InstallID;
    }

    public void setInstallID(int installID) {
        InstallID = installID;
    }

    public Schedule(java.sql.Date installDate, String installTime, int userID, int installID) {
        InstallDate = installDate;
        InstallTime = installTime;
        UserID = userID;
        InstallID = installID;
    }

    public Schedule() {
    }

    public void getSchedules(JSONArray response) {

        ArrayList<Schedule> scheduleArrayList = new ArrayList<Schedule>();

        for (int i = 0; i < response.length(); i++) {
            try {
                scheduleArrayList.add(new Schedule(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Schedule schedule : scheduleArrayList) {

            try {
                Log.i("Install Date", String.valueOf(schedule.getInstallDate()));
                /*Log.i("Install Time", schedule.getInstallTime());
                String toStringUser = String.valueOf(schedule.getUserID());
                Log.i("User ID", toStringUser);
                String toStringInstall = String.valueOf(schedule.getInstallID());
                Log.i("Install ID", toStringInstall);*/
                java.sql.Date dat = schedule.getInstallDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dat);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int year = cal.get(Calendar.YEAR);

                Log.i("Day", String.valueOf(day));
                Log.i("Month", String.valueOf(month));
                Log.i("Year", String.valueOf(year));
            } catch (Exception e) {

                Log.i("Error", "Field is null");
            }
        }
    }

    public ArrayList<WeekViewEvent> convertSchedules(JSONArray response) {

        ArrayList<Schedule> scheduleArrayList = new ArrayList<Schedule>();

        for (int i = 0; i < response.length(); i++) {
            try {
                scheduleArrayList.add(new Schedule(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Schedule schedule : scheduleArrayList) {

            try {

                java.sql.Date dat = schedule.getInstallDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dat);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int year = cal.get(Calendar.YEAR);

                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, 3);
                startTime.set(Calendar.MINUTE, 0);
                startTime.set(Calendar.DAY_OF_MONTH, day);
                startTime.set(Calendar.MONTH, month + 1);
                startTime.set(Calendar.YEAR, year);
                Calendar endTime = (Calendar) startTime.clone();
                endTime.add(Calendar.HOUR, 1);
                endTime.set(Calendar.MONTH, month + 1);
                endTime.set(Calendar.DAY_OF_MONTH, day);
                WeekViewEvent weekViewEvent = new WeekViewEvent(10, "hello", startTime, endTime);
                weekViewEvent.setColor(Color.red(R.color.colorPrimary));
                schedulesList.add(weekViewEvent);

                Log.i("Color", String.valueOf(weekViewEvent.getColor()));

            } catch (Exception e) {

                Log.i("Error", e.toString());
            }
        }


        return schedulesList;
    }

    /*@SuppressLint("SimpleDateFormat")
    public WeekViewEvent toEvent() {

        // Parse time.
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date start = new Date();
        Date end = new Date();
        /*try {
            start = sdf.parse(getStartTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            end = sdf.parse(getEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        /*java.sql.Date dat = getInstallDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dat);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);*/

        // Initialize start and end time.
        /*Calendar now = Calendar.getInstance();
        Calendar startTime = (Calendar) now.clone();
        startTime.set(Calendar.HOUR_OF_DAY, 17);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.YEAR, year);
        startTime.set(Calendar.MONTH, month);
        startTime.set(Calendar.DAY_OF_MONTH, day);
        Calendar endTime = (Calendar) startTime.clone();
        startTime.set(Calendar.HOUR_OF_DAY, 20);
        startTime.set(Calendar.MINUTE, 0);
        endTime.set(Calendar.YEAR, year);
        endTime.set(Calendar.MONTH, month);
        endTime.set(Calendar.DAY_OF_MONTH, day);

        // Create an week view event.
        WeekViewEvent weekViewEvent = new WeekViewEvent();
        weekViewEvent.setName("hello");
        weekViewEvent.setStartTime(startTime);
        weekViewEvent.setEndTime(endTime);
        weekViewEvent.setColor(Color.MAGENTA);*/

        /*Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.DAY_OF_MONTH, day);
        startTime.set(Calendar.MONTH, month + 1);
        startTime.set(Calendar.YEAR, year);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, month + 1);
        endTime.set(Calendar.DAY_OF_MONTH, day);
        WeekViewEvent weekViewEvent = new WeekViewEvent(1, "hello", startTime, endTime);
        weekViewEvent.setColor(Color.rgb(255, 0, 0));
        //events.add(event);

        Log.i("here", "hooray");
        Log.i("time", String.valueOf(weekViewEvent.getStartTime()));

        return weekViewEvent;
    }*/
}
