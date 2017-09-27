package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 23/09/17.
 */

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateTable {

    private Date DateValue;
    private java.util.Date DateValueUtil;

    public DateTable(JSONObject object) {
        try {

            String DateValueString = object.getString("DateValue");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
            try {
                DateValueUtil = sdf1.parse(DateValueString);
            }
            catch (java.text.ParseException e){
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

}
