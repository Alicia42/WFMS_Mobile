package com.example.alici.wfms_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

public class BookingDetailsActivity extends AppCompatActivity {

    ArrayList<String> customerNameArray = new ArrayList<String>();
    ArrayList<Customer> customerDetailsList = new ArrayList<Customer>();
    public TextView customerFirstLastName;
    public TextView invoiceNum;
    public TextView address;
    public TextView phoneNumber;
    public String installID = "";
    public int installIDInt = 0;
    public int saleID = 0;
    public int customerID = 0;

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
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        getInstalls();

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

                        customerFirstLastName = (TextView) findViewById(R.id.custNameTxtBx);
                        address = (TextView) findViewById(R.id.addressTxtBx);
                        phoneNumber = (TextView) findViewById(R.id.phoneNumberTxtBx);

                        for (Customer thisCustomer : customerDetailsList) {

                            customerFirstLastName.setText(thisCustomer.getFirstName() + " " + thisCustomer.getLastName());
                            //Log.i("address", thisCustomer.getPostalAddress());
                            address.setText(thisCustomer.getPostalAddress() + ", " + thisCustomer.getPostalSuburb() + ", " + thisCustomer.getPostalCode());
                            phoneNumber.setText(thisCustomer.getPhone());
                        }

                    }
                });
    }

}
