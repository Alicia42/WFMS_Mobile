package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 23/09/17.
 */

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

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
}
