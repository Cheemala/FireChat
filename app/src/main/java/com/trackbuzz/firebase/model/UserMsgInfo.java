package com.trackbuzz.firebase.model;

/**
 * Created by CheemalaCh on 7/14/2018.
 */

public class UserMsgInfo {
    private String username = null, usermsg = null, dispsts = "0";


    public UserMsgInfo(String username, String usermsg, String dispsts) {
        this.username = username;
        this.usermsg = usermsg;
        this.dispsts = dispsts;
    }

    public String getDispsts() {
        return dispsts;
    }

    public void setDispsts(String dispsts) {
        this.dispsts = dispsts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsermsg() {
        return usermsg;
    }

    public void setUsermsg(String usermsg) {
        this.usermsg = usermsg;
    }

}
