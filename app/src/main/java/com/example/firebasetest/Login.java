package com.example.firebasetest;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Login {

    String email;
    String password;
    String name;
    public Login(){}
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Login(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    @Override
    public String toString() {
        return "Login{" +
                "email='" + email + '\'' +
                "password='" + password + '\'' +
                "Name='" + name + '\'' +
                '}';
    }
}
