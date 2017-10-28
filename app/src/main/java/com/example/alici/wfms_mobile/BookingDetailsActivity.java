package com.example.alici.wfms_mobile;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import java.util.Objects;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

import android.widget.CompoundButton;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;
import android.widget.ArrayAdapter;

public class BookingDetailsActivity extends AppCompatActivity {

    ArrayList<String> customerNameArray = new ArrayList<String>();
    ArrayList<Customer> customerDetailsList = new ArrayList<Customer>();
    ArrayList<Sale> saleSiteAddress = new ArrayList<Sale>();
    public TextView customerFirstLastName;
    public TextView invoiceNum;
    public TextView address;
    public TextView homePhoneNumber;
    public TextView mobilePhoneNumber;
    public TextView emailAddress;
    public TextView fireType;
    public TextView installType;
    public TextView noteToInstaller;
    public TextView toolList;
    public CheckBox completed;
    public CheckBox uncomplete;
    public ImageView addressButton;
    public ImageView homePhone;
    public ImageView mobilePhone;
    public EditText installerNote;
    public Button submit;
    public String installID = "";
    public int installIDInt = 0;
    public int saleID = 0;
    public String fireID = "";
    public int customerID = 0;
    public int installTypeID = 0;
    public boolean installComplete = false;
    public BooVariable bv;
    public String note = "";
    public String stockList = "";
    public String prevInstallerNote = "";

    private static final int REQUEST_PHONE_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        Activity activity = BookingDetailsActivity.this;
        activity.setTitle("Selected Booking Details");

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
        fireType = (TextView) findViewById(R.id.fireTypeTxtView);
        installType = (TextView) findViewById(R.id.installTypeTxtBx);
        noteToInstaller = (TextView) findViewById(R.id.noteToInstallerTxtView);
        toolList = (TextView) findViewById(R.id.ToolListTxtBx);
        completed = (CheckBox) findViewById(R.id.completeCheckBx);
        uncomplete = (CheckBox) findViewById(R.id.unCompleteChkBx);

        addressButton = (ImageView) findViewById(R.id.addressImgVw);
        homePhone = (ImageView) findViewById(R.id.homePhoneImgVw);
        mobilePhone = (ImageView) findViewById(R.id.mobilePhoneImgVw);

        installerNote = (EditText) findViewById(R.id.notesEditTxt);
        submit = (Button) findViewById(R.id.SubmitBtn);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                note = String.valueOf(installerNote.getText());

