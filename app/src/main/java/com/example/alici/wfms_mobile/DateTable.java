package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 23/09/17.
 */

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

public class DateTable {

    private Date DateValue;
    private java.util.Date DateValueUtil;

    public DateTable(JSONObject object) {
        try {

            String DateValueString = object.getString("DateValue");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                DateValueUtil = sdf1.parse(DateValueString);
            } catch (java.text.ParseException e) {
                Log.i("Error", "couldn't parse date");
                Log.i("Error", e.getMessage());
            }
            java.sql.Date DateValueSql = new java.sql.Date(DateValueUtil.getTime());
            this.DateValue = DateValueSql;

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    public DateTable(Date DateValue) {
        this.DateValue = DateValue;
    }

    public Date getDateValue() {
        return DateValue;
    }

    public void setDateValue(Date dateValue) {
        DateValue = dateValue;
    }

    public DateTable() {
    }

    public void getDates(JSONArray response) {


        ArrayList<DateTable> dateTableArrayList = new ArrayList<DateTable>();

        for (int i = 0; i < response.length(); i++) {
            try {
                dateTableArrayList.add(new DateTable(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (DateTable dateTable : dateTableArrayList) {

            try {
                Log.i("Date Value", dateTable.getDateValue().toString());
            } catch (Exception e) {

                Log.i("Error", "Field is null");
            }
        }
    }

}
