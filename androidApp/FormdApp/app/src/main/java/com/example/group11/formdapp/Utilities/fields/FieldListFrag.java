package com.example.group11.formdapp.Utilities.fields;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.group11.formdapp.R;

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

        fields.add(new FieldItem("2",FieldItem.TEXT,1,"First Name"));
        fields.add(new FieldItem("4",FieldItem.TEXT,2,"Last Name"));
        fields.add(new FieldItem("3",FieldItem.TEXT,3,"DOB"));
        fields.add(new FieldItem("2",FieldItem.TEXT,4,"ID"));
        fields.add(new FieldItem("1",FieldItem.TEXT,5,"dog Friendly"));
        fields.add(new FieldItem("5",FieldItem.TEXT,6,"Who Are You"));


        return fields;
    }
}