                if (note.isEmpty()) {
                    Context context = getApplicationContext();
                    CharSequence text = "Please enter a note";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                if(note.length() > 255){
                    Context context = getApplicationContext();
                    CharSequence text = "Note is too big, maximum is 255 characters";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {

                    Log.i("length:" , String.valueOf(note.length()));

                    if(haveNetworkConnection()) {

                        new PostInstallerNote().execute();
                        new PostInstallComplete().execute();

                        if (!installComplete) {
                            completed.setEnabled(true);
                            completed.setChecked(false);
                        }
                    }
                }
            }
        });

        getBookings();

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

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void getBookings() {

        load();

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(BookingDetailsActivity.this, "/getbookingdetails", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        Booking booking = new Booking();
                        ArrayList<Booking> bookingArrayList = new ArrayList<Booking>();
                        bookingArrayList = booking.findBookingObj(response, installIDInt);

                        for (Booking thisBooking : bookingArrayList){
                            //Log.i("Booking", String.valueOf(thisBooking.getInstallID()));
                            saleID = thisBooking.getSaleID();
                            fireID = thisBooking.getFireID();
                            installComplete = thisBooking.isInstallComplete();
                            prevInstallerNote = thisBooking.getInstallerNote();

                            toolList.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {

                                    stockList(stockList);
                                }
                            });

                            stockList = thisBooking.getStockList();

                            String installerNoteString = thisBooking.getNoteToInstaller();

                            if(installerNoteString.equals(null)){
                                noteToInstaller.setText("No notes");
                            }
                            else {
                                noteToInstaller.setText(installerNoteString);
                            }

                            if(!prevInstallerNote.isEmpty() && !prevInstallerNote.equals("NULL")){
                                installerNote.setText(prevInstallerNote);

                                if(!installComplete) {
                                    uncomplete.setChecked(true);
                                }
                            }

                            completed.setChecked(installComplete);

                            if(installComplete){
                                completed.setEnabled(false);
                            }

                            completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                                     @Override
                                                                     public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                                                                         if(isChecked && haveNetworkConnection()){
                                                                             installComplete = true;
                                                                             new PostInstallComplete().execute();
                                                                             completed.setEnabled(false);
                                                                             uncomplete.setChecked(false);
                                                                         }
                                                                         else {
                                                                             completed.setChecked(false);
                                                                         }
                                                                     }
                                                                 }
                            );

                            uncomplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                                      @Override
                                                                      public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                                                                          if(isChecked && haveNetworkConnection()){
                                                                              installComplete = false;
                                                                              Context context = getApplicationContext();
                                                                              CharSequence text = "Please add note to update completion status";
                                                                              int duration = Toast.LENGTH_LONG;

                                                                              Toast toast = Toast.makeText(context, text, duration);
                                                                              toast.show();
                                                                          }
                                                                          else {
                                                                              uncomplete.setChecked(false);
                                                                          }
                                                                      }
                                                                  }
                            );

                            customerID = thisBooking.getCustomerID();
                            installTypeID = thisBooking.getInstallTypeID();
                            //getCustomers();

                            String siteAddress = "";
                            address.setText(thisBooking.getSiteAddress() + ", " + thisBooking.getSiteSuburb());
                            siteAddress = "google.navigation:q=" + thisBooking.getSiteAddress() + "+ Auckland";

                            final String finalSiteAddress = siteAddress;
                            addressButton.setOnClickListener(new View.OnClickListener() {
                                //@Override
                                public void onClick(View v) {
                                    Uri gmmIntentUri = Uri.parse(finalSiteAddress);
                                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                    mapIntent.setPackage("com.google.android.apps.maps");
                                    startActivity(mapIntent);
                                }
                            });


                                customerFirstLastName.setText(thisBooking.getFirstName() + " " + thisBooking.getLastName());

                                if(!thisBooking.getPhone().equals("NULL")){
                                    homePhoneNumber.setText(thisBooking.getPhone());
                                }
                                if (thisBooking.getPhone().equals("NULL")){
                                    homePhoneNumber.setText("No Home Phone Number");
                                }
                                if(!thisBooking.getMobile().equals("NULL")){
                                    mobilePhoneNumber.setText(thisBooking.getMobile());
                                }
                                if (thisBooking.getPhone().equals("NULL")){
                                    homePhoneNumber.setText("No Mobile Phone Number");
                                }

                                emailAddress.setText(thisBooking.getEmail());

                            homePhone.setOnClickListener(new View.OnClickListener() {
                                //@Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + homePhoneNumber.getText()));

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

                            mobilePhone.setOnClickListener(new View.OnClickListener() {
                                //@Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobilePhoneNumber.getText()));

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

                            String thisFireType = thisBooking.getFireType();
                            fireType.setText(thisFireType);

                            String installDescription = "";
                            installDescription = thisBooking.getInstallDescription();
                            installType.setText(installDescription);
                        }
                    }
                });

        bv.setBoo(true);
    }

    private void stockList(String stockList){

        if(!stockList.isEmpty()) {

            List<String> stockListArray = new ArrayList<String>();

            Context context = getApplicationContext();

            AlertDialog.Builder builderSingle = new AlertDialog.Builder(BookingDetailsActivity.this);
            builderSingle.setTitle("Tool List");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(BookingDetailsActivity.this, android.R.layout.select_dialog_singlechoice);
            String[] items = stockList.split(",");
            for (String item : items) {
                arrayAdapter.add(item);
            }

            builderSingle.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    /*String strName = arrayAdapter.getItem(which);
                    AlertDialog.Builder builderInner = new AlertDialog.Builder(BookingDetailsActivity.this);
                    builderInner.setMessage(strName);
                    builderInner.setTitle("Stocklist");
                    builderInner.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,int which) {
                            dialog.dismiss();
                        }
                    });
                    builderInner.show();*/
                }
            });
            builderSingle.show();
        }
        else {

            Context context = getApplicationContext();
            CharSequence text = "No tool list available";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    private class PostInstallComplete extends AsyncTask<Void, Void, String>  {

        @Override
        protected String doInBackground(Void... params) {

            String url = "http://wchdomain.duckdns.org:1997/addInstallComplete";
            //uncomment the next line to add install complete from cloud web service
            //String url = "http://52.65.97.218:1997/addInstallComplete";
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

            CharSequence text = "Installation Status Updated";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    private class PostInstallerNote extends AsyncTask<Void, Void, String>  {

        @Override
        protected String doInBackground(Void... params) {

            String url = "http://wchdomain.duckdns.org:1997/addinstallernote";
            //uncomment the next line to add install complete from cloud web service
            //String url = "http://52.65.97.218:1997/addinstallernote";
            JSONObject jsonBody;
            String requestBody;
            HttpURLConnection urlConnection = null;
            try {
                jsonBody = new JSONObject();
                jsonBody.put("InstallerNote", note);
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
            Log.i("Success","Installer Note Added");

            Context context = getApplicationContext();

            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(BookingDetailsActivity.this, R.style.myDialog);
            } else {
                builder = new AlertDialog.Builder(context);
            }
            builder.setTitle("Installer Note Added")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    //.setIcon(android.R.drawable.d)
                    .show();
        }
    }

    private void load(){

        //create progress wheel
        final ProgressDialog dialog = new ProgressDialog(BookingDetailsActivity.this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

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

}
