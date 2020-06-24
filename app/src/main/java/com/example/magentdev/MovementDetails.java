package com.example.magentdev;

public class MovementDetails {
    double mvl;
    String mst;
    String vdt;
    String time;



    String mds;

    public MovementDetails(double mvl, String mst, String vdt, String mds) {
        this.mvl = mvl;
        this.mst = mst;
        this.vdt = vdt;
        this.mds = mds;
        this.time = time;
    }

    public String getMvl() {
        return String.valueOf(mvl);
    }

    public String getMst() {
        String type;
        if(mst.equals("Z")) type = "Confirmed";
        else if(mst.equals("Y")) type = "Reverted";
        else if(mst.equals("X")) type = "Deleted";
        else if(mst.equals("S")) type = "Synchronised";
        else if(mst.equals("R")) type = "Reconciled";
        else if(mst.equals("P")) type = "Pending";
        else type = "Draft";

        return type;
    }

    public String getVdt() {
        return vdt;
    }

    public String getMds() {
        return mds;
    }
    public String getTime() {
        return time;
    }




}
