package com.trackbuzz.firebase.model;

/**
 * Created by CheemalaCh on 7/12/2018.
 */

public class ChatCredentials {

    public static String myId = "", chatWithId = "";

    public static String getMyId() {
        return myId;
    }

    public static void setMyId(String myId) {
        ChatCredentials.myId = myId;
    }

    public static String getChatWithId() {
        return chatWithId;
    }

    public static void setChatWithId(String chatWithId) {
        ChatCredentials.chatWithId = chatWithId;
    }

}
