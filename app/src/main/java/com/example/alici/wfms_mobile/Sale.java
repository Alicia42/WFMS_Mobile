package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 25/09/17.
 */

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class Sale {

    private java.util.Date DateValueUtil;
    private java.util.Date TimeValueUtil;

    private int SaleID;
    private int CustomerID;
    private int UserID;
    private int InstallTypeID;
    private String SiteAddress;
    private String SiteSuburb;
    private String SaleStatus;
    private String Fire;
    private int Price;
    private boolean SiteCheckBooked;
    private Date SiteCheckDate;
    private Time SiteCheckTime;
    private int SiteCheckBy;
    private int SalesPerson;
    private Date EstimationDate;
    private String QuoteNumber;
    private String SiteCheckPath;
    private String QuotePath;
    private String PhotoPath;
    private Date FollowUpDate;

    public Sale(int saleID, int customerID, int userID, int installTypeID, String siteAddress, String siteSuburb, String saleStatus, String fire, int price, boolean siteCheckBooked, Date siteCheckDate, Time siteCheckTime, int siteCheckBy, int salesPerson, Date estimationDate, String quoteNumber, String siteCheckPath, String quotePath, String photoPath, Date followUpDate) {

        SaleID = saleID;
        CustomerID = customerID;
        UserID = userID;
        InstallTypeID = installTypeID;
        SiteAddress = siteAddress;
        SiteSuburb = siteSuburb;
        SaleStatus = saleStatus;
        Fire = fire;
        Price = price;
        SiteCheckBooked = siteCheckBooked;
        SiteCheckDate = siteCheckDate;
        SiteCheckTime = siteCheckTime;
        SiteCheckBy = siteCheckBy;
        SalesPerson = salesPerson;
        EstimationDate = estimationDate;
        QuoteNumber = quoteNumber;
        SiteCheckPath = siteCheckPath;
        QuotePath = quotePath;
        PhotoPath = photoPath;
        FollowUpDate = followUpDate;
    }

    public int getSaleID() {
        return SaleID;
    }

    public void setSaleID(int saleID) {
        SaleID = saleID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
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

    public String getSaleStatus() {
        return SaleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        SaleStatus = saleStatus;
    }

    public String getFire() {
        return Fire;
    }

    public void setFire(String fire) {
        Fire = fire;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public boolean isSiteCheckBooked() {
        return SiteCheckBooked;
    }

    public void setSiteCheckBooked(boolean siteCheckBooked) {
        SiteCheckBooked = siteCheckBooked;
    }

    public Date getSiteCheckDate() {
        return SiteCheckDate;
    }

    public void setSiteCheckDate(Date siteCheckDate) {
        SiteCheckDate = siteCheckDate;
    }

    public Time getSiteCheckTime() {
        return SiteCheckTime;
    }

    public void setSiteCheckTime(Time siteCheckTime) {
        SiteCheckTime = siteCheckTime;
    }

    public int getSiteCheckBy() {
        return SiteCheckBy;
    }

    public void setSiteCheckBy(int siteCheckBy) {
        SiteCheckBy = siteCheckBy;
    }

    public int getSalesPerson() {
        return SalesPerson;
    }

    public void setSalesPerson(int salesPerson) {
        SalesPerson = salesPerson;
    }

    public Date getEstimationDate() {
        return EstimationDate;
    }

    public void setEstimationDate(Date estimationDate) {
        EstimationDate = estimationDate;
    }

    public String getQuoteNumber() {
        return QuoteNumber;
    }

    public void setQuoteNumber(String quoteNumber) {
        QuoteNumber = quoteNumber;
    }

    public String getSiteCheckPath() {
        return SiteCheckPath;
    }

    public void setSiteCheckPath(String siteCheckPath) {
        SiteCheckPath = siteCheckPath;
    }

    public String getQuotePath() {
        return QuotePath;
    }

    public void setQuotePath(String quotePath) {
        QuotePath = quotePath;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }

    public Date getFollowUpDate() {return FollowUpDate;}

    public void setFollowUpDate(Date followUpDate) {
        FollowUpDate = followUpDate;
    }

    public Sale(JSONObject object) {
        try {
            this.SaleID = object.getInt("SaleID");
            this.CustomerID = object.getInt("CustomerID");
            this.UserID = object.getInt("UserID");
            this.InstallTypeID = object.getInt("InstallTypeID");
            this.SiteAddress = object.getString("SiteAddress");
            this.SiteSuburb = object.getString("SiteSuburb");
            this.SaleStatus = object.getString("SaleStatus");
            this.Fire = object.getString("Fire");
            this.Price = object.getInt("Price");
            this.SiteCheckBooked = object.getBoolean("SiteCheckBooked");

            String SiteCheckDateString = object.getString("SiteCheckDate");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
            try {
                DateValueUtil = sdf1.parse(SiteCheckDateString);
            }
            catch (java.text.ParseException e){
                Log.i("Error", "couldn't parse date");
                Log.i("Error", e.getMessage());
            }
            java.sql.Date DateValueSql = new java.sql.Date(DateValueUtil.getTime());
            this.SiteCheckDate = DateValueSql;

            String SiteCheckTimeString = object.getString("SiteCheckTime");
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss");
            try {
                TimeValueUtil = sdf2.parse(SiteCheckTimeString);
            }
            catch (java.text.ParseException e){
                Log.i("Error", "couldn't parse date");
                Log.i("Error", e.getMessage());
            }
            java.sql.Time TimeValueSql = new java.sql.Time(TimeValueUtil.getTime());
            this.SiteCheckTime = TimeValueSql;

            this.SiteCheckBy = object.getInt("SiteCheckBy");
            this.SalesPerson = object.getInt("SalesPerson");

            String EstimationDateString = object.getString("EstimationDate");
            try {
                DateValueUtil = sdf1.parse(EstimationDateString);
            }
            catch (java.text.ParseException e){
                Log.i("Error", "couldn't parse date");
                Log.i("Error", e.getMessage());
            }
            java.sql.Date EstimationDateSql = new java.sql.Date(DateValueUtil.getTime());
            this.EstimationDate = EstimationDateSql;

            this.QuoteNumber = object.getString("QuoteNumber");
            this.SiteCheckPath = object.getString("SiteCheckPath");
            this.QuotePath = object.getString("QuotePath");
            this.PhotoPath = object.getString("PhotoPath");

            String FollowUpDateString = object.getString("FollowUpDate");
            try {
                DateValueUtil = sdf1.parse(FollowUpDateString);
            }
            catch (java.text.ParseException e){
                Log.i("Error", "couldn't parse date");
                Log.i("Error", e.getMessage());
            }
            java.sql.Date FollowUpDateSql = new java.sql.Date(DateValueUtil.getTime());
            this.FollowUpDate = FollowUpDateSql;

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
