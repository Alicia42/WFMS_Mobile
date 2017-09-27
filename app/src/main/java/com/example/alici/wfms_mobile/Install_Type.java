package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 23/09/17.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Install_Type {

    private int InstallTypeID;
    private String InstallDescription;
    private int BasePrice;
    private String EmailFromLetter;
    private String SiteCheckFile;

    public Install_Type(JSONObject object) {
        try {
            this.InstallTypeID = object.getInt("InstallTypeID");
            this.InstallDescription = object.getString("InstallDescription");
            this.BasePrice = object.getInt("BasePrice");
            this.EmailFromLetter = object.getString("EmailFromLetter");
            this.SiteCheckFile = object.getString("SiteCheckFile");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Install_Type(int installTypeID, String installDescription, int basePrice, String emailFromLetter, String siteCheckFile) {
        InstallTypeID = installTypeID;
        InstallDescription = installDescription;
        BasePrice = basePrice;
        EmailFromLetter = emailFromLetter;
        SiteCheckFile = siteCheckFile;
    }

    public int getInstallTypeID() {
        return InstallTypeID;
    }

    public void setInstallTypeID(int installTypeID) {
        InstallTypeID = installTypeID;
    }

    public String getInstallDescription() {
        return InstallDescription;
    }

    public void setInstallDescription(String installDescription) {
        InstallDescription = installDescription;
    }

    public int getBasePrice() {
        return BasePrice;
    }

    public void setBasePrice(int basePrice) {
        BasePrice = basePrice;
    }

    public String getEmailFromLetter() {
        return EmailFromLetter;
    }

    public void setEmailFromLetter(String emailFromLetter) {
        EmailFromLetter = emailFromLetter;
    }

    public String getSiteCheckFile() {
        return SiteCheckFile;
    }

    public void setSiteCheckFile(String siteCheckFile) {
        SiteCheckFile = siteCheckFile;
    }

    public Install_Type(){}

    public void getInstallTypes(JSONArray response){

        ArrayList<Install_Type> install_typeArrayList = new ArrayList<Install_Type>();

        for (int i = 0; i < response.length(); i++) {
            try {
                install_typeArrayList.add(new Install_Type(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Install_Type install_type : install_typeArrayList) {

            try {
                String toString = String.valueOf(install_type.getInstallTypeID());
                Log.i("InstallType ID", toString);
                Log.i("InstallTypeDescription", install_type.getInstallDescription());
                String toStringBase = String.valueOf(install_type.getBasePrice());
                Log.i("Base Price", toStringBase);
                Log.i("email from letter", install_type.getEmailFromLetter());
                Log.i("site check file", install_type.getSiteCheckFile());
            }
            catch (Exception e){

                Log.i("Error","Field is null");
            }
        }
    }
}
