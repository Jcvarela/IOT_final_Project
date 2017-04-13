package com.example.group11.formdapp.Utilities.MemoryManagment;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jcvar on 4/13/2017.
 */

public class GlobalJSON {
    private static String TAG = "TestingJSON";

    public static JSONObject json = new JSONObject();

    public static void testJSON(){

        try {
            json.put("clave", "valor");
            json.put("username", "iesous");
            json.put("password", "1234");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, json.toString());

        try {
            JSONObject temp = new JSONObject();
            temp.put("Jorge", "creo");
            temp.put("Alberto", "where are u ");

            json.put("clave", "1234");
            json.put("username", "980");
            json.put("password", temp);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, json.toString());
    }
}
