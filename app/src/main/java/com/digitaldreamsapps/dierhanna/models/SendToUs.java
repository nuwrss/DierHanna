package com.digitaldreamsapps.dierhanna.models;

public class SendToUs {
    private String name;
    private String phone;
    private String title;

    public SendToUs(String name, String phone, String title, String message) {
        this.name = name;
        this.phone = phone;
        this.title = title;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
