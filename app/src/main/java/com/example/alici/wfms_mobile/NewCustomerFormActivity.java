package com.example.alici.wfms_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

import android.os.AsyncTask;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

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
    private Button createCustomerBtn;

    public URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer_form);

        registerViews();
    }


    private void registerViews() {

        firstName = (EditText) findViewById(R.id.fNameTxtBx);
        // TextWatcher would let us check validation error on the fly
        firstName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(firstName);
                CustomerFormValidation.isFirstName(firstName, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        lastName = (EditText) findViewById(R.id.lNameTxtBx);
        // TextWatcher would let us check validation error on the fly
        lastName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(lastName);
                CustomerFormValidation.isLastName(lastName, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        EmailAddress = (EditText) findViewById(R.id.emailTxtBx);
        EmailAddress.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(EmailAddress);
                CustomerFormValidation.isEmailAddress(EmailAddress, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        HomeNumber = (EditText) findViewById(R.id.hNumberTxtBx);
        HomeNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.isHomePhoneNumber(HomeNumber, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        MobileNumber = (EditText) findViewById(R.id.mNumberTxtBx);
        MobileNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.isMobileNumber(MobileNumber, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        address = (EditText) findViewById(R.id.addressTxtBx);
        address.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(address);
                CustomerFormValidation.isAddress(address, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        suburb = (EditText) findViewById(R.id.suburbTxtBx);
        suburb.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(suburb);
                CustomerFormValidation.isSuburb(suburb, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        areaCode = (EditText) findViewById(R.id.areaCodeTxtBx);
        areaCode.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(areaCode);
                CustomerFormValidation.isAreaCode(areaCode, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        postalAddress = (EditText) findViewById(R.id.pAddressTxtBx);
        postalAddress.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(postalAddress);
                CustomerFormValidation.isAddress(postalAddress, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        postalSuburb = (EditText) findViewById(R.id.pSuburbTxtBx);
        postalSuburb.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(postalSuburb);
                CustomerFormValidation.isSuburb(postalSuburb, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        postalAreaCode = (EditText) findViewById(R.id.pAreaCodeTxtBx);
        postalAreaCode.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(postalAreaCode);
                CustomerFormValidation.isAreaCode(postalAreaCode, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        sameAddChBx = (CheckBox) findViewById(R.id.sameAddChBx);
        sameAddChBx.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(sameAddChBx.isChecked()){

                    if(CustomerFormValidation.hasText(areaCode) || CustomerFormValidation.hasText(suburb) ||CustomerFormValidation.hasText(address)) {
                        System.out.println("Checked");
                        postalAddress.setText(address.getText());
                        postalSuburb.setText(suburb.getText());
                        postalAreaCode.setText(areaCode.getText());
                    }

                }else{
                    System.out.println("Un-Checked");
                    postalAddress.setText("");
                    postalSuburb.setText("");
                    postalAreaCode.setText("");
                }
            }
        });

        createCustomerBtn = (Button) findViewById(R.id.registerBtn);
        createCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Validation class will check the error and display the error on respective fields
                but it won't resist the form submission, so we need to check again before submit
                 */
                if ( checkValidation () )
                    submitForm();
                else
                    Toast.makeText(NewCustomerFormActivity.this, "Form contains error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void submitForm() {
        // Submit your form here. your form is valid
        Toast.makeText(this, "Submitting form...", Toast.LENGTH_LONG).show();
        PrimeThread p = new PrimeThread(); //create thread for database queries
        p.start(); //start thread
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!CustomerFormValidation.hasText(firstName)) ret = false;
        if (!CustomerFormValidation.isEmailAddress(EmailAddress, true)) ret = false;
        if (!CustomerFormValidation.isFirstName(firstName, true)) ret = false;
        if (!CustomerFormValidation.isLastName(lastName, true)) ret = false;
        /*if(CustomerFormValidation.hasText(HomeNumber) || !CustomerFormValidation.hasText(MobileNumber)){
            ret = false;
        }*/
        if (!CustomerFormValidation.isAddress(address, true)) ret = false;
        if (!CustomerFormValidation.isAddress(postalAddress, true)) ret = false;
        if (!CustomerFormValidation.isSuburb(suburb, true)) ret = false;
        if (!CustomerFormValidation.isSuburb(postalSuburb, true)) ret = false;
        if (!CustomerFormValidation.isAreaCode(areaCode, true)) ret = false;
        if (!CustomerFormValidation.isAreaCode(postalAreaCode, true)) ret = false;

        return ret;
    }

    private class PostCustomer extends AsyncTask<Void, Void, String> {

        Editable fName = firstName.getText();
        Editable lName = lastName.getText();
        Editable pAddress = postalAddress.getText();
        Editable pSuburb = postalSuburb.getText();
        Editable pCode = postalAreaCode.getText();
        Editable hNumber = HomeNumber.getText();
        Editable mNumber = MobileNumber.getText();
        Editable email = EmailAddress.getText();

        @Override
        protected String doInBackground(Void... params) {

            // Of course, you should comment the other CASES when testing one CASE
            // CASE 2: For JSONObject parameter
            String url = "http://10.0.2.2:1997/addcustomer";
            JSONObject jsonBody;
            String requestBody;
            HttpURLConnection urlConnection = null;
            try {
                jsonBody = new JSONObject();
                jsonBody.put("FirstName", fName);
                jsonBody.put("LastName", lName);
                jsonBody.put("PostalAddress", pAddress);
                jsonBody.put("PostalSuburb", pSuburb);
                jsonBody.put("PostalCode", pCode);
                jsonBody.put("Phone", hNumber);
                jsonBody.put("Mobile", mNumber);
                jsonBody.put("Email", email);
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
            Log.i("Success","Form Submitted");
            getCustomer(email); //find customer ID of new customer with the email address
        }
    }

        class PrimeThread extends Thread {

        public void run() {

            new PostCustomer().execute();

        }
    }

    private void getCustomer(final Editable email) {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        WCHRestClient.get(NewCustomerFormActivity.this, "/getnewcustomer", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        Customer customer = new Customer();
                        customer.getNewCustomer(response, email);
                    }
                });
    }

}
