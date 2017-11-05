package com.example.alici.wfms_mobile;

/*
 * Created by Libby Jennings on 23/09/17.
 * Description: class for getting and setting user account details and returning requested user account details
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

    //Constructor for initialising JSON objects from server
    private User_Account(JSONObject object) {
        try {
            //parse objects with data type and name
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

    //getters and setters
    private int getUserID() {
        return UserID;
    }

    private String getUserName() {
        return UserName;
    }

    private boolean isAccountActive() {
        return AccountActive;
    }

    private String getRoleType() {
        return RoleType;
    }

    public boolean isInstall() {
        return Install;
    }

    public void setInstall(boolean install) {
        Install = install;
    }

    private String getPasswordHash() {
        return PasswordHash;
    }

    User_Account(){}

    //Method for checking login details are correct
    boolean checkLoginDetails(JSONArray response, String hash, String username){

        //get JSON objects and add to array list
        ArrayList<User_Account> user_accountArrayList = new ArrayList<User_Account>();
        boolean didMatch = false;

        for (int i = 0; i < response.length(); i++) {
            try {
                user_accountArrayList.add(new User_Account(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //If a user account matches the username and password and account is active, return true
        for (User_Account user_account : user_accountArrayList) {

            try {

                if(user_account.getPasswordHash().equals(hash) && user_account.getUserName().equals(username) && user_account.isAccountActive()) {
                    didMatch = true;
                }
            }
            catch (Exception ignored){
            }
        }

        return didMatch;
    }

    //Method for returning the role type of a selected user account
    String getRoleType(JSONArray response, String hash, String username){

        ArrayList<User_Account> user_accountArrayList = new ArrayList<User_Account>();
        String RoleType = "";

        //get JSON objects and add to array list
        for (int i = 0; i < response.length(); i++) {
            try {
                user_accountArrayList.add(new User_Account(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //If a user account matches the username and password and account is active, set the role type to return
        for (User_Account user_account : user_accountArrayList) {

            try {

                if(user_account.getPasswordHash().equals(hash) && user_account.getUserName().equals(username) && user_account.isAccountActive()) {
                    RoleType = user_account.getRoleType();
                }
            }
            catch (Exception ignored){
            }
        }

        return RoleType;
    }

    //Method for returning the user ID of a selected user account
    int getUserID(JSONArray response, String hash, String username){

        ArrayList<User_Account> user_accountArrayList = new ArrayList<User_Account>();
        int userID = 0;

        //get JSON objects and add to array list
        for (int i = 0; i < response.length(); i++) {
            try {
                user_accountArrayList.add(new User_Account(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //If a user account matches the username and password and account is active, set the User ID to return
        for (User_Account user_account : user_accountArrayList) {

            try {

                if(user_account.getPasswordHash().equals(hash) && user_account.getUserName().equals(username) && user_account.isAccountActive()) {
                    userID = user_account.getUserID();
                }
            }
            catch (Exception ignored){
            }
        }

        return userID;
    }
}
