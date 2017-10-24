package com.example.alici.wfms_mobile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by libbyjennings on 25/10/17.
 */

public class Booking {

    private int InstallID;
    private int SaleID;
    private String FireID;
    private String InstallerID;
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
    private String PostalAddress;
    private String PostalSuburb;
    private String PostalCode;
    private String Phone;
    private String Mobile;
    private String Email;
    private String FireType;
    private String InstallDescription;

    public int getInstallID() {
        return InstallID;
    }

    public void setInstallID(int installID) {
        InstallID = installID;
    }

    public int getSaleID() {
        return SaleID;
    }

    public void setSaleID(int saleID) {
        SaleID = saleID;
    }

    public String getFireID() {
        return FireID;
    }

    public void setFireID(String fireID) {
        FireID = fireID;
    }

    public String getInstallerID() {
        return InstallerID;
    }

    public void setInstallerID(String installerID) {
        InstallerID = installerID;
    }

    public String getStockList() {
        return StockList;
    }

    public void setStockList(String stockList) {
        StockList = stockList;
    }

    public String getNoteToInstaller() {
        return NoteToInstaller;
    }

    public void setNoteToInstaller(String noteToInstaller) {
        NoteToInstaller = noteToInstaller;
    }

    public boolean isInstallComplete() {
        return InstallComplete;
    }

    public void setInstallComplete(boolean installComplete) {
        InstallComplete = installComplete;
    }

    public String getInstallerNote() {
        return InstallerNote;
    }

    public void setInstallerNote(String installerNote) {
        InstallerNote = installerNote;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public int getInstallTypeID() {
        return InstallTypeID;
    }

    public void setInstallTypeID(int installTypeID) {
        InstallTypeID = installTypeID;
    }

    public String getSiteAddress() {
        return SiteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        SiteAddress = siteAddress;
    }

    public String getSiteSuburb() {
        return SiteSuburb;
    }

    public void setSiteSuburb(String siteSuburb) {
        SiteSuburb = siteSuburb;
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

    public String getFireType() {
        return FireType;
    }

    public void setFireType(String fireType) {
        FireType = fireType;
    }

    public String getInstallDescription() {
        return InstallDescription;
    }

    public void setInstallDescription(String installDescription) {
        InstallDescription = installDescription;
    }

    public Booking(int installID, int saleID, String fireID, String installerID, String stockList, String noteToInstaller, boolean installComplete, String installerNote, int customerID, int installTypeID, String siteAddress, String siteSuburb, String firstName, String lastName, String postalAddress, String postalSuburb, String postalCode, String phone, String mobile, String email, String fireType, String installDescription) {
        InstallID = installID;
        SaleID = saleID;
        FireID = fireID;
        InstallerID = installerID;
        StockList = stockList;
        NoteToInstaller = noteToInstaller;
        InstallComplete = installComplete;
        InstallerNote = installerNote;
        CustomerID = customerID;
        InstallTypeID = installTypeID;
        SiteAddress = siteAddress;
        SiteSuburb = siteSuburb;
        FirstName = firstName;
        LastName = lastName;
        PostalAddress = postalAddress;
        PostalSuburb = postalSuburb;
        PostalCode = postalCode;
        Phone = phone;
        Mobile = mobile;
        Email = email;
        FireType = fireType;
        InstallDescription = installDescription;
    }

    public Booking(){}

    public Booking(JSONObject object) {
        try {

            InstallID = object.getInt("InstallID");
            SaleID = object.getInt("SaleID");
            FireID = object.getString("FireID");
            InstallerID = object.getString("InstallerID");
            StockList = object.getString("StockList");
            NoteToInstaller = object.getString("NoteToInstaller");
            InstallComplete = object.getBoolean("InstallComplete");
            InstallerNote = object.getString("InstallerNote");
            CustomerID = object.getInt("InstallID");
            InstallTypeID = object.getInt("InstallID");
            SiteAddress = object.getString("SiteAddress");
            SiteSuburb = object.getString("SiteSuburb");
            FirstName = object.getString("FirstName");
            LastName = object.getString("LastName");
            PostalAddress = object.getString("PostalAddress");
            PostalSuburb = object.getString("PostalAddress");
            PostalCode = object.getString("PostalCode");
            Phone = object.getString("Phone");
            Mobile = object.getString("Mobile");
            Email = object.getString("Email");
            FireType = object.getString("FireType");
            InstallDescription = object.getString("InstallDescription");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList findBookingObj(JSONArray response, int installID){

        ArrayList<Booking> bookingArrayList = new ArrayList<Booking>();
        ArrayList<Booking> foundBookingList = new ArrayList<Booking>();

        for (int i = 0; i < response.length(); i++) {
            try {
                bookingArrayList.add(new Booking(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Booking booking : bookingArrayList){

            if (booking.getInstallID() == installID){

                foundBookingList.add(booking);
            }
        }

        return foundBookingList;
    }
}
