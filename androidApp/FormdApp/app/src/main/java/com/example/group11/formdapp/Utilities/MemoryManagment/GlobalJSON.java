package com.example.group11.formdapp.Utilities.MemoryManagment;


import com.example.group11.formdapp.ServerAdapter.MyRequest;
import com.example.group11.formdapp.Utilities.JSON.simple.JSONArray;
import com.example.group11.formdapp.Utilities.JSON.simple.JSONObject;
import com.example.group11.formdapp.Utilities.JSON.simple.parser.JSONParser;
import com.example.group11.formdapp.Utilities.JSON.simple.parser.ParseException;
import com.example.group11.formdapp.Utilities.fields.FieldItem;

import java.util.ArrayList;


/**
 * Created by jcvar on 4/13/2017.
 */

public class GlobalJSON {
    private static String TAG = "TestingJSON";

    public static String json = "[ { \"fullyQualifiedName\": \"Full name\", " +
        "   \"partialName\": \"Full name\", " +
        "   \"type\": \"org.apache.pdfbox.pdmodel.interactive.form.PDTextField\", " +
        "   \"isRequired\": false, " +
        "   \"page\": 0, " +
        "   \"cords\": {}, " +
        "   \"value\": \"\" }, " +
        " { \"fullyQualifiedName\": \"DOB\", " +
        "   \"partialName\": \"DOB\", " +
        "   \"type\": \"org.apache.pdfbox.pdmodel.interactive.form.PDTextField\", " +
        "   \"isRequired\": false, " +
        "   \"page\": 0, " +
        "   \"cords\": {}, " +
        "   \"value\": \"08-17-1993\" } ]";

    public static void saveValues(ArrayList<FieldItem> data) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONArray array = (JSONArray) parser.parse(json);

        for(int i = 0; i < data.size(); i++){
            JSONObject obj = (JSONObject) array.get(i);
            obj.put("value",data.get(i).getValue());
        }
    }

    public static String getSubmitJSON() {
        JSONObject output = new JSONObject();
        try {
            JSONParser parser = new JSONParser();
            JSONArray array = (JSONArray) parser.parse(json);

            for (int i = 0; i < array.size(); i++) {
                JSONObject obj = (JSONObject) array.get(i);
                output.put(obj.get("fullyQualifiedName"), obj.get("value"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return output.toString();
    }


    public static void setJSON(){
        MyRequest.setGlobalJSON();
    }

    public static void initJSON(){
        GlobalJSON.json = "[]";
    }
}
