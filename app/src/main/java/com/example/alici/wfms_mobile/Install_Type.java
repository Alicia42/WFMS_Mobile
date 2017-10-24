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

    public Install_Type(JSONObject object) {
        try {
            this.InstallTypeID = object.getInt("InstallTypeID");
            this.InstallDescription = object.getString("InstallDescription");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Install_Type(int installTypeID, String installDescription, int basePrice, String emailFromLetter, String siteCheckFile) {
        InstallTypeID = installTypeID;
        InstallDescription = installDescription;
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
            }
            catch (Exception e){

                Log.i("Error","Field is null");
            }
        }
    }

    public String findInstallType(JSONArray response, Integer installTypeID){

        String installDescription = "";

        ArrayList<Install_Type> install_typeArrayList = new ArrayList<Install_Type>();

        for (int i = 0; i < response.length(); i++) {
            try {
                install_typeArrayList.add(new Install_Type(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Install_Type install : install_typeArrayList){

            if (install.getInstallTypeID() == installTypeID){
                installDescription = install.getInstallDescription();
                Log.i("Install Description", installDescription);
            }
        }

        return installDescription;
    }
}
