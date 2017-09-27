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
    private String NZHHA_Number;
    private String FirstName;
    private String LastName;
    private String PostalAddress;
    private String PostalSuburb;
    private String PostalCode;
    private String Phone;
    private String Mobile;
    private String Email;
    private String ReesNumber;
    private boolean AccountActive;

    public User_Account(JSONObject object) {
        try {
            this.UserID = object.getInt("UserID");
            this.AuthenticationID = object.getInt("AuthenticationID");
            this.UserName = object.getString("UserName");
            this.NZHHA_Number = object.getString("NZHHA_Number");
            this.FirstName = object.getString("FirstName");
            this.LastName = object.getString("LastName");
            this.PostalAddress = object.getString("PostalAddress");
            this.PostalSuburb = object.getString("PostalSuburb");
            this.PostalCode = object.getString("PostalCode");
            this.Phone = object.getString("Phone");
            this.Mobile = object.getString("Mobile");
            this.Email = object.getString("Email");
            this.ReesNumber = object.getString("ReesNumber");
            this.AccountActive = object.getBoolean("AccountActive");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public User_Account(int userID, int authenticationID, String userName, String NZHHA_Number, String firstName, String lastName, String postalAddress, String postalSuburb, String postalCode, String phone, String mobile, String email, String reesNumber, boolean accountActive) {
        UserID = userID;
        AuthenticationID = authenticationID;
        UserName = userName;
        this.NZHHA_Number = NZHHA_Number;
        FirstName = firstName;
        LastName = lastName;
        PostalAddress = postalAddress;
        PostalSuburb = postalSuburb;
        PostalCode = postalCode;
        Phone = phone;
        Mobile = mobile;
        Email = email;
        ReesNumber = reesNumber;
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

    public String getNZHHA_Number() {
        return NZHHA_Number;
    }

    public void setNZHHA_Number(String NZHHA_Number) {
        this.NZHHA_Number = NZHHA_Number;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPostalAddress() {
        return PostalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        PostalAddress = postalAddress;
    }

    public String getPostalSuburb() {
        return PostalSuburb;
    }

    public void setPostalSuburb(String postalSuburb) {
        PostalSuburb = postalSuburb;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getReesNumber() {
        return ReesNumber;
    }

    public void setReesNumber(String reesNumber) {
        ReesNumber = reesNumber;
    }

    public boolean isAccountActive() {
        return AccountActive;
    }

    public void setAccountActive(boolean accountActive) {
        AccountActive = accountActive;
    }

    public User_Account(){}

    public void getUserAccounts(JSONArray response){

        ArrayList<User_Account> user_accountArrayList = new ArrayList<User_Account>();

        for (int i = 0; i < response.length(); i++) {
            try {
                user_accountArrayList.add(new User_Account(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (User_Account user_account : user_accountArrayList) {

            try {
                String toString = String.valueOf(user_account.getAuthenticationID());
                Log.i("Authentication ID", toString);
                String toStringUser = String.valueOf(user_account.getUserID());
                Log.i("UserID", toStringUser);
                Log.i("first name", user_account.getFirstName());
                Log.i("last name", user_account.getLastName());
                Log.i("postal address", user_account.getPostalAddress());
                Log.i("postal suburb", user_account.getPostalSuburb());
                Log.i("postal code", user_account.getPostalCode());
                Log.i("phone", user_account.getPhone());
                Log.i("mobile", user_account.getMobile());
                Log.i("email", user_account.getEmail());
                Log.i("rees number", user_account.getReesNumber());
                String toStringAccountActive = String.valueOf(user_account.isAccountActive());
                Log.i("Account Active", toStringAccountActive);
            }
            catch (Exception e){

                Log.i("Error","Field is null");
            }
        }
    }
}
