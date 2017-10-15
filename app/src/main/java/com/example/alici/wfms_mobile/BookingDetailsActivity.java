package com.example.alici.wfms_mobile;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

import android.widget.CompoundButton;
import android.view.View;
import android.content.Intent;

public class BookingDetailsActivity extends AppCompatActivity {

    ArrayList<String> customerNameArray = new ArrayList<String>();
    ArrayList<Customer> customerDetailsList = new ArrayList<Customer>();
    public TextView customerFirstLastName;
    public TextView invoiceNum;
    public TextView address;
    public TextView homePhoneNumber;
    public TextView mobilePhoneNumber;
    public TextView emailAddress;
    public TextView installType;
    public CheckBox completed;
    public CheckBox uncomplete;
    public ImageView homePhone;
    public String installID = "";
    public int installIDInt = 0;
    public int saleID = 0;
    public int customerID = 0;
    public int installTypeID = 0;
    public boolean installComplete = false;

    private static final int REQUEST_PHONE_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        /*Bundle b = getIntent().getExtras();
        custName = b.getString("customerName");*/

        Bundle bundle = getIntent().getExtras();
        installID = bundle.getString("installID");

        invoiceNum = (TextView) findViewById(R.id.invoiceNumTxtBx);
        invoiceNum.setText(installID);

        try {
            installIDInt = Integer.parseInt(installID);
        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        customerFirstLastName = (TextView) findViewById(R.id.custNameTxtBx);
        address = (TextView) findViewById(R.id.addressTxtBx);
        homePhoneNumber = (TextView) findViewById(R.id.homePhoneNumTxtBx);
        mobilePhoneNumber = (TextView) findViewById(R.id.mobilePhoneNumberTxtBx);
        emailAddress = (TextView) findViewById(R.id.emailTxtBx);
        installType = (TextView) findViewById(R.id.installTypeTxtBx);
        completed = (CheckBox) findViewById(R.id.completeCheckBx);
        uncomplete = (CheckBox) findViewById(R.id.unCompleteChkBx);

        homePhone = (ImageView) findViewById(R.id.homePhoneImgVw);

        homePhone.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+918511812660"));

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(BookingDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(BookingDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                    }
                    else
                    {
                        startActivity(intent);
                    }
                }
                else
                {
                    startActivity(intent);
                }
            }
        });

        getInstalls();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL : {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + homePhoneNumber.getText()));

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(intent);
                    }
                }
            }
        }
    }

    private void getInstalls() {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(BookingDetailsActivity.this, "/getinstalls", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        Install install = new Install();
                        saleID = install.findSaleID(response, installIDInt);
                        installComplete = install.findInstallCompletion(response, installIDInt);
                        completed.setChecked(installComplete);

                        if(installComplete){
                            completed.setEnabled(false);
                        }

                        completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                                 @Override
                                                                 public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                                                                     if(isChecked){
                                                                         installComplete = true;
                                                                         new PostInstallComplete().execute();
                                                                         completed.setEnabled(false);
                                                                         uncomplete.setChecked(false);
                                                                     }
                                                                 }
                                                             }
                        );

                        uncomplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                                  @Override
                                                                  public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                                                                      if(isChecked){
                                                                          installComplete = false;
                                                                          new PostInstallComplete().execute();
                                                                          completed.setEnabled(true);
                                                                          completed.setChecked(false);
                                                                          Log.i("submit", "please write note");
                                                                      }
                                                                  }
                                                              }
                        );
                        getSales();
                    }
                });
    }

    private void getSales() {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(BookingDetailsActivity.this, "/getsales", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        Sale sale = new Sale();
                        customerID = sale.findCustomerID(response, saleID);
                        //Log.i("customerID", String.valueOf(customerID));
                        installTypeID = sale.findInstallTypeID(response, saleID);
                        getCustomers();
                    }
                });
    }

    private void getCustomers() {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(BookingDetailsActivity.this, "/getcustomers", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        Customer customer = new Customer();
                        customerDetailsList = customer.findCustomerDetails(response, customerID);

                        for (Customer thisCustomer : customerDetailsList) {

                            customerFirstLastName.setText(thisCustomer.getFirstName() + " " + thisCustomer.getLastName());
                            //Log.i("address", thisCustomer.getPostalAddress());
                            address.setText(thisCustomer.getPostalAddress() + ", " + thisCustomer.getPostalSuburb() + ", " + thisCustomer.getPostalCode());
                            if(!thisCustomer.getPhone().equals("NULL")){
                                homePhoneNumber.setText(thisCustomer.getPhone());
                            }
                            if (thisCustomer.getPhone().equals("NULL")){
                                homePhoneNumber.setText("No Home Phone Number");
                            }
                            if(!thisCustomer.getMobile().equals("NULL")){
                                mobilePhoneNumber.setText(thisCustomer.getMobile());
                            }
                            if (thisCustomer.getPhone().equals("NULL")){
                                homePhoneNumber.setText("No Mobile Phone Number");
                            }

                            emailAddress.setText(thisCustomer.getEmail());
                        }

                        getInstallTypes();

                    }
                });
    }

    private void getInstallTypes() {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(BookingDetailsActivity.this, "/getinstalltypes", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        String installDescription = "";
                        Install_Type install_type = new Install_Type();
                        installDescription = install_type.findInstallType(response, installTypeID);
                        installType.setText(installDescription);
                    }
                });
    }

    private class PostInstallComplete extends AsyncTask<Void, Void, String>  {

        @Override
        protected String doInBackground(Void... params) {

            String url = "http://10.0.2.2:1997/addInstallComplete";
            JSONObject jsonBody;
            String requestBody;
            HttpURLConnection urlConnection = null;
            try {
                jsonBody = new JSONObject();
                jsonBody.put("InstallComplete", installComplete);
                jsonBody.put("InstallID", installIDInt);
                //jsonBody.put("ReesCode", "null");
                requestBody = Utils.buildPostParameters(jsonBody);
                urlConnection = (HttpURLConnection) Utils.makeRequest("POST", url, null, "application/json", requestBody);
                InputStream inputStream;
                // get stream
                if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                    inputStream = urlConnection.getInputStream();
                } else {
                    inputStream = urlConnection.getErrorStream();
                }
                // parse stream
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp, response = "";
                while ((temp = bufferedReader.readLine()) != null) {
                    response += temp;
                }
                return response;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.i("Success","Install Complete");

            Context context = getApplicationContext();

            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(BookingDetailsActivity.this, R.style.myDialog);
            } else {
                builder = new AlertDialog.Builder(context);
            }
            builder.setTitle("Installation Status Updated")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    //.setIcon(android.R.drawable.d)
                    .show();
        }
    }

}
