package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 25/09/17.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fire {

    private String FireID;
    private String FireType;

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

    public Fire(JSONObject object) {
        try {
            this.FireID = object.getString("FireID");
            this.FireType = object.getString("FireType");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public Fire(String fireID, String fireType) {
        FireID = fireID;
        FireType = fireType;
    }

    public Fire(){}

    public void getFires(JSONArray response){

        ArrayList<Fire> fireArrayList = new ArrayList<Fire>();

        for (int i = 0; i < response.length(); i++) {
            try {
                fireArrayList.add(new Fire(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Fire fire : fireArrayList) {

            try {
                Log.i("Fire ID", fire.getFireID());
                Log.i("Fire Type", fire.getFireType());
            }
            catch (Exception e){

                Log.i("Error","Field is null");
            }
        }
    }

    public String findFire(JSONArray response, String fireID){

        ArrayList<Fire> fireArrayList = new ArrayList<Fire>();
        String fireType = "";

        for (int i = 0; i < response.length(); i++) {
            try {
                fireArrayList.add(new Fire(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Fire fire: fireArrayList){

            if (fire.getFireID().equals(fireID)){
                fireType = fire.getFireType();
            }
        }

        return fireType;
    }
}
