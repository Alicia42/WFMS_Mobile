package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 25/09/17.
 */

import org.json.JSONException;
import org.json.JSONObject;

public class Fire {

    private String FireID;
    private String FireType;
    private String Make;
    private String Model;
    private String Fuel;
    private String ECAN;
    private String Nelson;
    private String Life;

    public String getFireID() {
        return FireID;
    }

    public void setFireID(String fireID) {
        FireID = fireID;
    }

    public String getFireType() {
        return FireType;
    }

    public void setFireType(String fireType) {
        FireType = fireType;
    }

    public String getMake() {
        return Make;
    }

    public void setMake(String make) {
        Make = make;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getFuel() {
        return Fuel;
    }

    public void setFuel(String fuel) {
        Fuel = fuel;
    }

    public String getECAN() {
        return ECAN;
    }

    public void setECAN(String ECAN) {
        this.ECAN = ECAN;
    }

    public String getNelson() {
        return Nelson;
    }

    public void setNelson(String nelson) {
        Nelson = nelson;
    }

    public String getLife() {
        return Life;
    }

    public void setLife(String life) {
        Life = life;
    }

    public Fire(JSONObject object) {
        try {
            this.FireID = object.getString("FireID");
            this.FireType = object.getString("FireType");
            this.Make = object.getString("Make");
            this.Model = object.getString("Model");
            this.Fuel = object.getString("Fuel");
            this.ECAN = object.getString("ECAN");
            this.Nelson = object.getString("Nelson");
            this.Life = object.getString("Life");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public Fire(String fireID, String fireType, String make, String model, String fuel, String ECAN, String nelson, String life) {
        FireID = fireID;
        FireType = fireType;
        Make = make;
        Model = model;
        Fuel = fuel;
        this.ECAN = ECAN;
        Nelson = nelson;
        Life = life;
    }
}