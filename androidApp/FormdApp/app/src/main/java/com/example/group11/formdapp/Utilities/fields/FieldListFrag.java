package com.example.group11.formdapp.Utilities.fields;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.group11.formdapp.R;
import com.example.group11.formdapp.Utilities.JSON.simple.JSONArray;
import com.example.group11.formdapp.Utilities.JSON.simple.JSONObject;
import com.example.group11.formdapp.Utilities.JSON.simple.parser.JSONParser;
import com.example.group11.formdapp.Utilities.JSON.simple.parser.ParseException;
import com.example.group11.formdapp.Utilities.MemoryManagment.GlobalJSON;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */


public class FieldListFrag extends AppCompatActivity {


    private RecyclerView m_recView;
    private RecyclerView.Adapter m_adapter;
    private RecyclerView.LayoutManager m_layout;


    //TODO:FILL THIS VALUE
    private String formId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.field_list);

        m_recView = (RecyclerView)findViewById(R.id.recycler_view_field);
        m_layout = new LinearLayoutManager(this);
        m_adapter = new FieldAdapter(this);

        m_recView.setHasFixedSize(true);
        m_recView.setLayoutManager(m_layout);
        m_recView.setAdapter(m_adapter);

//        ((FieldAdapter) m_adapter).setOnItemClickListener(new FieldAdapter.FieldClickListener() {
//            @Override
//            public void onItemClick(int position, View v) {
//                FieldItem datum = ((FieldAdapter) m_adapter).getItem(position);
//                getFormData(datum);
//            }
//        });

        populateTable();
    }

    @Override
    public void onResume(){
        super.onResume();
        ((FieldAdapter) m_adapter).clear();
        populateTable();
    }

    private void populateTable(){
        ArrayList<FieldItem> field = getFieldById();

        for(FieldItem i: field){
            // Add the item to the view
            ((FieldAdapter) m_adapter).addItem(i, m_adapter.getItemCount());
        }
    }



    //TODO:FINISH METHOD
    public ArrayList<FieldItem> getFieldById(){
        //TODO:use formId to fill fields
        ArrayList<FieldItem> fields = new ArrayList<>();


        String json = GlobalJSON.idkWhatImDoing;

        JSONParser parser = new JSONParser();

        try {
            JSONArray array = (JSONArray) parser.parse(json);

            for(int i = 0; i < array.size(); i++){
                JSONObject obj = (JSONObject) array.get(i);

                String id = obj.get("fullyQualifiedName").toString();
                String title = obj.get("partialName").toString();
                int pos = i;
                String type = obj.get("type").toString();
                String value = obj.get("value").toString();

                FieldItem item = new FieldItem(id,type,pos,title);
                item.setValue(value);

                fields.add(item);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return fields;
    }
}

