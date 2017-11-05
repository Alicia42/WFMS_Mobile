package com.example.alici.wfms_mobile;

/*
 * Created by Alicia Craig on 11/9/17.
 * Description: New customer digital entry form activity for registering customers into the system
 */

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

import org.json.JSONArray;

import java.net.URL;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Build;
import android.content.DialogInterface;
import android.content.Intent;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

import android.app.Activity;


public class NewCustomerFormActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText EmailAddress;
    private EditText HomeNumber;
    private EditText MobileNumber;
    private EditText address;
    private EditText suburb;
    private EditText areaCode;
    private CheckBox sameAddChBx;
    private EditText postalAddress;
    private EditText postalSuburb;
    private EditText postalAreaCode;
    public Boolean customerExists = false;

    public URL url;
    public int customerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer_form);
        Activity activity = NewCustomerFormActivity.this;
        activity.setTitle("Register New Customer");
        InitialiseComponents();
    }

    //Method for initialising GUI components
    private void InitialiseComponents() {

        //set up listener for checking first name validation
        firstName = (EditText) findViewById(R.id.fNameTxtBx);
        firstName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(firstName); //check not empty
                CustomerFormValidation.isFirstName(firstName); //check correct input
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //set up listener for checking last name validation
        lastName = (EditText) findViewById(R.id.lNameTxtBx);
        lastName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(lastName); //check not empty
                CustomerFormValidation.isLastName(lastName); //check correct input
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //set up listener for checking email address validation
        EmailAddress = (EditText) findViewById(R.id.emailTxtBx);
        EmailAddress.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(EmailAddress); //check not empty
                CustomerFormValidation.isEmailAddress(EmailAddress); //check correct input
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //set up listener for checking home phone number validation
        HomeNumber = (EditText) findViewById(R.id.hNumberTxtBx);
        HomeNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.isHomePhoneNumber(HomeNumber); //check correct input
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //set up listener for checking mobile phone number validation
        MobileNumber = (EditText) findViewById(R.id.mNumberTxtBx);
        MobileNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.isMobileNumber(MobileNumber); //check correct input
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //set up listener for checking address validation
        address = (EditText) findViewById(R.id.addressTxtBx);
        address.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(address); //check not empty
                CustomerFormValidation.isAddress(address); //check correct input
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //set up listener for checking suburb validation
        suburb = (EditText) findViewById(R.id.suburbTxtBx);
        suburb.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(suburb); //check not empty
                CustomerFormValidation.isSuburb(suburb); //check correct input
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //set up listener for checking area code validation
        areaCode = (EditText) findViewById(R.id.areaCodeTxtBx);
        areaCode.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(areaCode); //check not empty
                CustomerFormValidation.isAreaCode(areaCode); //check correct input
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //set up listener for checking postal address validation
        postalAddress = (EditText) findViewById(R.id.pAddressTxtBx);
        postalAddress.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(postalAddress); //check not empty
                CustomerFormValidation.isAddress(postalAddress); //check correct input
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //set up listener for checking postal suburb validation
        postalSuburb = (EditText) findViewById(R.id.pSuburbTxtBx);
        postalSuburb.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(postalSuburb); //check not empty
                CustomerFormValidation.isSuburb(postalSuburb); //check correct input
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //set up listener for checking postal area code validation
        postalAreaCode = (EditText) findViewById(R.id.pAreaCodeTxtBx);
        postalAreaCode.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(postalAreaCode); //check not empty
                CustomerFormValidation.isAreaCode(postalAreaCode); //check correct input
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //set up listener for when same as postal checkbox is clicked
        sameAddChBx = (CheckBox) findViewById(R.id.sameAddChBx);
        sameAddChBx.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (sameAddChBx.isChecked()) {

                    //Check that all required fields aren't empty
                    if (CustomerFormValidation.hasText(areaCode) || CustomerFormValidation.hasText(suburb) || CustomerFormValidation.hasText(address)) {
                        postalAddress.setText(address.getText()); //set postal address text
                        postalSuburb.setText(suburb.getText());  //set postal suburb text
                        postalAreaCode.setText(areaCode.getText());  //set postal area code text
                    }

                }
                //Uncheck box and reset fields
                else {
                    postalAddress.setText(""); //set to empty
                    postalSuburb.setText(""); //set to empty
                    postalAreaCode.setText(""); //set to empty
                }
            }
        });

        Button createCustomerBtn = (Button) findViewById(R.id.registerBtn);
        createCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Validation class will check the error and display the error on respective fields
                but it won't resist the form submission, so we need to check again before submit
                 */
                if (checkValidation()) {

                    //check for internet connection
                    if (!haveNetworkConnection()) {
                        //display error message
                        Context context = getApplicationContext();
                        CharSequence text = "No Internet Connection - Please connect and try again";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    //has internet connection,submit form
                    else {
                        submitForm();
                    }
                }
                //Errors in form, display message
                else
                    Toast.makeText(NewCustomerFormActivity.this, "Please fix errors where marked", Toast.LENGTH_LONG).show();
            }
        });

        //set on click listener for cancel button
        Button cancelBtn = (Button) findViewById(R.id.CancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Create alert dialog pop-up to confirm cancellation
                Context context = getApplicationContext();
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(NewCustomerFormActivity.this, R.style.myDialog);
                } else {
                    builder = new AlertDialog.Builder(context);
                }
                builder.setTitle("Warning") //set title
                        .setMessage("Are you sure you want to cancel?") //confirm cancellation
                        //YES button - close form
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(NewCustomerFormActivity.this, MainActivity.class);
                                startActivity(intent); //open login page
                                finish(); //finish current activity
                            }
                        })
                        //NO button - close message and do nothing
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
    }

    //Method for checking input validation before on submission of form
    private boolean checkValidation() {
        boolean result = true;

        if (!CustomerFormValidation.hasText(EmailAddress)) result = false;
        if (!CustomerFormValidation.hasText(firstName)) result = false;
        if (!CustomerFormValidation.hasText(lastName)) result = false;
        if (!CustomerFormValidation.hasText(address)) result = false;
        if (!CustomerFormValidation.hasText(postalAddress)) result = false;
        if (!CustomerFormValidation.hasText(suburb)) result = false;
        if (!CustomerFormValidation.hasText(postalSuburb)) result = false;
        if (!CustomerFormValidation.hasText(areaCode)) result = false;
        if (!CustomerFormValidation.hasText(postalAreaCode)) result = false;

        if (!CustomerFormValidation.isEmailAddress(EmailAddress)) result = false;
        if (!CustomerFormValidation.isFirstName(firstName)) result = false;
        if (!CustomerFormValidation.isLastName(lastName)) result = false;
        if (!CustomerFormValidation.isAddress(address)) result = false;
        if (!CustomerFormValidation.isAddress(postalAddress)) result = false;
        if (!CustomerFormValidation.isSuburb(suburb)) result = false;
        if (!CustomerFormValidation.isSuburb(postalSuburb)) result = false;
        if (!CustomerFormValidation.isAreaCode(areaCode)) result = false;
        if (!CustomerFormValidation.isAreaCode(postalAreaCode)) result = false;

        return result;
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

    //Method for submitting form
    private void submitForm() {
        // Submit your form here. your form is valid
        Toast.makeText(this, "Checking details...", Toast.LENGTH_LONG).show();

        //check if customer exists
        getCustomers();
    }

    //Method for getting customer details
    private void getCustomers() {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        //async request for getting sever data
        WCHRestClient.get(NewCustomerFormActivity.this, "/getcustomers", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        //check if customer exists
                        customerExists = findCustomer(response);

                        //Add customer to db if the customer doesn't exist
                        if (!customerExists) {
                            PostCustomer();
                        }
                        //Customer exists, don't add
                        else {

                            //Create alert dialog pop-up
                            Context context = getApplicationContext();
                            AlertDialog.Builder builder;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                builder = new AlertDialog.Builder(NewCustomerFormActivity.this, R.style.myDialog);
                            } else {
                                builder = new AlertDialog.Builder(context);
                            }
                            builder.setTitle("Customer Already Exists") //set title
                                    //set message
                                    .setMessage("This customer already exists, please use the desktop application to update details if necessary")
                                    //set OK button - do nothing
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .show();
                        }
                    }
                });
    }

    //Method for checking if a customer exists in the db
    public boolean findCustomer(JSONArray response) {

        ArrayList<Customer> customerArray = new ArrayList<Customer>();

        //get JSON objects and add to array list
        for (int i = 0; i < response.length(); i++) {
            try {
                customerArray.add(new Customer(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //get the text from the edit text boxes
        String fname = String.valueOf(firstName.getText());
        String lname = String.valueOf(lastName.getText());
        String email = String.valueOf(EmailAddress.getText());

        //If a customer in the array matches the first name, last name and email address, return true (does exist)
        for (Customer customer : customerArray) {

            if (customer.getFirstName().equals(fname) && customer.getLastName().equals(lname) && customer.getEmail().equals(email)) {

                return true;
            }
        }

        //otherwise, return false (doesn't exist)
        return false;

    }

    //Method for posting Customer details to the server
    public void PostCustomer() {

        //set variables from edit text boxes
        Editable fName = firstName.getText();
        Editable lName = lastName.getText();
        Editable pAddress = postalAddress.getText();
        Editable pSuburb = postalSuburb.getText();
        Editable pCode = postalAreaCode.getText();
        Editable hNumber = HomeNumber.getText();
        Editable mNumber = MobileNumber.getText();
        Editable email = EmailAddress.getText();
        Editable sAddress = address.getText();
        Editable sSuburb = suburb.getText();

        //Create param to post with customer details
        RequestParams params = new RequestParams();
        params.put("FirstName", fName);
        params.put("LastName", lName);
        params.put("PostalAddress", pAddress);
        params.put("PostalSuburb", pSuburb);
        params.put("PostalCode", pCode);
        params.put("Phone", hNumber);
        params.put("Mobile", mNumber);
        params.put("Email", email);
        params.put("SiteAddress", sAddress);
        params.put("SiteSuburb", sSuburb);
        params.setUseJsonStreamer(true);

        //async post request
        WCHRestClient.post("/addcustomersale", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                //Creare alert dialog pop-up to display success message
                Context context = getApplicationContext();
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(NewCustomerFormActivity.this, R.style.myDialog);
                } else {
                    builder = new AlertDialog.Builder(context);
                }
                builder.setTitle("Registration Complete!") //set title
                        .setMessage("Thank you for registering") //set message
                        //set OK button
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(NewCustomerFormActivity.this, NewCustomerFormActivity.class);
                                startActivity(intent); //restart activity to reset form
                                finish();
                            }
                        })
                        //.setIcon(android.R.drawable.d)
                        .show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });

    }

}
