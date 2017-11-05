package com.example.alici.wfms_mobile;

/*
 * Created by Libby Jennings on 22/09/17.
 * Description: Class for getting and setting Customer details
 */

import org.json.JSONException;
import org.json.JSONObject;

public class Customer {

    private String FirstName;
    private String LastName;
    private String Phone;
    private String Mobile;
    private String Email;

    public Customer(JSONObject object) {
        try {
            this.FirstName = object.getString("FirstName");
            this.LastName = object.getString("LastName");
            this.Phone = object.getString("Phone");
            this.Mobile = object.getString("Mobile");
            this.Email = object.getString("Email");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Customer(String FirstName, String LastName) {
        this.FirstName = FirstName;
        this.LastName = LastName;
    }

    public Customer(){}

    String getFirstName() {
        return this.FirstName;
    }

    String getLastName() {
        return this.LastName;
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

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public void setEmail(String email) {
        Email = email;
    }

}
