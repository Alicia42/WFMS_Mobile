package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 25/09/17.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Schedule {

    private java.util.Date DateValueUtil;

    private java.sql.Date InstallDate;
    private String InstallTime;
    private int UserID;
    private int InstallID;

    public Schedule(JSONObject object) {
        try {

            String InstallDateString = object.getString("InstallDate");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
            try {
                DateValueUtil = sdf1.parse(InstallDateString);
            }
            catch (java.text.ParseException e){
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

    public Schedule(){}

    public void getSchedules(JSONArray response){

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
                Log.i("Install Date", schedule.getInstallDate().toString());
                Log.i("Install Time", schedule.getInstallTime());
                String toStringUser = String.valueOf(schedule.getUserID());
                Log.i("User ID", toStringUser);
                String toStringInstall = String.valueOf(schedule.getUserID());
                Log.i("Sale ID", toStringInstall);
            }
            catch (Exception e){

                Log.i("Error","Field is null");
            }
        }
    }
}
