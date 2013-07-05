package com.application.wakeapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by 23053969 on 2013-07-05.
 */
public class Stations {
    private NearbyStations nearbyStations;
    private StationList stationList;

    public Stations(Double x, Double y,Integer r){

        nearbyStations = new NearbyStations(x,y,r);
        stationList = new StationList();

    }
    public class NearbyStations {
        private String url = "https://api.trafiklab.se/samtrafiken/resrobot/StationsInZone.json?";
        private JSONParser jParser;
        private JSONObject json;
        private JSONArray items;

        public NearbyStations(Double x, Double y, Integer r){
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            sb.append("apiVersion=2.1&");
            sb.append("centerX=" + x.toString() + "&");
            sb.append("centerY=" + y.toString() + "&");
            sb.append("radius=" + r.toString() + "&" );
            sb.append("coordSys=WGS84&");
            sb.append("key=da562e01a1a10ae57652788f1d5dd642");
            url = sb.toString();

            System.out.println("Radde123" + url);
            jParser = new JSONParser();
            json = jParser.getJSONFromUrl(url,Boolean.FALSE);

        }
        public JSONArray getItems(){
            try {
                JSONObject c1 = json.getJSONObject("stationsinzoneresult");
                items = c1.getJSONArray("location");
                // for(int i = 0; i < items.length(); i++){
                //     JSONObject c = items.getJSONObject(i);
                //     System.out.println("Radde123" + c.getString("name"));
                // }
                //System.out.println("Radde123 " + items.length());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return items;
        }
    }

    public class StationList {
        private String url = "http://api.tagtider.net/v1/stations.json";
        private JSONParser jParser;
        private JSONObject json;
        private JSONArray items;

        public StationList(){
            jParser = new JSONParser();
            json = jParser.getJSONFromUrl(url,Boolean.TRUE);
        }

        public JSONArray getItems(){
            try {
                JSONObject c1 = json.getJSONObject("stations");
                items = c1.getJSONArray("station");
                for(int i = 0; i < items.length(); i++){
                    JSONObject c = items.getJSONObject(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return items;
        }
    }

    public ArrayList<String> getAllStations(){
        ArrayList<String> locationList = new ArrayList<String>();

        locationList = getNearbyStations();
        locationList.addAll(getTrainStations());

        return locationList;
    }
    public ArrayList<String> getNearbyStations(){
        JSONArray jsonArray;
        ArrayList<String> locationList = new ArrayList<String>();

        jsonArray = nearbyStations.getItems();

        for (int i=0;i<jsonArray.length();i++){
            try {
                JSONObject c = jsonArray.getJSONObject(i);

                locationList.add(c.getString("name") + " " +
                        c.getString("@x") + " " +
                        c.getString("@y"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return locationList;
    }
    public ArrayList<String> getTrainStations(){
        JSONArray jsonArray;
        ArrayList<String> locationList = new ArrayList<String>();

        jsonArray = stationList.getItems();

        for (int i=0;i<jsonArray.length();i++){
            try {
                JSONObject c = jsonArray.getJSONObject(i);

                locationList.add(c.getString("name") + " " +
                        c.getString("lat") + " " +
                        c.getString("lng"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return locationList;
    }
}