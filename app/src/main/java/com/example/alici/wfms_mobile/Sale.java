package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 25/09/17.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Sale {

    private java.util.Date DateValueUtil;
    private java.util.Date TimeValueUtil;

    private int SaleID;
    private int CustomerID;
    private int InstallTypeID;
    private String SiteAddress;
    private String SiteSuburb;

    public Sale(int saleID, int customerID, int installTypeID, String siteAddress, String siteSuburb) {

        SaleID = saleID;
        CustomerID = customerID;
        InstallTypeID = installTypeID;
        SiteAddress = siteAddress;
        SiteSuburb = siteSuburb;
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

    public Sale(JSONObject object) {
        try {
            this.SaleID = object.getInt("SaleID");
            this.CustomerID = object.getInt("CustomerID");
            this.InstallTypeID = object.getInt("InstallTypeID");
            this.SiteAddress = object.getString("SiteAddress");
            this.SiteSuburb = object.getString("SiteSuburb");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public Sale(){}

    public void getSales(JSONArray response){

        ArrayList<Sale> saleArrayList = new ArrayList<Sale>();

        for (int i = 0; i < response.length(); i++) {
            try {
                saleArrayList.add(new Sale(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Sale sale : saleArrayList) {

            try {
                String toString = String.valueOf(sale.getSaleID());
                Log.i("Sale ID", toString);
                String toStringCust = String.valueOf(sale.getCustomerID());
                Log.i("Customer ID", toStringCust);
                String toStringInstall = String.valueOf(sale.getInstallTypeID());
                Log.i("Install Type ID", toStringInstall);
                Log.i("Site Address", sale.getSiteAddress());
                Log.i("Site Suburb", sale.getSiteSuburb());
            }
            catch (Exception e){

                Log.i("Error","Field is null");
            }
        }
    }

    public int findCustomerID(JSONArray response, Integer saleID){

        ArrayList<Sale> saleArrayList = new ArrayList<Sale>();
        ArrayList<Integer> customerIDList = new ArrayList<Integer>();
        int customerID = 0;

        for (int i = 0; i < response.length(); i++) {
            try {
                saleArrayList.add(new Sale(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Sale sale : saleArrayList){

            if (sale.getSaleID() == saleID){
                //Log.i("found", "found");
                //Log.i("Customer ID", String.valueOf(sale.getCustomerID()));
                customerID = sale.getCustomerID();
            }
        }

        return customerID;
    }

    public int findInstallTypeID(JSONArray response, Integer saleID){

        ArrayList<Sale> saleArrayList = new ArrayList<Sale>();
        int installTypeID = 0;

        for (int i = 0; i < response.length(); i++) {
            try {
                saleArrayList.add(new Sale(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Sale sale : saleArrayList){

            if (sale.getSaleID() == saleID){
                //Log.i("InstallType ID", String.valueOf(sale.getInstallTypeID()));
                installTypeID = sale.getInstallTypeID();
            }
        }

        return installTypeID;
    }

    public ArrayList findSiteAddress(JSONArray response, Integer saleID){

        ArrayList<Sale> saleArrayList = new ArrayList<Sale>();
        ArrayList<Sale> siteAddressList = new ArrayList<Sale>();
        String siteAddress = "";
        String siteSuburb = "";

        for (int i = 0; i < response.length(); i++) {
            try {
                saleArrayList.add(new Sale(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Sale sale : saleArrayList){

            if (sale.getSaleID() == saleID){
                //siteAddress = sale.getSiteAddress();
                //siteSuburb = sale.getSiteSuburb();
                siteAddressList.add(sale);
            }
        }

        return siteAddressList;
    }

}
