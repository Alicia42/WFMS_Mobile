package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 25/09/17.
 */

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Install {

    private java.util.Date DateValueUtil;

    private int InstallID;
    private int SaleID;
    private String InstallStatus;
    private String InvoicePath;
    private String SiteCheckPath;
    private String PhotoPath;
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

    public String getInvoicePath() {
        return InvoicePath;
    }

    public void setInvoicePath(String invoicePath) {
        InvoicePath = invoicePath;
    }

    public String getSiteCheckPath() {
        return SiteCheckPath;
    }

    public void setSiteCheckPath(String siteCheckPath) {
        SiteCheckPath = siteCheckPath;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
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
            this.InvoicePath = object.getString("InvoicePath");
            this.SiteCheckPath = object.getString("SiteCheckPath");
            this.PhotoPath = object.getString("PhotoPath");
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

}
