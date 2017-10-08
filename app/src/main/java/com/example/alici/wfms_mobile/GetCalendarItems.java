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
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GetCalendarItems extends Calendar_Base_Activity {

    public ArrayList<WeekViewEvent> schedulesList = new ArrayList<WeekViewEvent>();

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {

        getSchedules();
        getWeekView().notifyDatasetChanged();

        /*List <WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
        // Return only the events that matches newYear and newMonth.
        for (WeekViewEvent schedule : schedulesList) {
                matchedEvents.add(schedule);
        }
        Log.i("matched events", String.valueOf(matchedEvents.size()));*/

        for (WeekViewEvent weekViewEvent : schedulesList) {
            Log.i("Time", String.valueOf(weekViewEvent.getStartTime()));
        }


        return schedulesList;

        /*List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, 9);
        startTime.set(Calendar.YEAR, newYear);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, 9);
        WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);

        Calendar startTime = Calendar.getInstance();
        Calendar endTime = (Calendar) startTime.clone();


        startTime = Calendar.getInstance();
        startTime.set(2017, 9, 8, 6, 00);
        endTime = Calendar.getInstance();
        endTime.set(2017, 9, 8, 9, 00);
        WeekViewEvent event2 = new WeekViewEvent(0,"00kjbhjbhjbjbhj",startTime, endTime);
        event2.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event2);

        Log.i("Date", String.valueOf(event2.getStartTime()));


        return events;*/
    }

   /* private boolean eventMatches(WeekViewEvent event, int year, int month) {
        Log.i("Day:", String.valueOf(event.getStartTime().get(Calendar.DAY_OF_MONTH)));
        Log.i("Month:", String.valueOf(event.getStartTime().get(Calendar.MONTH)));
        Log.i("Year:", String.valueOf(event.getStartTime().get(Calendar.YEAR)));
        //Log.i("Time:", String.valueOf(event.getStartTime()));
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }*/

    /*public void convertSchedules(List<Schedule> schedules, Response response) {
        for (Schedule schedule : schedules) {
            this.schedulesList.add(schedule.toEvent());
        }
        getWeekView().notifyDatasetChanged();
        getSchedules();
    }*/

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

                /*Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, 3);
                startTime.set(Calendar.MINUTE, 0);
                startTime.set(Calendar.DAY_OF_MONTH, day);
                startTime.set(Calendar.MONTH, month - 1);
                startTime.set(Calendar.YEAR, year);
                Calendar endTime = (Calendar) startTime.clone();
                endTime.add(Calendar.HOUR, 1);
                endTime.set(Calendar.MONTH, month - 1);
                endTime.set(Calendar.DAY_OF_MONTH, day );
                WeekViewEvent weekViewEvent = new WeekViewEvent(1, "hello", startTime, endTime);
                weekViewEvent.setColor(getResources().getColor(R.color.event_color_01));
                thisSchedulesList.add(weekViewEvent);*/

                Calendar startTime = Calendar.getInstance();
                Calendar endTime = (Calendar) startTime.clone();


                startTime = Calendar.getInstance();
                startTime.set(year, month, day, 6, 00);
                endTime = Calendar.getInstance();
                endTime.set(year, month, day, 9, 00);
                WeekViewEvent event2 = new WeekViewEvent(0,"hello",startTime, endTime);
                event2.setColor(getResources().getColor(R.color.event_color_01));
                thisSchedulesList.add(event2);

                //Log.i("Color", String.valueOf(event2.getColor()));
                Log.i("Time", String.valueOf(event2.getStartTime()));

            } catch (Exception e) {

                Log.i("Error", e.toString());
            }
        }


        return thisSchedulesList;
    }
}
