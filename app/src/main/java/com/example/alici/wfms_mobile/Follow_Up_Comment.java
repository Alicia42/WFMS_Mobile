package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 25/09/17.
 */

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Follow_Up_Comment {

    private java.util.Date TimeValueUtil;

    private int CommentID;
    private int SaleID;
    private String Comment;
    private Time Time_Stamp;

    public Date getTimeValueUtil() {
        return TimeValueUtil;
    }

    public void setTimeValueUtil(Date timeValueUtil) {
        TimeValueUtil = timeValueUtil;
    }

    public int getCommentID() {
        return CommentID;
    }

    public void setCommentID(int commentID) {
        CommentID = commentID;
    }

    public int getSaleID() {
        return SaleID;
    }

    public void setSaleID(int saleID) {
        SaleID = saleID;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public Time getTime_Stamp() {
        return Time_Stamp;
    }

    public void setTime_Stamp(Time time_Stamp) {
        Time_Stamp = time_Stamp;
    }

    public Follow_Up_Comment(int commentID, int saleID, String comment, Time time_Stamp) {
        CommentID = commentID;
        SaleID = saleID;
        Comment = comment;
        Time_Stamp = time_Stamp;
    }

    public Follow_Up_Comment(JSONObject object) {
        try {
            this.CommentID = object.getInt("CommentID");
            this.SaleID = object.getInt("SaleID");
            this.Comment = object.getString("Comment");

            String TimeStampString = object.getString("Time_Stamp");
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss");
            try {
                TimeValueUtil = sdf2.parse(TimeStampString);
            }
            catch (java.text.ParseException e){
                Log.i("Error", "couldn't parse date");
                Log.i("Error", e.getMessage());
            }
            java.sql.Time TimeValueSql = new java.sql.Time(TimeValueUtil.getTime());
            this.Time_Stamp = TimeValueSql;

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
