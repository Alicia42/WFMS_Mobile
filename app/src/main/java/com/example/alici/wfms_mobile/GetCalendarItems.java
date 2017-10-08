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
        return schedulesList;
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

                        Schedule schedule = new Schedule();
                        schedulesList = schedule.convertSchedules(response);
                        Log.i("List Size", String.valueOf(schedulesList.size()));

                    }
                });
    }
}
