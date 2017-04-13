package com.example.group11.formdapp.ServerAdapter;

import android.os.AsyncTask;
import android.util.Log;

import com.example.group11.formdapp.MainActivity;
import com.example.group11.formdapp.Utilities.JSON.simple.parser.JSONParser;
import com.example.group11.formdapp.Utilities.JSON.simple.parser.ParseException;
import com.example.group11.formdapp.Utilities.MemoryManagment.GlobalJSON;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by jcvar on 4/13/2017.
 */

public class MyRequest {
    public static final String TAG = "API_CALL";

    public static void setGlobalJSON(){
        AsyncTask<String, Void, String> a = new AsyncTask<String, Void, String>(){
            @Override
            protected String doInBackground(String[] params) {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("androidID", MainActivity.ANDROID_ID);
                String re = RequestAPI.sendGetRequest("http://192.168.1.2:8080/android/",map);

                JSONParser parse = new JSONParser();
                try {
                    parse.parse(re);
                    GlobalJSON.json = re;
                } catch (ParseException e) {
                    GlobalJSON.json = "{}";
                }
                Log.i(TAG,"JSON response " + re);
                Log.i(TAG,GlobalJSON.json);
                return "some message";
            }

            @Override
            protected void onPostExecute(String message) {
                //process message
            }
        };
        a.execute();
    }

    public static void foundBeacon(){

        AsyncTask<String, Void, String> a = new AsyncTask<String, Void, String>(){


            @Override
            protected String doInBackground(String[] params) {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("androidID", MainActivity.ANDROID_ID);
                map.put("beaconID", Arrays.toString(MainActivity.BEACON_ID));
                String re = RequestAPI.sendPostRequest("http://192.168.1.2:8080/android/",map);

                JSONParser parse = new JSONParser();
                try {
                    parse.parse(re);
                    GlobalJSON.json = re;
                } catch (ParseException e) {
                    GlobalJSON.json = "{}";
                }
                Log.i(TAG,"beacon response " + re);

                return "some message";
            }

            @Override
            protected void onPostExecute(String message) {
                //process message
            }
        };


        a.execute();
    }

}
