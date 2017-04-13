package com.example.group11.formdapp.Utilities.fields;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.group11.formdapp.R;

import java.util.ArrayList;

/**
 * Created by jcvar on 4/12/2017.
 */

public class FieldAdapter extends RecyclerView.Adapter<FieldAdapter.FieldObjectHolder> {

    private ArrayList<FieldItem> m_data;
    private static Context m_context;

    public FieldAdapter(Context con){
        m_context = con;
        m_data = new ArrayList<>();
        m_data = new ArrayList<>();
    }

    //creates the view for the elements
    @Override
    public FieldObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fieldcard,parent,false);

        FieldObjectHolder formholder = new FieldObjectHolder(view);
        return formholder;
    }

    //maps view with array index
    @Override
    public void onBindViewHolder(FieldObjectHolder holder, int position) {
        holder.title.setText(m_data.get(position).getTitle());
        holder.value.setText(m_data.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return m_data.size();
    }

    public void addItem(FieldItem form, int index){
        m_data.add(index,form);
        super.notifyItemInserted(index);
    }
    public void deleteItem(int index) {
        m_data.remove(index);
        notifyItemRemoved(index);
    }

    public void clear() {
        m_data.clear();
        notifyDataSetChanged();
    }

    // Get a specific datum
    public FieldItem getItem(int position) {
        if (position > -1 && position < m_data.size())
            return m_data.get(position);
        else
            return null;
    }


    public class FieldObjectHolder extends RecyclerView.ViewHolder {

        TextView title;
        EditText value;


        public FieldObjectHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.TitleText);
            value = (EditText) itemView.findViewById(R.id.editText);


        }


    }

}