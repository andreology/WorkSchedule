package com.schedulesmadeeasy.schedular;

/**
 * User class that is needed to build a user upon sign up
 */
public class User {
    private String user_name;
    private String email;
    private String first_name;
    private String last_name;
    private String userId;

    public User(){

    }

    public User(String mUserName, String mEmail, String mFirstName, String mLastName, String userId) {
        this.user_name = mUserName;
        this.email = mEmail;
        this.first_name = mFirstName;
        this.last_name = mLastName;
        this.userId = userId;
    }

    public String getUserName() {
        return user_name;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getUserId() {
        return userId;
    }
}
