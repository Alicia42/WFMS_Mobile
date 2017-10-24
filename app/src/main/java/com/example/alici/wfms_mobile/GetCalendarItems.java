package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 6/10/17.
 */

import android.app.ProgressDialog;
import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;
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
    public ArrayList<Integer> saleIDArray = new ArrayList<Integer>();
    ArrayList<String> customerNameArray = new ArrayList<String>();
    public int customerNameArraySize = 0;
    public boolean update = false;

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {


        //stops the application from constantly grabbing data from the database caausing a thread pool exception
        if (schedulesList.isEmpty() || update) {
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

    private void refreshHour() {

        update = false; //reset update
        int delay = 60000;// 1 minute

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
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
                        Log.d("fail", "onFailure : " + statusCode);
                    }
                });
    }

    private void getInstalls(final ArrayList installIDArray) {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(GetCalendarItems.this, "/getinstalls", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        /*Install install = new Install();
                        saleIDArray = (ArrayList<Integer>) install.findSaleID(response);
                        //saleIDArray.add(saleID);
                        getSales(saleIDArray);*/

                        ArrayList<Integer> saleIDArray = new ArrayList<Integer>();
                        int saleID = 0;
                        Install install = new Install();
                        for (Object installID : installIDArray) {
                            Install newInstall = new Install();
                            newInstall.setInstallID((Integer) installID);
                            saleID = install.findSaleID(response, newInstall.getInstallID());
                            saleIDArray.add(saleID);

                            getSales(saleIDArray);
                            //Log.i("Customer size", String.valueOf(customerIDArray.size()));
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("fail", "onFailure : " + statusCode);
                    }
                });
    }

    private void getSales(final ArrayList saleIDArray) {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(GetCalendarItems.this, "/getsales", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        ArrayList<Integer> customerIDArray = new ArrayList<Integer>();
                        int customerID = 0;
                        Sale sale = new Sale();
                        for (Object saleID : saleIDArray) {
                            Sale newSale = new Sale();
                            newSale.setSaleID((Integer) saleID);
                            customerID = sale.findCustomerID(response, newSale.getSaleID());
                            customerIDArray.add(customerID);
                            //Log.i("Customer size", String.valueOf(customerIDArray.size()));
                        }

                        getCustomers(customerIDArray);
                    }
                });
    }

    private void getCustomers(final ArrayList customerIDArray) {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(GetCalendarItems.this, "/getcustomers", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        customerNameArray.clear();
                        Customer customer = new Customer();
                        String customerName = "";
                        for (Object customerID : customerIDArray) {
                            Customer newCustomer = new Customer();
                            newCustomer.setCustomerID((Integer) customerID);
                            customerName = customer.findCustomer(response, newCustomer.getCustomerID());
                            customerNameArray.add(customerName);
                            customerNameArraySize = customerNameArray.size();
                            //Log.i("Customer Name size", String.valueOf(customerNameArraySize));
                        }
                    }
                });
    }


    public ArrayList<WeekViewEvent> convertSchedules(JSONArray response) {

        ArrayList<Schedule> scheduleArrayList = new ArrayList<Schedule>();
        ArrayList<WeekViewEvent> thisSchedulesList = new ArrayList<WeekViewEvent>();
        ArrayList<Integer> installIDArray = new ArrayList<Integer>();

        for (int i = 0; i < response.length(); i++) {
            try {
                scheduleArrayList.add(new Schedule(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Schedule schedule : scheduleArrayList) {
            if (schedule.getUserID() == MainActivity.User.userID){
                Install install = new Install();
                install.setInstallID(schedule.getInstallID());
                installIDArray.add(install.getInstallID());
            }
        }

        int count = 0;
        String concat = "";

        for (Schedule schedule : scheduleArrayList) {

            try {

                MainActivity.User user = new MainActivity.User();

                if(schedule.getUserID() == MainActivity.User.userID) {

                    getInstalls(installIDArray);

                    concat = customerNameArray.get(count) + "'s House";
                    java.sql.Date dat = schedule.getInstallDate();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dat);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);

                    Calendar startTime = Calendar.getInstance();
                    Calendar endTime = (Calendar) startTime.clone();

                    if (schedule.getInstallTime().equals("AM")) {

                        startTime = Calendar.getInstance();
                        startTime.set(year, month, day, 9, 00);
                        endTime = Calendar.getInstance();
                        endTime.set(year, month, day, 13, 00);
                        WeekViewEvent event2 = new WeekViewEvent(0, concat, startTime, endTime);
                        event2.setColor(getResources().getColor(R.color.event_color_01));
                        event2.setId(installIDArray.get(count));
                        thisSchedulesList.add(event2);

                    } else {

                        startTime = Calendar.getInstance();
                        startTime.set(year, month, day, 14, 00);
                        endTime = Calendar.getInstance();
                        endTime.set(year, month, day, 18, 00);
                        WeekViewEvent event2 = new WeekViewEvent(0, concat, startTime, endTime);
                        event2.setColor(getResources().getColor(R.color.event_color_02));
                        event2.setId(installIDArray.get(count));
                        thisSchedulesList.add(event2);
                    }
                    count++;
                }
                else{
                    Log.i("Error", String.valueOf(user.userID));
                }

            } catch (Exception e) {

                Log.i("Error", e.toString());
            } finally {
                getWeekView().notifyDatasetChanged();
            }

        }

        return thisSchedulesList;
    }
}
