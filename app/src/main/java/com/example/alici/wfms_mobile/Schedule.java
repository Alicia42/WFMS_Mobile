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

        MainActivity.User user = new MainActivity.User();

        for (Schedule schedule : scheduleArrayList) {

            try {

                if(schedule.getUserID() == user.userID) {
                    Log.i("UserID", String.valueOf(schedule.getUserID()));
                    Log.i("Install Date", String.valueOf(schedule.getInstallDate()));
                    java.sql.Date dat = schedule.getInstallDate();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dat);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);

                    Log.i("Day", String.valueOf(day));
                    Log.i("Month", String.valueOf(month));
                    Log.i("Year", String.valueOf(year));
                }
                else{
                    Log.i("Error", "Didn't match");
                }
            } catch (Exception e) {

                Log.i("Error", "Field is null");
            }
        }
    }
}
