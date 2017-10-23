package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 23/09/17.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class User_Account {

    private int UserID;
    private int AuthenticationID;
    private String UserName;
    private String RoleType;
    private boolean Install;
    private boolean AccountActive;
    private String PasswordHash;

    public User_Account(JSONObject object) {
        try {
            this.UserID = object.getInt("UserID");
            this.AuthenticationID = object.getInt("AuthenticationID");
            this.UserName = object.getString("UserName");
            this.RoleType = object.getString("RoleType");
            this.Install = object.getBoolean("Install");
            this.AccountActive = object.getBoolean("AccountActive");
            this.PasswordHash = object.getString("PasswordHash");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public User_Account(int userID, int authenticationID, String userName, boolean accountActive) {
        UserID = userID;
        AuthenticationID = authenticationID;
        UserName = userName;
        AccountActive = accountActive;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getAuthenticationID() {
        return AuthenticationID;
    }

    public void setAuthenticationID(int authenticationID) {
        AuthenticationID = authenticationID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public boolean isAccountActive() {
        return AccountActive;
    }

    public void setAccountActive(boolean accountActive) {
        AccountActive = accountActive;
    }

    public String getRoleType() {
        return RoleType;
    }

    public void setRoleType(String roleType) {
        RoleType = roleType;
    }

    public boolean isInstall() {
        return Install;
    }

    public void setInstall(boolean install) {
        Install = install;
    }

    public String getPasswordHash() {
        return PasswordHash;
    }

    public void setPasswordHash(String passwordHash) {
        PasswordHash = passwordHash;
    }

    public User_Account(){}

    public boolean checkLoginDetails(JSONArray response, String hash, String username){

        ArrayList<User_Account> user_accountArrayList = new ArrayList<User_Account>();
        boolean didMatch = false;

        for (int i = 0; i < response.length(); i++) {
            try {
                user_accountArrayList.add(new User_Account(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (User_Account user_account : user_accountArrayList) {

            try {

                if(user_account.getPasswordHash().equals(hash) && user_account.getUserName().equals(username) && user_account.isAccountActive()) {
                    Log.i("Success", "Matched");
                    String toString = String.valueOf(user_account.getAuthenticationID());
                    Log.i("Authentication ID", toString);
                    Log.i("Password Hash", user_account.getPasswordHash());
                    didMatch = true;
                }
                else{
                    Log.i("Error", "Didn't match");
                }
            }
            catch (Exception e){

                Log.i("Error","Field is null");
            }
        }

        return didMatch;
    }

    public String getRoleType(JSONArray response, String hash, String username){

        ArrayList<User_Account> user_accountArrayList = new ArrayList<User_Account>();
        String RoleType = "";

        for (int i = 0; i < response.length(); i++) {
            try {
                user_accountArrayList.add(new User_Account(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (User_Account user_account : user_accountArrayList) {

            try {

                if(user_account.getPasswordHash().equals(hash) && user_account.getUserName().equals(username) && user_account.isAccountActive()) {
                    Log.i("Success", "Matched");
                    String toString = String.valueOf(user_account.getAuthenticationID());
                    Log.i("Authentication ID", toString);
                    Log.i("Password Hash", user_account.getPasswordHash());
                    RoleType = user_account.getRoleType();
                }
                else{
                    Log.i("Error", "Didn't match");
                }
            }
            catch (Exception e){

                Log.i("Error","Field is null");
            }
        }

        return RoleType;
    }

    public int getUserID(JSONArray response, String hash, String username){

        ArrayList<User_Account> user_accountArrayList = new ArrayList<User_Account>();
        int userID = 0;

        for (int i = 0; i < response.length(); i++) {
            try {
                user_accountArrayList.add(new User_Account(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (User_Account user_account : user_accountArrayList) {

            try {

                if(user_account.getPasswordHash().equals(hash) && user_account.getUserName().equals(username) && user_account.isAccountActive()) {
                    Log.i("User ID", String.valueOf(user_account.getUserID()));
                    userID = user_account.getUserID();
                }
                else{
                    Log.i("Error", "Didn't match");
                }
            }
            catch (Exception e){

                Log.i("Error","Field is null");
            }
        }

        return userID;
    }
}
