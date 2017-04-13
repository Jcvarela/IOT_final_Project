package com.example.group11.formdapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.group11.formdapp.Utilities.MemoryManagment.GlobalJSON;
import com.example.group11.formdapp.Utilities.fields.FieldListFrag;
import com.example.group11.formdapp.Utilities.form.FormAdapter;
import com.example.group11.formdapp.Utilities.form.FormCard;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllFormFrag extends Fragment {


    private RecyclerView m_recView;
    private RecyclerView.Adapter m_adapter;
    private RecyclerView.LayoutManager m_layout;

    public AllFormFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_list, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_recView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        m_layout = new LinearLayoutManager(getActivity());
        m_adapter = new FormAdapter(getActivity());

        m_recView.setHasFixedSize(true);
        m_recView.setLayoutManager(m_layout);
        m_recView.setAdapter(m_adapter);

        ((FormAdapter) m_adapter).setOnItemClickListener(new FormAdapter.FormClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                FormCard form = ((FormAdapter) m_adapter).getItem(position);
                getFormData(form.getId());
            }
        });


        populateTable();
    }

    @Override
    public void onResume(){
        super.onResume();
        ((FormAdapter) m_adapter).clear();
        populateTable();
    }

    private void getFormData(String formdId){
        Intent intent = new Intent(getActivity().getBaseContext(), FieldListFrag.class);

        intent.putExtra("message", formdId);
        startActivity(intent);


//        Bundle bundle = new Bundle();
//        bundle.putString("message", formdId);
//        //set Fragmentclass Arguments
//        FieldListFrag fragobj= new FieldListFrag();
//        fragobj.setArguments(bundle);
//
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        transaction.add(R.id.forFragList,fragobj).commit();

//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.forFragList, fragobj);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
        //getFragmentManager().beginTransaction().replace(R.id.forFragList,fragobj).commit();
    }

    private void populateTable(){
        ArrayList<FormCard> forms = getFormCard();

        for(FormCard i: forms){
            // Add the item to the view
            ((FormAdapter) m_adapter).addItem(i, m_adapter.getItemCount());
        }
    }

    //TODO:FINISH METHOD
    public ArrayList<FormCard> getFormCard(){
        ArrayList<FormCard> form = new ArrayList<>();

        form.add(new FormCard("1","Jorge OP",new Date()));
        form.add(new FormCard("2","Another name",new Date()));
        form.add(new FormCard("3","League of Legends",new Date()));
        form.add(new FormCard("4","Vladimir was here screaming",new Date()));
        form.add(new FormCard("5","Dario was not Screaming",new Date()));
        form.add(new FormCard("6","Where is Alberto",new Date()));


        return form;
    }

}
