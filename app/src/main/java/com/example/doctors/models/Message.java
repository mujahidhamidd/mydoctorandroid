package com.example.doctors.models;

import java.io.Serializable;

public class Message implements Serializable {

    String message ;
    int user_id;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
