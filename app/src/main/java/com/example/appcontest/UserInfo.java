package com.example.appcontest;

public class UserInfo {

    private String uid;
    private String name;
    private String phone;
    private String email;
    private String password;
    private String user_classify;

    public UserInfo(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_classify() {
        return user_classify;
    }

    public void setUser_classify(String user_classify) {
        this.user_classify = user_classify;
    }
}
