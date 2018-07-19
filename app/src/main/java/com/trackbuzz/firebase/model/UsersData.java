package com.trackbuzz.firebase.model;

/**
 * Created by CheemalaCh on 7/8/2018.
 */

public class UsersData {

    public String username, password;

    public UsersData() {
    }


    public UsersData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
