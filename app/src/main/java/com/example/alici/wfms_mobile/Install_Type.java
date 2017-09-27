package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 23/09/17.
 */

import org.json.JSONException;
import org.json.JSONObject;


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
}
