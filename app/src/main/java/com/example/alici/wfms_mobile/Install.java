package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 25/09/17.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Install {

    private java.util.Date DateValueUtil;

    private int InstallID;
    private int SaleID;
    private String InstallStatus;
    private boolean OrderChecked;
    private String InstallerID;
    private Date InstallDate;
    private String InstallTime;
    private int PartsReady;
    private String NoteToInstaller;
    private boolean InstallComplete;
    private String InstallerNote;

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

    public String getInstallStatus() {
        return InstallStatus;
    }

    public void setInstallStatus(String installStatus) {
        InstallStatus = installStatus;
    }

    public boolean isOrderChecked() {
        return OrderChecked;
    }

    public void setOrderChecked(boolean orderChecked) {
        OrderChecked = orderChecked;
    }

    public String getInstallerID() {
        return InstallerID;
    }

    public void setInstallerID(String installerID) {
        InstallerID = installerID;
    }

    public Date getInstallDate() {
        return InstallDate;
    }

    public void setInstallDate(Date installDate) {
        InstallDate = installDate;
    }

    public String getInstallTime() {
        return InstallTime;
    }

    public void setInstallTime(String installTime) {
        InstallTime = installTime;
    }

    public int getPartsReady() {
        return PartsReady;
    }

    public void setPartsReady(int partsReady) {
        PartsReady = partsReady;
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

    public Install(JSONObject object) {
        try {
            this.InstallID = object.getInt("InstallID");
            this.SaleID = object.getInt("SaleID");
            this.InstallStatus = object.getString("InstallStatus");
            this.OrderChecked = object.getBoolean("OrderChecked");
            this.InstallerID = object.getString("InstallerID");

            String InstallDateString = object.getString("InstallDate");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
            try {
                DateValueUtil = sdf1.parse(InstallDateString);
            }
            catch (java.text.ParseException e){
                Log.i("Error", "couldn't parse date");
                Log.i("Error", e.getMessage());
            }
            java.sql.Date DateValueSql = new java.sql.Date(DateValueUtil.getTime());

            this.InstallDate = DateValueSql;
            this.InstallTime = object.getString("InstallTime");
            this.PartsReady = object.getInt("PartsReady");
            this.NoteToInstaller = object.getString("NoteToInstaller");
            this.InstallComplete = object.getBoolean("InstallComplete");
            this.InstallerNote = object.getString("InstallerNote");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Install(){}

    public void getInstalls(JSONArray response) {

        ArrayList<Install> installArrayList = new ArrayList<Install>();

        for (int i = 0; i < response.length(); i++) {
            try {
                installArrayList.add(new Install(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Install install : installArrayList) {

            try {
                String toStringInstall = String.valueOf(install.getInstallID());
                Log.i("Install ID", toStringInstall);
                String toString = String.valueOf(install.getSaleID());
                Log.i("Sale ID", toString);
                Log.i("Install Status", install.getInstallStatus());
                String toStringChecked = String.valueOf(install.isOrderChecked());
                Log.i("Order Checked", toStringChecked);
                Log.i("Installer ID", install.getInstallerID());
                Log.i("Install Date", install.getInstallDate().toString());
                Log.i("Install Time", install.getInstallTime());
                String toStringParts = String.valueOf(install.getPartsReady());
                Log.i("Parts Ready", toStringParts);
                Log.i("Note to Installer", install.getNoteToInstaller());
                Log.i("Installer ID", install.getInstallerID());
                String toStringComplete = String.valueOf(install.isInstallComplete());
                Log.i("Install Complete", toStringComplete);
                Log.i("Installer Note", install.getInstallerNote());
            } catch (Exception e) {

                Log.i("Error", "Field is null");
            }
        }
    }

    public List findSaleID(JSONArray response){

        ArrayList<Install> installArrayList = new ArrayList<Install>();
        ArrayList<Integer> saleIDList = new ArrayList<Integer>();
        int saleID = 0;

        for (int i = 0; i < response.length(); i++) {
            try {
                installArrayList.add(new Install(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Install install : installArrayList) {

            try {

                /*if(installID == install.getInstallID()) {
                    String toString = String.valueOf(install.getSaleID());
                    Log.i("Found Sale ID", toString);
                    Log.i("Install ID", String.valueOf(installID));
                    //Log.i("Install ID 2", String.valueOf(install.getInstallID()));
                    saleIDList.add(install.getSaleID());
                }
                else {
                    //Log.i("Sale ID", "no match");
                }*/
                //Log.i("Sale ID", String.valueOf(install.getSaleID()));
                saleID = install.getSaleID();
                saleIDList.add(install.getSaleID());
            }
            catch (Exception e){

                Log.i("Error","Field is null");
            }
        }

        return saleIDList;
    }

}
