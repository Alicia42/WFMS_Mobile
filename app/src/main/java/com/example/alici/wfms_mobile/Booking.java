package com.example.alici.wfms_mobile;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Libby Jennings on 16/10/17.
 * Description: class for getting and setting booking details
 */

public class Booking {

    private java.util.Date DateValueUtil;
    private int InstallID;
    private java.sql.Date InstallDate;
    private String InstallTime;
    private int UserID;
    private int SaleID;
    private String FireID;
    private String StockList;
    private String NoteToInstaller;
    private boolean InstallComplete;
    private String InstallerNote;
    private int CustomerID;
    private int InstallTypeID;
    private String SiteAddress;
    private String SiteSuburb;
    private String FirstName;
    private String LastName;
    private String Phone;
    private String Mobile;
    private String Email;
    private String FireType;
    private String InstallDescription;

    //Getter and setter methods
    int getInstallID() {
        return InstallID;
    }

    Date getInstallDate() {
        return InstallDate;
    }

    String getInstallTime() {
        return InstallTime;
    }

    int getUserID() {
        return UserID;
    }

    String getStockList() {
        return StockList;
    }

    String getNoteToInstaller() {
        return NoteToInstaller;
    }

    boolean isInstallComplete() {
        return InstallComplete;
    }

    String getInstallerNote() {
        return InstallerNote;
    }

    String getSiteAddress() {
        return SiteAddress;
    }

    String getSiteSuburb() {
        return SiteSuburb;
    }

    String getFirstName() {
        return FirstName;
    }

    String getLastName() {
        return LastName;
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

    String getFireType() {
        return FireType;
    }

    String getInstallDescription() {
        return InstallDescription;
    }

    Booking(){}

    //Constructor for initialising JSON objects from server
    Booking(JSONObject object) {
        try {

            //parse object as int with name
            InstallID = object.getInt("InstallID");

            //parse object as string with name and convert to date
            String InstallDateString = object.getString("InstallDate");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                DateValueUtil = sdf1.parse(InstallDateString);
            } catch (java.text.ParseException e) {
                Log.i("Error", "couldn't parse date");
                Log.i("Error", e.getMessage());
            }

            //get value of converted date object
            this.InstallDate = new Date(DateValueUtil.getTime());
            this.InstallTime = object.getString("InstallTime");
            this.UserID = object.getInt("UserID");
            this.SaleID = object.getInt("SaleID");
            this.FireID = object.getString("FireID");
            this.StockList = object.getString("StockList");
            this.NoteToInstaller = object.getString("NoteToInstaller");
            this.InstallComplete = object.getBoolean("InstallComplete");
            this.InstallerNote = object.getString("InstallerNote");
            this.CustomerID = object.getInt("InstallID");
            this.InstallTypeID = object.getInt("InstallID");
            this.SiteAddress = object.getString("SiteAddress");
            this.SiteSuburb = object.getString("SiteSuburb");
            this.FirstName = object.getString("FirstName");
            this.LastName = object.getString("LastName");
            this.Phone = object.getString("Phone");
            this.Mobile = object.getString("Mobile");
            this.Email = object.getString("Email");
            this.FireType = object.getString("FireType");
            this.InstallDescription = object.getString("InstallDescription");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //method for returning a selected booking row from the install ID
    ArrayList<Booking> findBookingObj(JSONArray response, int installID){

        ArrayList<Booking> bookingArrayList = new ArrayList<Booking>();
        ArrayList<Booking> foundBookingList = new ArrayList<Booking>();

        //get JSON objects and add to array list
        for (int i = 0; i < response.length(); i++) {
            try {
                bookingArrayList.add(new Booking(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //for each booking in the array that has the selected install ID, add it to another list
        for (Booking booking : bookingArrayList){

            if (booking.getInstallID() == installID){

                foundBookingList.add(booking);
            }
        }

        return foundBookingList;
    }
}
