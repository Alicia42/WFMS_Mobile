package com.example.alici.wfms_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.app.ProgressDialog;
import java.util.ArrayList;

import org.json.JSONArray;
import com.loopj.android.http.JsonHttpResponseHandler;
import java.util.List;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public BooVariable bv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);


        //getCustomers();
        //getDates();
        //getTimes();
        //getAuthentication();
        //getUserAccounts();
        //getInstallTypes();
        //getSales();
        //getInstalls();
        //getFires();
        //getFollowUpComments();
        //getSchedules();

    }

    public void onClick(View v) {
        Intent intent = new Intent(this, NewCustomerFormActivity.class);
        startActivity(intent);
    }

    /*class PrimeThread extends Thread {

        public void run() {

            bv.setBoo(true);

        }
    }*/

    private void load(){

        //create progress wheel
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        //PrimeThread p = new PrimeThread(); //create thread for database queries
        //p.start(); //start thread

        bv = new BooVariable(); //instantiate boolean variable
        //set listener for variable changes
        bv.setListener(new BooVariable.ChangeListener() {
            @Override
            //stop thread when variable changes
            public void onChange() {
                Log.i("tag", "thread done");
                dialog.cancel(); //stop progress wheel
            }
        });
    }

    private void getCustomers() {

        load();

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(MainActivity.this, "/getcustomers", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        Customer customer = new Customer();
                        customer.getCustomers(response);
                    }
                });

        bv.setBoo(true);
    }


    private void getDates() {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(MainActivity.this, "/getdatetable", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        DateTable dateTable = new DateTable();
                        dateTable.getDates(response);
                    }
                });

        bv.setBoo(true);
    }

    private void getTimes() {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(MainActivity.this, "/gettimetable", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        TimeTable timeTable = new TimeTable();
                        timeTable.getTimes(response);
                    }
                });

        bv.setBoo(true);
    }

    private void getAuthentication() {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(MainActivity.this, "/getauthentication", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        Authenticate authenticate = new Authenticate();
                        authenticate.getAuthentications(response);
                    }
                });
        bv.setBoo(true);
    }

    private void getUserAccounts() {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(MainActivity.this, "/getuseraccounts", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        User_Account user_account = new User_Account();
                       user_account.getUserAccounts(response);
                    }
                });
        bv.setBoo(true);
    }

    private void getInstallTypes() {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(MainActivity.this, "/getinstalltypes", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        Install_Type install_type = new Install_Type();
                        install_type.getInstallTypes(response);
                    }
                });
        bv.setBoo(true);
    }

    private void getSales() {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(MainActivity.this, "/getsales", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        Sale sale = new Sale();
                        sale.getSales(response);
                    }
                });

        bv.setBoo(true);
    }

    private void getInstalls() {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(MainActivity.this, "/getinstalls", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        Install install = new Install();
                        install.getInstalls(response);
                    }
                });

        bv.setBoo(true);
    }

    private void getFires() {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(MainActivity.this, "/getfires", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        Fire fire = new Fire();
                        fire.getFires(response);
                    }
                });
        bv.setBoo(true);
    }

    private void getFollowUpComments() {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(MainActivity.this, "/getfollowupcomments", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        Follow_Up_Comment follow_up_comment = new Follow_Up_Comment();
                        follow_up_comment.getFollowUpComments(response);
                    }
                });

        bv.setBoo(true);
    }

    private void getSchedules() {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(MainActivity.this, "/getschedules", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        Schedule schedule = new Schedule();
                        schedule.getSchedules(response);
                    }
                });

        bv.setBoo(true);
    }

}
