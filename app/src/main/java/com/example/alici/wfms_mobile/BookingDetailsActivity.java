package com.example.alici.wfms_mobile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

import android.widget.CompoundButton;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;
import android.widget.ArrayAdapter;

/**
 * Created by Libby Jennings on 14/10/17.
 * Description: Activity class for displaying a selected bookings details
 */

public class BookingDetailsActivity extends AppCompatActivity {

    //global class variables
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
    public boolean isHomePhone = false;
    public boolean isMobilePhone = false;
    private static final int REQUEST_PHONE_CALL = 1;
    public String phoneSelected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        //set title bar
        Activity activity = BookingDetailsActivity.this;
        activity.setTitle("Selected Booking Details");

        //get install id from calendar
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        installID = bundle.getString("installID");

        invoiceNum = (TextView) findViewById(R.id.invoiceNumTxtBx);
        invoiceNum.setText(installID);

        //convert id to int
        try {
            installIDInt = Integer.parseInt(installID);
        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        //initialise text views, checkboxes, edit texts and buttons
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

        //get booking details
        getBookings();

        //set on click listener for submit button
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                //get text from note field
                note = String.valueOf(installerNote.getText());

                //check that note field isn't empty
                if (note.isEmpty()) {
                    Context context = getApplicationContext();
                    CharSequence text = "Please enter a note";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                //check that note field isn't over it's maximum capacity for db
                if (note.length() > 255) {
                    //display toast message
                    Context context = getApplicationContext();
                    CharSequence text = "Note is too big, maximum is 255 characters";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                //Add note to database if correct
                if (!note.isEmpty() && note.length() < 255) {

                    Log.i("length:", String.valueOf(note.length()));

                    //check for internet connection
                    if (haveNetworkConnection()) {

                        PostInstallerNote(); //post note to server
                        PostInstallComplete(); //post installation not complete (if nothing checked)

                        //un-check complete checkbox and re-enable it
                        if (!installComplete) {
                            completed.setEnabled(true);
                            completed.setChecked(false);
                        }
                    }
                    //no internet connection
                    else {
                        Context context = getApplicationContext();
                        CharSequence text = "No Internet Connection - Please connect and try again";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
            }
        });

    }

    //Method for requesting permission to call someone from the app
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    //Check that mobile or home phone button was clicked and parse that number in when allowed calling from user
                    if (phoneSelected.equals("home")) {
                        intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + homePhoneNumber.getText()));
                    }
                    if (phoneSelected.equals("mobile")) {
                        intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobilePhoneNumber.getText()));
                    }

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(intent);
                    }
                }
            }
        }
    }

    //Method for checking wifi and mobile network connection
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            //check wifi connection
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true; //wifi connection is there
            //check mobile network connection
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true; //mobile network connection is there
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    //Method for getting booking details from server
    private void getBookings() {

        //loading wheel just in case it takes long to grab data (use to be slow)
        load();

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        //async request for getting sever data
        WCHRestClient.get(BookingDetailsActivity.this, "/getbookingdetails", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        //create new booking object
                        Booking booking = new Booking();
                        ArrayList<Booking> bookingArrayList;
                        //assign to result of method findBookingObj in Booking class
                        bookingArrayList = booking.findBookingObj(response, installIDInt);

                        //loop through the array and assign details to text views
                        for (Booking thisBooking : bookingArrayList) {
                            //get installation status (true or false)
                            installComplete = thisBooking.isInstallComplete();
                            //get installer notes
                            prevInstallerNote = thisBooking.getInstallerNote();
                            //get stock list string
                            stockList = thisBooking.getStockList();
                            //set on click listener for tool list button
                            toolList.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {

                                    //call method to convert stock list string into a list
                                    stockList(stockList);
                                }
                            });
                            //get note to installer
                            String installerNoteString = thisBooking.getNoteToInstaller();

                            //check if there is a note to an installer
                            if (installerNoteString.isEmpty()) {
                                //set text view message
                                noteToInstaller.setText("No notes");
                            }
                            //set text view to note
                            else {
                                noteToInstaller.setText(installerNoteString);
                            }

                            //Check if there is an installer note
                            if (!prevInstallerNote.isEmpty() && !prevInstallerNote.equals("NULL")) {
                                //set textview to installer note
                                installerNote.setText(prevInstallerNote);

                                //if there is a note and installation isn't complete, set checkbox to checked (true)
                                if (!installComplete) {
                                    uncomplete.setChecked(true);
                                }
                            }

                            //set complete checkbox to result from db (true / false)
                            completed.setChecked(installComplete);

                            //disable checkbox if completion status is true
                            if (installComplete) {
                                completed.setEnabled(false);
                            }

                            //set complete checkbox on check listener
                            completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                                     @Override
                                                                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                                         //check for internet connection
                                                                         if (haveNetworkConnection()) {
                                                                             //check if completion is checked already
                                                                             if (isChecked) {
                                                                                 //update completion status to true
                                                                                 installComplete = true;
                                                                                 PostInstallComplete(); //post status
                                                                                 completed.setEnabled(false); //disable checkbox
                                                                                 uncomplete.setChecked(false); //uncheck uncomplete checkbox
                                                                             } else {
                                                                                 completed.setChecked(false); //uncheck checkbox
                                                                             }
                                                                         }
                                                                         //No internet connection
                                                                         else {
                                                                             completed.setChecked(false); //uncheck checkbox if no internet connection
                                                                             //display message
                                                                             Context context = getApplicationContext();
                                                                             CharSequence text = "No Internet Connection - Please connect and try again";
                                                                             int duration = Toast.LENGTH_LONG;

                                                                             Toast toast = Toast.makeText(context, text, duration);
                                                                             toast.show();
                                                                         }
                                                                     }
                                                                 }
                            );
                            //set incomplete checkbox on check listener
                            uncomplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                                      @Override
                                                                      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                                          //check for internet connection
                                                                          if (haveNetworkConnection()) {
                                                                              //check if completion is checked already
                                                                              if (isChecked) {
                                                                                  //update completion status to true
                                                                                  installComplete = false;
                                                                                  //display message to add note to update status in db
                                                                                  Context context = getApplicationContext();

                                                                                  CharSequence text = "Please add a note to update completion status";
                                                                                  int duration = Toast.LENGTH_SHORT;

                                                                                  Toast toast = Toast.makeText(context, text, duration);
                                                                                  toast.show();
                                                                              } else {
                                                                                  uncomplete.setChecked(false); //uncheck checkbox
                                                                              }
                                                                          }
                                                                          //No internet connection
                                                                          else {
                                                                              uncomplete.setChecked(false); //uncheck checkbox if no internet connection
                                                                              //display message
                                                                              Context context = getApplicationContext();
                                                                              CharSequence text = "No Internet Connection - Please connect and try again";
                                                                              int duration = Toast.LENGTH_LONG;

                                                                              Toast toast = Toast.makeText(context, text, duration);
                                                                              toast.show();
                                                                          }
                                                                      }
                                                                  }
                            );

                            String siteAddress = "";
                            //Concatenate site address with site suburb and add with google navigation format
                            address.setText(thisBooking.getSiteAddress() + ", " + thisBooking.getSiteSuburb());
                            siteAddress = "google.navigation:q=" + thisBooking.getSiteAddress() + "+ Auckland";

                            final String finalSiteAddress = siteAddress; //set final variable
                            //set on click listener for address button
                            addressButton.setOnClickListener(new View.OnClickListener() {
                                //@Override
                                public void onClick(View v) {
                                    //open google maps app with site address
                                    Uri gmmIntentUri = Uri.parse(finalSiteAddress);
                                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                    mapIntent.setPackage("com.google.android.apps.maps");
                                    startActivity(mapIntent);
                                }
                            });

                            //Get and concatenate customers first and last name and set textviews text
                            customerFirstLastName.setText(thisBooking.getFirstName() + " " + thisBooking.getLastName());
                            //get and set email address textview text
                            emailAddress.setText(thisBooking.getEmail());

                            //check that home phone isn't null
                            if (!thisBooking.getPhone().equals("NULL")) {
                                //get and set home phone text view
                                homePhoneNumber.setText(thisBooking.getPhone());
                                isHomePhone = true; //home phone isn't null
                            }
                            //Home phone number is empty
                            if (thisBooking.getPhone().equals("NULL")) {
                                isHomePhone = false;
                            }
                            //check that mobile phone isn't null
                            if (!thisBooking.getMobile().equals("NULL")) {
                                //get and set mobile phone text view
                                mobilePhoneNumber.setText(thisBooking.getMobile());
                                isMobilePhone = true; //mobile phone isn't null
                            }
                            //Mobile phone is empty
                            if (thisBooking.getPhone().equals("NULL")) {
                                isMobilePhone = true;
                            }

                            //Set home phone button on click listener
                            homePhone.setOnClickListener(new View.OnClickListener() {
                                //@Override
                                public void onClick(View v) {

                                    phoneSelected = "home"; //home phone selected

                                    //Check that this device can be called from (e.g not tablet)
                                    TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                                    assert tm != null;

                                    if (tm.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
                                        //No calling functionality, display error message
                                        Context context = getApplicationContext();
                                        CharSequence text = "No calling functionality on this device";
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();

                                    }
                                    //Device is callable
                                    else {

                                        //check that there is a home phone number
                                        if (isHomePhone) {

                                            //Check for permission to call number, call if permission allowed
                                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + homePhoneNumber.getText()));

                                            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                if (ContextCompat.checkSelfPermission(BookingDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(BookingDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                                                } else {
                                                    startActivity(intent);
                                                }
                                            } else {
                                                startActivity(intent);
                                            }
                                        }
                                        //No home phone number
                                        else {
                                            //display error message
                                            Context context = getApplicationContext();

                                            CharSequence text = "No Home Phone Number";
                                            int duration = Toast.LENGTH_SHORT;

                                            Toast toast = Toast.makeText(context, text, duration);
                                            toast.show();
                                        }
                                    }
                                }
                            });

                            //Set mobile phone button on click listener
                            mobilePhone.setOnClickListener(new View.OnClickListener() {
                                //@Override
                                public void onClick(View v) {

                                    phoneSelected = "mobile"; //mobile phone selected

                                    //Check that this device can be called from (e.g not tablet)
                                    TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                                    assert tm != null;

                                    if (tm.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
                                        //No calling functionality, display error message
                                        Context context = getApplicationContext();
                                        CharSequence text = "No calling functionality on this device";
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();

                                    }
                                    //Device is callable
                                    else {

                                        //check that there is a mobile phone number
                                        if (isMobilePhone) {

                                            //Check for permission to call number, call if permission allowed
                                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobilePhoneNumber.getText()));

                                            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                if (ContextCompat.checkSelfPermission(BookingDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(BookingDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                                                } else {
                                                    startActivity(intent);
                                                }
                                            } else {
                                                startActivity(intent);
                                            }
                                        }
                                        //No mobile phone number
                                        else {
                                            //display error message
                                            Context context = getApplicationContext();

                                            CharSequence text = "No Mobile Phone Number";
                                            int duration = Toast.LENGTH_SHORT;

                                            Toast toast = Toast.makeText(context, text, duration);
                                            toast.show();
                                        }
                                    }
                                }
                            });

                            //get and set fire type textview text
                            fireType.setText(thisBooking.getFireType());
                            //get and set install type description text view text
                            installType.setText(thisBooking.getInstallDescription());
                        }
                    }
                });

        bv.setBoo(); //stop loading, process is finished
    }

    //Method for converting string to tool list in alert dialog pop-up
    private void stockList(String stockList) {

        //Check that given string isn't empty
        if (!stockList.isEmpty()) {

            //create alert dialog
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(BookingDetailsActivity.this);
            builderSingle.setTitle("Tool List"); //set title

            //Set array adaptor for layout of list
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(BookingDetailsActivity.this, android.R.layout.select_dialog_singlechoice);
            //split string when , appears and add to string list
            String[] items = stockList.split(",");
            //go through list and add each item to the adaptor
            for (String item : items) {
                arrayAdapter.add(item);
            }
            //set button to OK
            builderSingle.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builderSingle.show();
        }
        //String is empty, show error message
        else {

            Context context = getApplicationContext();
            CharSequence text = "No tool list available";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    //Method for updating installation status to complete in db
    public void PostInstallComplete() {

        RequestParams params = new RequestParams();
        params.put("InstallComplete", installComplete); //post install complete
        params.put("InstallID", installIDInt); //post install ID
        params.setUseJsonStreamer(true);

        //async post request
        WCHRestClient.post("/addInstallComplete", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //display success message
                Context context = getApplicationContext();
                CharSequence text = "Installation Status Updated";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }

    //Method for adding installer note to db
    public void PostInstallerNote() {

        RequestParams params = new RequestParams();
        params.put("InstallerNote", note); //post note
        params.put("InstallID", installIDInt); //post install id as an int
        params.setUseJsonStreamer(true);

        //async post request
        WCHRestClient.post("/addinstallernote", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //display alert dialog pop-up of success message
                Context context = getApplicationContext();
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(BookingDetailsActivity.this, R.style.myDialog);
                } else {
                    builder = new AlertDialog.Builder(context);
                }
                builder.setTitle("Installer Note Added") //set pop-up title
                        //set pop-up button as OK
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }

    //Method for showing loading progress wheel
    private void load() {

        //create progress wheel
        final ProgressDialog dialog = new ProgressDialog(BookingDetailsActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //show spinner
        dialog.setMessage("Loading. Please wait..."); //show message
        dialog.setIndeterminate(true);
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
