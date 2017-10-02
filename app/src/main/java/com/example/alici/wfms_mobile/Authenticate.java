package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 23/09/17.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Authenticate {

    private int AuthenticationID;
    private String PasswordHash;

    public Authenticate(JSONObject object) {
        try {
            this.AuthenticationID = object.getInt("AuthenticationID");
            this.PasswordHash = object.getString("PasswordHash");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Authenticate(int AuthenticationID, String PasswordHash) {
        this.AuthenticationID = AuthenticationID;
        this.PasswordHash = PasswordHash;

    }

    public int getAuthenticationID() {
        return AuthenticationID;
    }

    public void setAuthenticationID(int authenticationID) {
        AuthenticationID = authenticationID;
    }

    public String getPasswordHash() {
        return PasswordHash;
    }

    public void setPasswordHash(String passwordHash) {
        PasswordHash = passwordHash;
    }

    public Authenticate(){}

    public void getAuthentication(JSONArray response, String hash){
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
                if(authenticate.getPasswordHash().equals(hash)) {
                    Log.i("Success", "Matched");
                    String toString = String.valueOf(authenticate.getAuthenticationID());
                    Log.i("Authentication ID", toString);
                    Log.i("Password Hash", authenticate.getPasswordHash());
                }
                else{
                    Log.i("Error", "Didn't match");
                }
            }
            catch (Exception e){

                Log.i("Error","Field is null");
            }
        }
    }
}
