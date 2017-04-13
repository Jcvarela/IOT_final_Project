package com.example.group11.formdapp.Utilities.MemoryManagment;


import com.example.group11.formdapp.ServerAdapter.MyRequest;


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

    public static void setJSON(){
        MyRequest.setGlobalJSON();
    }

    public static void initJSON(){
        GlobalJSON.json = "[]";
    }
}
