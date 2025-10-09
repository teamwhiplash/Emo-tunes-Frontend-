package com.emo_tunes.javafxapp;

public class UserInfo {
    private Integer userId;          // Backend user ID
    private String username;         // Username
    private String email;            // Email// Optional profile image
    // Optional country
         // Optional access token if backend sends it

    // Default constructor (needed for Jackson)
    public UserInfo() {}

    public UserInfo(Integer userId, String username, String email) {


        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    // Getters and setters
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}

