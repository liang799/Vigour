package com.sp.vigour;

public class Vigouritem {

    private String usersteps;
    private String userdate;
    private String usertime;
    private String usercrypto;


    public Vigouritem(String usersteps,String userdate,String usertime,String usercrypto){
        this.usersteps = usersteps;
        this.userdate = userdate;
        this.usertime = usertime;
        this.usercrypto = usercrypto;

    }

    public String getUsercrypto() {
        return usercrypto;
    }

    public String getUserdate() {
        return userdate;
    }

    public String getUsersteps() {
        return usersteps;
    }

    public String getUsertime() {
        return usertime;
    }
}
