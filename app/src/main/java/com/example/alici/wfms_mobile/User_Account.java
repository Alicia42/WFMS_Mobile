package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 23/09/17.
 */

import org.json.JSONException;
import org.json.JSONObject;


public class User_Account {

    private int UserID;
    private int AuthenticationID;
    private String UserName;
    private String Role;
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
            this.Role = object.getString("Role");
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

    public User_Account(int userID, int authenticationID, String userName, String role, String NZHHA_Number, String firstName, String lastName, String postalAddress, String postalSuburb, String postalCode, String phone, String mobile, String email, String reesNumber, boolean accountActive) {
        UserID = userID;
        AuthenticationID = authenticationID;
        UserName = userName;
        Role = role;
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

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
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
}
