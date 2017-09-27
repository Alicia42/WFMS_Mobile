package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 23/09/17.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimeTable {

    private String TimeID;

    public TimeTable(JSONObject object) {
        try {
            this.TimeID = object.getString("TimeID");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTimeID() {
        return TimeID;
    }

    public void setTimeID(String timeID) {
        TimeID = timeID;
    }

    public TimeTable(String TimeID) {
        this.TimeID = TimeID;

    }

    public TimeTable(){}

    public void getTimes(JSONArray response) {
        ArrayList<TimeTable> timeTableArrayList = new ArrayList<TimeTable>();

        for (int i = 0; i < response.length(); i++) {
            try {
                timeTableArrayList.add(new TimeTable(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (TimeTable timeTable : timeTableArrayList) {

            try {
                Log.i("Time ID", timeTable.getTimeID());
            }
            catch (Exception e){

                Log.i("Error","Field is null");
            }
        }
    }
}
