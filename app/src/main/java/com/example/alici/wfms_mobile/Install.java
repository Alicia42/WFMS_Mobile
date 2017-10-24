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
    private String FireID;
    private String InstallerID;
    private String StockList;
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

    public String getFireID() {
        return FireID;
    }

    public void setFireID(String fireID) {
        FireID = fireID;
    }

    public Install(JSONObject object) {
        try {
            this.InstallID = object.getInt("InstallID");
            this.SaleID = object.getInt("SaleID");
            this.FireID = object.getString("FireID");
            this.InstallerID = object.getString("InstallerID");
            this.StockList = object.getString("StockList");
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
                Log.i("Installer ID", install.getInstallerID());
                Log.i("Fire ID", install.getFireID());
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

    public int findSaleID(JSONArray response, int installID){

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

        for (Install install : installArrayList){

            if (install.getInstallID() == installID){
                saleID = install.getSaleID();
                //Log.i("Sale", String.valueOf(saleID));
            }
        }

        return saleID;
    }

    public String findFireID(JSONArray response, int installID){

        ArrayList<Install> installArrayList = new ArrayList<Install>();
        String fireID = "";

        for (int i = 0; i < response.length(); i++) {
            try {
                installArrayList.add(new Install(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Install install : installArrayList){

            if (install.getInstallID() == installID){
                fireID = install.getFireID();
                //Log.i("Sale", String.valueOf(saleID));
            }
        }

        return fireID;
    }

    public String findNoteToInstaller(JSONArray response, int installID){

        ArrayList<Install> installArrayList = new ArrayList<Install>();
        String noteToInstaller = "";

        for (int i = 0; i < response.length(); i++) {
            try {
                installArrayList.add(new Install(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Install install : installArrayList){

            if (install.getInstallID() == installID){
                noteToInstaller = install.getNoteToInstaller();
            }
        }

        return noteToInstaller;
    }

    public boolean findInstallCompletion(JSONArray response, int installID){

        ArrayList<Install> installArrayList = new ArrayList<Install>();
        boolean installationComplete = false;

        for (int i = 0; i < response.length(); i++) {
            try {
                installArrayList.add(new Install(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Install install : installArrayList){

            if (install.getInstallID() == installID){
                installationComplete = install.isInstallComplete();
                //Log.i("Sale", String.valueOf(saleID));
            }
        }

        return installationComplete;
    }

    public String findStockList(JSONArray response, int installID){

        ArrayList<Install> installArrayList = new ArrayList<Install>();
        String stockList = "";

        for (int i = 0; i < response.length(); i++) {
            try {
                installArrayList.add(new Install(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Install install : installArrayList){

            if (install.getInstallID() == installID){
                stockList = install.getStockList();
            }
        }

        return stockList;
    }

    public String findInstallerNote(JSONArray response, int installID){

        ArrayList<Install> installArrayList = new ArrayList<Install>();
        String installerNote = "";

        for (int i = 0; i < response.length(); i++) {
            try {
                installArrayList.add(new Install(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Install install : installArrayList){

            if (install.getInstallID() == installID){
                installerNote = install.getInstallerNote();
            }
        }

        return installerNote;
    }

}
