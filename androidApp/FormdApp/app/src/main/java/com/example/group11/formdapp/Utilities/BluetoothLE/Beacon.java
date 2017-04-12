package com.example.group11.formdapp.Utilities.BluetoothLE;


/**
 * Created by jcvar on 4/11/2017.
 */


class Beacon {
    //ele[0] id
    private String[] ele;

    public byte[] info;

    public Beacon(byte[] info, String...id){
        ele = id;
        this.info = info;
    }


    private int getSize() {return ele.length;}
    private String getEleAt(int pos) {return ele[pos];}

    @Override
    public boolean equals(Object obj){

        Beacon o;
        if(this.getSize() < 1 || !(obj instanceof Beacon) ) {
            return false;
        }else {
            o = (Beacon) obj;
        }

        if(o.getSize() < 1){
            return false;
        }

        return ele[0].compareTo(o.getEleAt(0)) == 0;
    }

    @Override
    public int hashCode(){
        if(this.getSize() < 1){
            return -1;
        }
        return ele[0].hashCode();
    }
}