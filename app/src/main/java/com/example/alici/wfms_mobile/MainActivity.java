package com.example.alici.wfms_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.app.ProgressDialog;
import java.util.ArrayList;
import org.json.JSONException;
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
        getSchedules();

    }

    public void onClick(View v) {
        Intent intent = new Intent(this, newCustomerFormActivity.class);
        startActivity(intent);
    }

    class PrimeThread extends Thread {

        public void run() {

            bv.setBoo(true);

        }
    }

    private void getCustomers() {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(MainActivity.this, "/getcustomers", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        ArrayList<Customer> customerArray = new ArrayList<Customer>();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                customerArray.add(new Customer(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                        for (Customer customer : customerArray) {

                            try {
                                String toString = String.valueOf(customer.getCustomerID());
                                Log.i("customerID", toString);
                                Log.i("first name", customer.getFirstName());
                                Log.i("last name", customer.getLastName());
                                Log.i("postal address", customer.getPostalAddress());
                                Log.i("postal suburb", customer.getPostalSuburb());
                                Log.i("postal code", customer.getPostalCode());
                                Log.i("phone", customer.getPhone());
                                Log.i("mobile", customer.getMobile());
                                Log.i("email", customer.getEmail());
                                Log.i("rees code", customer.getReesCode());
                            }
                            catch (Exception e){

                                Log.i("Error","Field is null");
                            }
                        }
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
                            }
                            catch (Exception e){

                                Log.i("Error","Field is null");
                            }
                        }
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

                        ArrayList<Authenticate> authenticateArrayList = new ArrayList<Authenticate>();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                authenticateArrayList.add(new Authenticate(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        for (Authenticate authenticate : authenticateArrayList) {

                            try {
                                String toString = String.valueOf(authenticate.getAuthenticationID());
                                Log.i("Authentication ID", toString);
                                Log.i("Password Hash", authenticate.getPasswordHash());
                            }
                            catch (Exception e){

                                Log.i("Error","Field is null");
                            }
                        }
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

                        ArrayList<User_Account> user_accountArrayList = new ArrayList<User_Account>();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                user_accountArrayList.add(new User_Account(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        for (User_Account user_account : user_accountArrayList) {

                            try {
                                String toString = String.valueOf(user_account.getAuthenticationID());
                                Log.i("Authentication ID", toString);
                                String toStringUser = String.valueOf(user_account.getUserID());
                                Log.i("UserID", toStringUser);
                                Log.i("first name", user_account.getFirstName());
                                Log.i("last name", user_account.getLastName());
                                Log.i("postal address", user_account.getPostalAddress());
                                Log.i("postal suburb", user_account.getPostalSuburb());
                                Log.i("postal code", user_account.getPostalCode());
                                Log.i("phone", user_account.getPhone());
                                Log.i("mobile", user_account.getMobile());
                                Log.i("email", user_account.getEmail());
                                Log.i("rees number", user_account.getReesNumber());
                                String toStringAccountActive = String.valueOf(user_account.isAccountActive());
                                Log.i("Account Active", toStringAccountActive);
                            }
                            catch (Exception e){

                                Log.i("Error","Field is null");
                            }
                        }
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

                        ArrayList<Install_Type> install_typeArrayList = new ArrayList<Install_Type>();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                install_typeArrayList.add(new Install_Type(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        for (Install_Type install_type : install_typeArrayList) {

                            try {
                                String toString = String.valueOf(install_type.getInstallTypeID());
                                Log.i("InstallType ID", toString);
                                Log.i("InstallTypeDescription", install_type.getInstallDescription());
                                String toStringBase = String.valueOf(install_type.getBasePrice());
                                Log.i("Base Price", toStringBase);
                                Log.i("email from letter", install_type.getEmailFromLetter());
                                Log.i("site check file", install_type.getSiteCheckFile());
                            }
                            catch (Exception e){

                                Log.i("Error","Field is null");
                            }
                        }
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

                        ArrayList<Sale> saleArrayList = new ArrayList<Sale>();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                saleArrayList.add(new Sale(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        for (Sale sale : saleArrayList) {

                            try {
                                String toString = String.valueOf(sale.getSaleID());
                                Log.i("Sale ID", toString);
                                String toStringCust = String.valueOf(sale.getCustomerID());
                                Log.i("Customer ID", toStringCust);
                                String toStringUser = String.valueOf(sale.getUserID());
                                Log.i("User ID", toStringUser);
                                String toStringInstall = String.valueOf(sale.getInstallTypeID());
                                Log.i("Install Type ID", toStringInstall);
                                Log.i("Site Address", sale.getSiteAddress());
                                Log.i("Site Suburb", sale.getSiteSuburb());
                                Log.i("Sale Status", sale.getSaleStatus());
                                Log.i("Fire", sale.getFire());
                                String toStringPrice = String.valueOf(sale.getPrice());
                                Log.i("Price", toStringPrice);
                                String toStringBooked = String.valueOf(sale.isSiteCheckBooked());
                                Log.i("Site Check Booked", toStringBooked);
                                Log.i("Site Check Date", sale.getSiteCheckDate().toString());
                                Log.i("Site Check Time", sale.getSiteCheckTime().toString());
                                String toStringSalesperson = String.valueOf(sale.getSalesPerson());
                                Log.i("Salesperson", toStringSalesperson);
                                Log.i("Estimation Date", sale.getEstimationDate().toString());
                                Log.i("Quote Number", sale.getQuoteNumber());
                                Log.i("Site Check Path", sale.getSiteCheckPath());
                                Log.i("Quote Path", sale.getQuotePath());
                                Log.i("Photo Path", sale.getPhotoPath());
                                Log.i("Follow Up Date", sale.getFollowUpDate().toString());
                            }
                            catch (Exception e){

                                Log.i("Error","Field is null");
                            }
                        }
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

                        ArrayList<Install> installArrayList = new ArrayList<Install>();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                installArrayList.add(new Install(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        for (Install install : installArrayList) {

                            try {
                                String toStringInstall = String.valueOf(install.getInstallID());
                                Log.i("Install ID", toStringInstall);
                                String toString = String.valueOf(install.getSaleID());
                                Log.i("Sale ID", toString);
                                Log.i("Install Status", install.getInstallStatus());
                                Log.i("Invoice Path", install.getInvoicePath());
                                Log.i("Site Check Path", install.getSiteCheckPath());
                                Log.i("Photo Path", install.getPhotoPath());
                                String toStringChecked = String.valueOf(install.isOrderChecked());
                                Log.i("Order Checked", toStringChecked);
                                Log.i("Installer ID", install.getInstallerID());
                                Log.i("Install Date", install.getInstallDate().toString());
                                Log.i("Install Time", install.getInstallTime());
                                String toStringParts = String.valueOf(install.getPartsReady());
                                Log.i("Parts Ready", toStringParts);
                                Log.i("Note to Installer", install.getNoteToInstaller());
                                Log.i("Installer ID", install.getInstallerID());
                                String toStringComplete = String.valueOf(install.isInstallComplete());
                                Log.i("Install Complete", toStringComplete);
                                Log.i("Installer Note", install.getInstallerNote());
                            }
                            catch (Exception e){

                                Log.i("Error","Field is null");
                            }
                        }
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

                        ArrayList<Fire> fireArrayList = new ArrayList<Fire>();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                fireArrayList.add(new Fire(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        for (Fire fire : fireArrayList) {

                            try {
                                Log.i("Fire ID", fire.getFireID());
                                Log.i("Fire Type", fire.getFireType());
                                Log.i("Make", fire.getMake());
                                Log.i("Model", fire.getModel());
                                Log.i("Fuel", fire.getFuel());
                                Log.i("ECAN", fire.getECAN());
                                Log.i("Nelson", fire.getNelson());
                                Log.i("Life", fire.getLife());
                            }
                            catch (Exception e){

                                Log.i("Error","Field is null");
                            }
                        }
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

                        ArrayList<Follow_Up_Comment> follow_up_commentArrayList = new ArrayList<Follow_Up_Comment>();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                follow_up_commentArrayList.add(new Follow_Up_Comment(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        for (Follow_Up_Comment follow_up_comment : follow_up_commentArrayList) {

                            try {
                                String toStringComment = String.valueOf(follow_up_comment.getComment());
                                Log.i("Comment ID", toStringComment);
                                String toStringSale = String.valueOf(follow_up_comment.getSaleID());
                                Log.i("Sale ID", toStringSale);
                                Log.i("Comment", follow_up_comment.getComment());
                                Log.i("Time Stamp", follow_up_comment.getTime_Stamp().toString());
                            }
                            catch (Exception e){

                                Log.i("Error","Field is null");
                            }
                        }
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

                        ArrayList<Schedule> scheduleArrayList = new ArrayList<Schedule>();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                scheduleArrayList.add(new Schedule(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        for (Schedule schedule : scheduleArrayList) {

                            try {
                                Log.i("Install Date", schedule.getInstallDate().toString());
                                Log.i("Install Time", schedule.getInstallTime());
                                String toStringUser = String.valueOf(schedule.getUserID());
                                Log.i("User ID", toStringUser);
                                String toStringInstall = String.valueOf(schedule.getUserID());
                                Log.i("Sale ID", toStringInstall);
                            }
                            catch (Exception e){

                                Log.i("Error","Field is null");
                            }
                        }
                    }
                });

        bv.setBoo(true);
    }

}
