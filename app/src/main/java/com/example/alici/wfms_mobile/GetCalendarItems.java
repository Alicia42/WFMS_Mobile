package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 6/10/17.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alamkanak.weekview.WeekViewEvent;
import com.example.alici.wfms_mobile.apiclient.Event;
import com.example.alici.wfms_mobile.apiclient.MyJsonService;
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

    public ArrayList<WeekViewEvent> schedulesList = new ArrayList<WeekViewEvent>();
    public boolean update = false;

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {


        //stops the application from constantly grabbing data from the database caausing a thread pool exception
        if(schedulesList.isEmpty() || update){
            getSchedules();
            refreshHour();
        }

        // Return only the events that matches newYear and newMonth.
        List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : schedulesList) {
            if (eventMatches(event, newYear, newMonth)) {
                matchedEvents.add(event);
            }
        }

        return matchedEvents;
    }

    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }

    private void refreshHour(){

        update = false; //reset update
        int delay = 60000;// 1 minutes

        Timer timer = new Timer();

        timer.schedule( new TimerTask(){
            public void run() {
                update = true; //allow update
                Log.i("update", "true");
            }
        }, delay);
    }

    private void getSchedules() {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(GetCalendarItems.this, "/getschedules", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        //Schedule schedule = new Schedule();
                        schedulesList = convertSchedules(response);
                        Log.i("List Size", String.valueOf(schedulesList.size()));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("fail" , "onFailure : "+statusCode);
                    }
                });
    }

    public ArrayList<WeekViewEvent> convertSchedules(JSONArray response) {

        ArrayList<Schedule> scheduleArrayList = new ArrayList<Schedule>();
        ArrayList<WeekViewEvent> thisSchedulesList = new ArrayList<WeekViewEvent>();

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
                Calendar endTime = (Calendar) startTime.clone();

                if(schedule.getInstallTime().equals("AM")) {

                    startTime = Calendar.getInstance();
                    startTime.set(year, month, day, 9, 00);
                    endTime = Calendar.getInstance();
                    endTime.set(year, month, day, 13, 00);
                    WeekViewEvent event2 = new WeekViewEvent(0, "hello", startTime, endTime);
                    event2.setColor(getResources().getColor(R.color.event_color_01));
                    thisSchedulesList.add(event2);

                }
                else{

                    startTime = Calendar.getInstance();
                    startTime.set(year, month, day, 14, 00);
                    endTime = Calendar.getInstance();
                    endTime.set(year, month, day, 18, 00);
                    WeekViewEvent event2 = new WeekViewEvent(0, "hello", startTime, endTime);
                    event2.setColor(getResources().getColor(R.color.event_color_02));
                    thisSchedulesList.add(event2);
                }


            } catch (Exception e) {

                Log.i("Error", e.toString());
            }
            finally {
                getWeekView().notifyDatasetChanged();
            }
        }

        return thisSchedulesList;
    }
}
