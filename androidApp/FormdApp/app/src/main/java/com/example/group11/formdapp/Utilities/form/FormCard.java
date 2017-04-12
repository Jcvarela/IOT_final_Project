package com.example.group11.formdapp.Utilities.form;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jcvar on 4/12/2017.
 */

public class FormCard implements Serializable{

    private String id;
    private String title;
    private Date date;


    public FormCard(String id, String title, Date date){
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public String getId(){return id;}
    public String getTitle(){return title;}
    public Date getDate(){return date;}
}
