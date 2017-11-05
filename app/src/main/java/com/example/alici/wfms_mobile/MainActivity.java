package com.example.alici.wfms_mobile;

/*
 * Created by Alicia Craig on 11/9/17.
 * Description: Main activity class that is displayed on app-launch. Starts with login functionality
 */


import android.content.Context;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.widget.EditText;
import android.widget.Toast;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public BooVariable bv;
    private EditText username;
    private EditText passwordTxtBx;
    public String md5Hash;
    public String usernameString;

    //Sets the User ID on login and can be used globally in the app
    static class User{

        static int userID;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialise edit texts and button
        username = (EditText) findViewById(R.id.usernameTxtBx);
        passwordTxtBx = (EditText) findViewById(R.id.passwordTxtBx);
        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this); //set button on click listener

    }

    //Method for hashing the password entered to MD5 format
    public void computeMD5Hash(String password)
    {

        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder MD5Hash = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                StringBuilder h = new StringBuilder(Integer.toHexString(0xFF & aMessageDigest));
                while (h.length() < 2)
                    h.insert(0, "0");
                MD5Hash.append(h);
            }

            md5Hash = MD5Hash.toString(); //set global variable of hashed password

        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
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

    //Method for when login button is clicked
    public void onClick(View v) {

        //Check that there is internet connection
        if(haveNetworkConnection()){
            usernameString = String.valueOf(username.getText()); //get username from text view
            computeMD5Hash(passwordTxtBx.getText().toString() + usernameString); //hash password with username
            getUserAccounts(); //get user accounts
        }
        //No internet connection
        else {
            //Display error message
            Context context = getApplicationContext();
            CharSequence text = "No Internet Connection - Please connect to login";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    //Method for showing loading progress wheel
    private void load() {

        //create progress wheel
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
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

    //Method for getting user account details
    private void getUserAccounts() {

        load(); //loading wheel just in case it takes long to grab data (use to be slow)

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        //async request for getting sever data
        WCHRestClient.get(MainActivity.this, "/getuseraccounts", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        //create new user account object
                        User_Account user_account = new User_Account();

                        //set user id to id returned from method in user account class
                        User.userID = user_account.getUserID(response, md5Hash, usernameString);

                        //Check that login details are correct
                        if(user_account.checkLoginDetails(response, md5Hash, usernameString)){

                            //get role type of logged in user
                            String roleType = user_account.getRoleType(response, md5Hash, usernameString);

                            //If salesperson or shop logs in, show customer digital entry form
                            if(roleType.equals("Salesperson") || roleType.equals("Shop")) {
                                Intent intent = new Intent(MainActivity.this, NewCustomerFormActivity.class);
                                startActivity(intent); //start intent
                                username.setText(""); //reset username text view
                                passwordTxtBx.setText(""); //reset password text view
                            }
                            //If installer logs in, show scheduled bookings form
                            if(roleType.equals("Installer")){
                                Intent intent = new Intent(MainActivity.this, GetCalendarItems.class);
                                startActivity(intent);
                                username.setText("");
                                passwordTxtBx.setText("");
                            }
                            //Don't let Admin login, show error message
                            if(roleType.equals("Admin")) {
                                Context context = getApplicationContext();
                                CharSequence text = "Please sign in as either a Salesperson, Shop or Installer";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        }
                        //Otherwise, login details are incorrect
                        else {
                            //show error  message
                            Context context = getApplicationContext();
                            CharSequence text = "Incorrect Login Details";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }

                    }
                });
        bv.setBoo(); //stop loading, process is finished
    }

}
