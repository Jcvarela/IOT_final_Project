package com.example.group11.formdapp.Utilities.MemoryManagment;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by jcvar on 4/12/2017.
 */

//Singleton
public class FileEditor {
    //contains all the forms id
    private final static String FORMS = "FormDrive.txt";

    private ArrayList<String> formsId;

    private static FileEditor fileEditor;
    private Context context;

    private FileEditor(Context context){
        this.context = context;
        readFromFile(FORMS);
    }



    public static FileEditor getFileEditor(Context context){
        if(fileEditor == null){
            fileEditor = new FileEditor(context);
        }

        return fileEditor;
    }


    private void writeToFile(String info, String filePath){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filePath, Context.MODE_PRIVATE));
            outputStreamWriter.write(info);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile(String filePath) {
        String output = "";

        try {
            InputStream inputStream = context.openFileInput(filePath);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();

                output = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return output;
    }

}
