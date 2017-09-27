package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 22/09/17.
 */

import org.json.JSONException;
import org.json.JSONObject;

public class Customer {

    private int CustomerID;
    private String FirstName;
    private String LastName;
    private String PostalAddress;
    private String PostalSuburb;
    private String PostalCode;
    private String Phone;
    private String Mobile;
    private String Email;
    private String ReesCode;

    public Customer(JSONObject object) {
        try {
            this.CustomerID = object.getInt("CustomerID");
            this.FirstName = object.getString("FirstName");
            this.LastName = object.getString("LastName");
            this.PostalAddress = object.getString("PostalAddress");
            this.PostalSuburb = object.getString("PostalSuburb");
            this.PostalCode = object.getString("PostalCode");
            this.Phone = object.getString("Phone");
            this.Mobile = object.getString("Mobile");
            this.Email = object.getString("Email");
            this.ReesCode = object.getString("ReesCode");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Customer(int CustomerID, String FirstName, String LastName) {
        this.CustomerID = CustomerID;
        this.FirstName = FirstName;
        this.LastName = LastName;
    }

    public int getCustomerID() {
        return this.CustomerID;
    }

    public String getFirstName() {
        return this.FirstName;
    }

    public String getLastName() {
        return this.LastName;
    }

    public String getPostalAddress() {
        return this.PostalAddress;
    }

    public String getPostalSuburb() {
        return this.PostalSuburb;
    }

    public String getPostalCode() {
        return this.PostalCode;
    }

    public String getPhone() {
        return this.Phone;
    }

    public String getMobile() {
        return this.Mobile;
    }

    public String getEmail() {
        return this.Email;
    }

    public String getReesCode() {
        return this.ReesCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setReesCode(String reesCode) {
        ReesCode = reesCode;
    }

    public void setPostalSuburb(String postalSuburb) {
        PostalSuburb = postalSuburb;

    }

    public void setCustomerID(int CustomerID) {
        this.CustomerID = CustomerID;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }
    public void setPostalAddress(String PostalAddress) {
        this.PostalAddress = PostalAddress;
    }

}
