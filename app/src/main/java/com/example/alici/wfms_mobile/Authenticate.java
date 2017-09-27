package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 23/09/17.
 */

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

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
}
