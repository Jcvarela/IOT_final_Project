package com.example.group11.formdapp.Utilities.fields;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Lesly on 4/11/17.
 */

public class FieldItem implements Comparator<FieldItem>, Serializable {
    public static final String TEXT = "text";

    private String id;
    private String type;

    private String title;
    private String value;
    private int pos;


    public FieldItem(String id, String type, int pos, String title){
        this.id = id;
        this.type = type;
        this.pos = pos;
        this.title = title;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public int getPos() {
        return pos;
    }

    public String getValue() {return value;}

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public int compare(FieldItem o1, FieldItem o2) {
        return o2.getPos() - o1.getPos();
    }

    @Override
    public String toString(){
        return Integer.toString(pos);
    }

}
