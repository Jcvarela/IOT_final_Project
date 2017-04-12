package com.example.group11.formdapp.Utilities.form;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.group11.formdapp.R;

import java.util.ArrayList;

/**
 * Created by jcvar on 4/12/2017.
 */

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.FormObjectHolder> {

    private static FormClickListener m_clickListener;
    private ArrayList<FormCard> m_data;
    private static Context m_context;

    public FormAdapter(Context con){
        m_context = con;
        m_data = new ArrayList<>();
    }

    //creates the view for the elements
    @Override
    public FormObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards,parent,false);

        FormObjectHolder formholder = new FormObjectHolder(view);
        return formholder;
    }

    //maps view with array index
    @Override
    public void onBindViewHolder(FormObjectHolder holder, int position) {
        holder.title.setText(m_data.get(position).getTitle());
        holder.date.setText(m_data.get(position).getDate().toString());
    }

    @Override
    public int getItemCount() {
        return m_data.size();
    }

    public void addItem(FormCard form, int index){
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
    public FormCard getItem(int position) {
        if (position > -1 && position < m_data.size())
            return m_data.get(position);
        else
            return null;
    }


    public class FormObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView date;


        public FormObjectHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.formName);
            date = (TextView) itemView.findViewById(R.id.date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            m_clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    // Sets the callback function to handle clicking on individual form
    public void setOnItemClickListener(FormClickListener clickListener) {
        this.m_clickListener = clickListener;
    }

    public interface FormClickListener {
        public void onItemClick(int position, View v);
    }
}